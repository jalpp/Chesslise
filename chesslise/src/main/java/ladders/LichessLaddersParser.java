package ladders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LichessLaddersParser {

    public static final int DISCORD_CHAR_LIMIT = 2000;
    public static final int SAFE_CHAR_LIMIT = 1900; // Leave some buffer

    // Main data classes
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LadderResponse {
        public int id;
        public String name;
        public String description;
        public int timeControlBase;
        public int timeControlIncrement;
        public String status;
        public List<UserRanking> users = new ArrayList<>();

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("```\n"); // Code block for better formatting
            sb.append("🏆 LADDER INFORMATION\n");
            sb.append("══════════════════════\n");
            sb.append("📋 Name: ").append(name).append("\n");
            sb.append("📝 Description: ").append(description).append("\n");
            sb.append("⏱️ Time Control: ").append(timeControlBase).append("+").append(timeControlIncrement).append("\n");
            sb.append("🔄 Status: ").append(status).append("\n");
            sb.append("👥 Total Players: ").append(users.size()).append("\n");
            sb.append("```\n");
            sb.append("\n**🎯 LADDER RANKINGS**\n");

            for (UserRanking userRanking : users) {
                sb.append(userRanking.toString()).append("\n");
            }

            return sb.toString();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserRanking {
        public User user;
        public int openChallengeCount;
        public int ranking;
        public boolean canIChallenge;
        public String status;
        public String holidayFrom;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            // Rank emoji based on position
            String rankEmoji = getRankEmoji(ranking);

            sb.append(rankEmoji).append(" **#").append(ranking).append("** ");
            sb.append(" \uD83D\uDC4B ").append("**").append(user.discordName == null ? "Not connected" : user.discordName).append("** ");
            sb.append("♟\uFE0F ");
            sb.append("**").append(user.lichessName).append("** ");
            sb.append(" (").append(user.classicalRating);
            if (user.classicalRatingIsProvisional) {
                sb.append("? 🔄) ");
            } else {
                sb.append(")");
            }

            // Status emoji
            String statusEmoji = getStatusEmoji(status);
            sb.append(" ").append(statusEmoji).append(" ").append(status);

            // Timezone with emoji
            sb.append(" 🌍 ").append(user.timezone);

            if (user.notes != null && !user.notes.trim().isEmpty()) {
                sb.append("\n   💬 *").append(user.notes.replace("\n", " | ")).append("*");
            }
            return sb.toString();
        }

        private String getRankEmoji(int rank) {
            switch (rank) {
                case 1: return "🥇";
                case 2: return "🥈";
                case 3: return "🥉";
                default:
                    if (rank <= 10) return "🏅";
                    else if (rank <= 20) return "⭐";
                    else return "🔹";
            }
        }

        private String getStatusEmoji(String status) {
            if (status == null) return "❓";
            switch (status.toLowerCase()) {
                case "active": return "🟢";
                case "inactive": return "🔴";
                case "busy": return "🟡";
                case "holiday": return "🏖️";
                case "available": return "✅";
                default: return "⚪";
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        public String lichessId;
        public String lichessName;
        public String lichessURL;
        public int classicalRating;
        public boolean classicalRatingIsProvisional;
        public String email;
        public int id;
        public boolean isAdmin;
        public String timezone;
        public String notes;
        public String discordId;
        public String discordName;
        public int firstDayOfWeek;
    }

    // Class to hold paged results
    public static class PagedResult {
        private List<String> pages;
        private int totalPages;
        private String originalContent;

        public PagedResult(List<String> pages, String originalContent) {
            this.pages = pages;
            this.totalPages = pages.size();
            this.originalContent = originalContent;
        }

        public List<String> getPages() {
            return pages;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public String getPage(int pageNumber) {
            if (pageNumber < 1 || pageNumber > totalPages) {
                throw new IllegalArgumentException("Page number must be between 1 and " + totalPages);
            }
            return pages.get(pageNumber - 1);
        }

        public String getOriginalContent() {
            return originalContent;
        }

        public boolean hasMultiplePages() {
            return totalPages > 1;
        }
    }

    // Method to fetch data from API
    public static String fetchApiData(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Java LichessLaddersParser/1.0");

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("HTTP Error: " + responseCode);
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();

        } finally {
            connection.disconnect();
        }
    }

    // Method to parse JSON and return formatted string
    public static String parseAndFormat(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        LadderResponse ladder = mapper.readValue(jsonString, LadderResponse.class);
        return ladder.toString();
    }

    // Method to create paged ladder info
    public static PagedResult parseAndFormatPaged(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        LadderResponse ladder = mapper.readValue(jsonString, LadderResponse.class);

        // Create header section
        StringBuilder header = new StringBuilder();
        header.append("```\n"); // Code block for better formatting
        header.append("🏆 LADDER INFORMATION\n");
        header.append("══════════════════════\n");
        header.append("📋 Name: ").append(ladder.name).append("\n");
        header.append("📝 Description: ").append(ladder.description).append("\n");
        header.append("⏱️ Time Control: ").append(ladder.timeControlBase).append("+").append(ladder.timeControlIncrement).append("\n");
        header.append("🔄 Status: ").append(ladder.status).append("\n");
        header.append("👥 Total Players: ").append(ladder.users.size()).append("\n");
        header.append("```\n");

        List<String> pages = new ArrayList<>();

        // If header + some users fit in one page, try to include them
        StringBuilder currentPage = new StringBuilder(header);
        currentPage.append("\n**🎯 LADDER RANKINGS**\n");

        String headerWithRankings = currentPage.toString();
        int currentPageNumber = 1;
        boolean isFirstPage = true;

        for (UserRanking userRanking : ladder.users) {
            String userString = userRanking.toString() + "\n";

            // Check if adding this user would exceed the limit
            if (currentPage.length() + userString.length() > SAFE_CHAR_LIMIT) {
                // Add current page and start a new one
                if (isFirstPage) {
                    pages.add(currentPage.toString() + "\n*📄 Page 1 of " + calculateTotalPages(ladder.users, headerWithRankings.length()) + "*");
                } else {
                    pages.add(currentPage.toString() + "\n*📄 Page " + currentPageNumber + " of " + calculateTotalPages(ladder.users, headerWithRankings.length()) + "*");
                }

                currentPageNumber++;
                isFirstPage = false;

                // Start new page
                currentPage = new StringBuilder();
                currentPage.append("**🎯 LADDER RANKINGS** *(continued)*\n");
                currentPage.append(userString);
            } else {
                currentPage.append(userString);
            }
        }

        // Add the last page
        if (currentPage.length() > 0) {
            if (pages.isEmpty()) {
                // Only one page needed
                pages.add(currentPage.toString());
            } else {
                pages.add(currentPage.toString() + "\n*📄 Page " + currentPageNumber + " of " + currentPageNumber + "*");
            }
        }

        // Update page numbers if multiple pages
        if (pages.size() > 1) {
            for (int i = 0; i < pages.size(); i++) {
                String page = pages.get(i);
                if (!page.contains("*📄 Page")) {
                    continue;
                }
                // Update with correct total
                page = page.replaceAll("\\*📄 Page \\d+ of \\d+\\*", "*📄 Page " + (i + 1) + " of " + pages.size() + "*");
                pages.set(i, page);
            }
        }

        return new PagedResult(pages, ladder.toString());
    }

    // Helper method to calculate total pages needed
    private static int calculateTotalPages(List<UserRanking> users, int headerLength) {
        int totalPages = 1;
        int currentLength = headerLength;

        for (UserRanking user : users) {
            String userString = user.toString() + "\n";
            if (currentLength + userString.length() > SAFE_CHAR_LIMIT) {
                totalPages++;
                currentLength = "**🎯 LADDER RANKINGS** *(continued)*\n".length() + userString.length();
            } else {
                currentLength += userString.length();
            }
        }

        return totalPages;
    }

    public static String searchUsersByDiscordId(String discordId) throws IOException {
        String apiUrl = "https://api.lichessladders.com/users/search?discordId=" + discordId;
        return fetchApiData(apiUrl);
    }

    // Method to parse user search results with Discord formatting
    public static String parseUserSearchResults(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User[] users = mapper.readValue(jsonString, User[].class);

        StringBuilder sb = new StringBuilder();
        if (users.length == 0) {
            sb.append("❌ **No users found.**\n");
        } else {
            sb.append("👤 **USER SEARCH RESULTS**\n");
            sb.append("════════════════════════\n\n");

            for (int i = 0; i < users.length; i++) {
                User user = users[i];

                sb.append("**").append(i + 1).append(".** 🎯 **").append(user.lichessName).append("**\n");
                sb.append("📊 **Rating:** ").append(user.classicalRating);
                if (user.classicalRatingIsProvisional) {
                    sb.append("? 🔄 *(Provisional)*");
                }
                sb.append("\n");

                sb.append("🔗 **Profile:** ").append(user.lichessURL).append("\n");
                sb.append("🌍 **Timezone:** ").append(user.timezone).append("\n");
                sb.append("💬 **Discord:** ").append(user.discordName).append(" (ID: `").append(user.discordId).append("`)\n");

                if (user.isAdmin) {
                    sb.append("👑 **Admin User**\n");
                }

                if (user.notes != null && !user.notes.trim().isEmpty()) {
                    sb.append("📝 **Notes:** *").append(user.notes.replace("\n", " | ")).append("*\n");
                }

                // Add separator between users (except for the last one)
                if (i < users.length - 1) {
                    sb.append("\n").append("─".repeat(30)).append("\n\n");
                }
            }
        }

        return sb.toString();
    }

    // Original methods for backward compatibility
    public static String getLadderInfo(String id) throws IOException {
        String apiUrl = "https://api.lichessladders.com/ladders/" + id;
        String jsonResponse = fetchApiData(apiUrl);
        return parseAndFormat(jsonResponse);
    }

    // New paged method
    public static PagedResult getLadderInfoPaged(String id) throws IOException {
        String apiUrl = "https://api.lichessladders.com/ladders/" + id;
        String jsonResponse = fetchApiData(apiUrl);
        return parseAndFormatPaged(jsonResponse);
    }

    public static String getPlayerInfo(String discordId) throws IOException {
        return parseUserSearchResults(searchUsersByDiscordId(discordId));
    }

    // Utility method to check if content needs paging
    public static boolean needsPaging(String content) {
        return content.length() > SAFE_CHAR_LIMIT;
    }

    public static void main(String[] args) {
        try {
            // Test paged functionality
            System.out.println("=== TESTING PAGED LADDER INFO ===");
            PagedResult pagedLadder = getLadderInfoPaged("2");

            System.out.println("Total pages: " + pagedLadder.getTotalPages());
            System.out.println("Has multiple pages: " + pagedLadder.hasMultiplePages());

            // Print each page
            for (int i = 1; i <= pagedLadder.getTotalPages(); i++) {
                System.out.println("\n" + "=".repeat(60));
                System.out.println("PAGE " + i + ":");
                System.out.println("=".repeat(60));
                String page = pagedLadder.getPage(i);
                System.out.println(page);
                System.out.println("Character count: " + page.length());
            }

            // Test player info (usually short enough for one message)
            System.out.println("\n" + "=".repeat(60));
            System.out.println("PLAYER INFO:");
            System.out.println("=".repeat(60));
            String playerInfo = getPlayerInfo("1229698220293230654");
            System.out.println(playerInfo);
            System.out.println("Character count: " + playerInfo.length());

        } catch (IOException e) {
            System.err.println("Error fetching or parsing data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}