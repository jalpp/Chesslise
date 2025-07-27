package discord.helpermodules.logic;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import discord.helpermodules.chess.ChessBoardManager;
import discord.helpermodules.data.PuzzleDataService;

/**
 * Service class for validating puzzle moves and managing puzzle state
 * Handles the core business logic for puzzle move validation
 */
public class PuzzleMoveValidator {

    /**
     * Result class that encapsulates the outcome of move validation
     */
    public static class ValidationResult {
        private final boolean isCorrect;
        private final boolean isPuzzleComplete;
        private final String newFen;
        private final String originalFen;
        private final String userMove;
        private final String opponentMove;
        private final int stepsLeft;
        private final String message;
        private final ValidationError error;

        private ValidationResult(Builder builder) {
            this.isCorrect = builder.isCorrect;
            this.isPuzzleComplete = builder.isPuzzleComplete;
            this.newFen = builder.newFen;
            this.originalFen = builder.originalFen;
            this.userMove = builder.userMove;
            this.opponentMove = builder.opponentMove;
            this.stepsLeft = builder.stepsLeft;
            this.message = builder.message;
            this.error = builder.error;
        }

        // Getters
        public boolean isCorrect() {
            return isCorrect;
        }

        public boolean isPuzzleComplete() {
            return isPuzzleComplete;
        }

        public String getNewFen() {
            return newFen;
        }

        public String getOriginalFen() {
            return originalFen;
        }

        public String getUserMove() {
            return userMove;
        }

        public String getOpponentMove() {
            return opponentMove;
        }

        public int getStepsLeft() {
            return stepsLeft;
        }

        public String getMessage() {
            return message;
        }

        public ValidationError getError() {
            return error;
        }

        public boolean hasError() {
            return error != null;
        }

        // Builder pattern for cleaner construction
        public static class Builder {
            private boolean isCorrect = false;
            private boolean isPuzzleComplete = false;
            private String newFen = "";
            private String originalFen = "";
            private String userMove = "";
            private String opponentMove = "";
            private int stepsLeft = 0;
            private String message = "";
            private ValidationError error = null;

            public Builder correct(boolean correct) {
                this.isCorrect = correct;
                return this;
            }

            public Builder complete(boolean complete) {
                this.isPuzzleComplete = complete;
                return this;
            }

            public Builder newFen(String fen) {
                this.newFen = fen;
                return this;
            }

            public Builder originalFen(String fen) {
                this.originalFen = fen;
                return this;
            }

            public Builder userMove(String move) {
                this.userMove = move;
                return this;
            }

            public Builder opponentMove(String move) {
                this.opponentMove = move;
                return this;
            }

            public Builder stepsLeft(int steps) {
                this.stepsLeft = steps;
                return this;
            }

            public Builder message(String msg) {
                this.message = msg;
                return this;
            }

            public Builder error(ValidationError err) {
                this.error = err;
                return this;
            }

            public ValidationResult build() {
                return new ValidationResult(this);
            }
        }
    }

    /**
     * Enumeration of possible validation errors
     */
    public enum ValidationError {
        INVALID_MOVE_FORMAT("Invalid move format. Please use format like 'e2e4' or 'e7e8q'."),
        NO_ACTIVE_PUZZLE("You haven't generated a puzzle yet. Use the `/puzzle` command to get started!"),
        DATABASE_ERROR("An error occurred while accessing puzzle data. Please try again."),
        MOVE_EXECUTION_ERROR("An error occurred while processing the move. Please verify the move is legal."),
        PUZZLE_STATE_ERROR("The puzzle is in an invalid state. Please generate a new puzzle.");

        private final String message;

        ValidationError(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Validate a user's move against the puzzle solution
     *
     * @param move   The user's move in UCI format (e.g., "e2e4", "e7e8q")
     * @param userId The Discord user ID
     * @return ValidationResult containing the outcome of the validation
     */
    public static ValidationResult validateMove(String move, String userId) {
        try {
            // Step 1: Validate move format
            if (!ChessBoardManager.isValidUciFormat(move)) {
                return new ValidationResult.Builder()
                        .error(ValidationError.INVALID_MOVE_FORMAT)
                        .message(ValidationError.INVALID_MOVE_FORMAT.getMessage())
                        .build();
            }

            // Step 2: Get puzzle data from database
            JSONObject solutionData = PuzzleDataService.getUserGeneratedPuzzleData(userId);

            if (!(boolean) solutionData.get("success")) {
                return new ValidationResult.Builder()
                        .error(ValidationError.NO_ACTIVE_PUZZLE)
                        .message(ValidationError.NO_ACTIVE_PUZZLE.getMessage())
                        .build();
            }

            // Step 3: Extract puzzle information
            JSONObject data = (JSONObject) solutionData.get("data");
            @SuppressWarnings("unchecked")
            List<String> solution = (List<String>) data.get("solution");
            String currentFen = (String) data.get("fen");

            // Step 4: Validate puzzle state
            if (solution == null || solution.isEmpty() || currentFen == null) {
                return new ValidationResult.Builder()
                        .error(ValidationError.PUZZLE_STATE_ERROR)
                        .message(ValidationError.PUZZLE_STATE_ERROR.getMessage())
                        .build();
            }

            // Step 5: Check if user's move matches the expected solution
            String expectedMove = solution.get(0);
            if (!move.equalsIgnoreCase(expectedMove)) {
                int stepsLeft = calculateStepsLeft(solution.size() / 2 + 1);
                return new ValidationResult.Builder()
                        .correct(false)
                        .originalFen(currentFen)
                        .stepsLeft(stepsLeft)
                        .message("❌ Incorrect Move")
                        .build();
            }

            // Step 6: Process correct move
            return processCorrectMove(currentFen, solution, userId);

        } catch (Exception e) {
            System.err.println("Error validating move for user " + userId + ": " + e.getMessage());
            e.printStackTrace();

            return new ValidationResult.Builder()
                    .error(ValidationError.DATABASE_ERROR)
                    .message(ValidationError.DATABASE_ERROR.getMessage())
                    .build();
        }
    }

    /**
     * Process a correct move and update puzzle state
     */
    private static ValidationResult processCorrectMove(String currentFen, List<String> solution, String userId) {
        try {
            String userMove = solution.get(0);

            // Apply user's move to get new position
            String newFen = ChessBoardManager.generateFEN(currentFen, userMove);

            if (solution.size() <= 1) {
                // Puzzle completed - only user move remaining
                PuzzleDataService.deleteUserGeneratedPuzzle(userId);

                return new ValidationResult.Builder()
                        .correct(true)
                        .complete(true)
                        .newFen(newFen)
                        .originalFen(currentFen)
                        .userMove(userMove)
                        .stepsLeft(0)
                        .message("🎉 Puzzle Completed!")
                        .build();
            }

            // Continue puzzle - apply opponent's response
            String opponentMove = solution.get(1);
            String finalFen = ChessBoardManager.generateFEN(newFen, opponentMove);

            // Update puzzle with remaining moves
            List<String> remainingMoves = new ArrayList<>(solution.subList(2, solution.size()));
            JSONObject updateResult = PuzzleDataService.updateGeneratedPuzzleData(finalFen, remainingMoves, userId);

            if (!(boolean) updateResult.get("success")) {
                throw new RuntimeException("Failed to update puzzle data");
            }

            int stepsLeft = calculateStepsLeft(remainingMoves.size() / 2 + 1);

            return new ValidationResult.Builder()
                    .correct(true)
                    .complete(false)
                    .newFen(finalFen)
                    .originalFen(currentFen)
                    .userMove(userMove)
                    .opponentMove(opponentMove)
                    .stepsLeft(stepsLeft)
                    .message("✅ Correct Move!")
                    .build();

        } catch (Exception e) {
            System.err.println("Error processing correct move: " + e.getMessage());
            e.printStackTrace();

            return new ValidationResult.Builder()
                    .error(ValidationError.MOVE_EXECUTION_ERROR)
                    .message(ValidationError.MOVE_EXECUTION_ERROR.getMessage())
                    .build();
        }
    }

    /**
     * Calculate remaining steps in the puzzle
     * Each "step" represents a user move (opponent moves don't count as steps)
     */
    private static int calculateStepsLeft(int remainingMoves) {
        // In a puzzle, moves alternate: user move, opponent response, user move, etc.
        // So steps left = (remaining moves + 1) / 2
        return (remainingMoves + 1) / 2;
    }

    /**
     * Check if user has an active puzzle
     *
     * @param userId The Discord user ID
     * @return true if the user has an active puzzle
     */
    public static boolean hasActivePuzzle(String userId) {
        try {
            JSONObject solutionData = PuzzleDataService.getUserGeneratedPuzzleData(userId);
            return (boolean) solutionData.get("success");
        } catch (Exception e) {
            System.err.println("Error checking active puzzle for user " + userId + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the current puzzle state for a user
     *
     * @param userId The Discord user ID
     * @return JSONObject containing puzzle data, or null if no active puzzle
     */
    public static JSONObject getCurrentPuzzleState(String userId) {
        try {
            JSONObject solutionData = PuzzleDataService.getUserGeneratedPuzzleData(userId);
            if ((boolean) solutionData.get("success")) {
                return (JSONObject) solutionData.get("data");
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error getting puzzle state for user " + userId + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Get the number of moves remaining in the current puzzle
     *
     * @param userId The Discord user ID
     * @return Number of moves remaining, or -1 if no active puzzle
     */
    public static int getRemainingMoves(String userId) {
        try {
            JSONObject puzzleState = getCurrentPuzzleState(userId);
            if (puzzleState != null) {
                @SuppressWarnings("unchecked")
                List<String> solution = (List<String>) puzzleState.get("solution");
                return solution != null ? solution.size() : -1;
            }
            return -1;
        } catch (Exception e) {
            System.err.println("Error getting remaining moves for user " + userId + ": " + e.getMessage());
            return -1;
        }
    }
}
