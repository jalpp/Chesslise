public class Blog {

    private String name;
    private String topic;
    private String url;
    private int year;


    public Blog(String topic){
        this.topic = topic;
    }

    public Blog(int year){
        this.year = year;
    }

    public Blog(String name, String topic, String url){
        this.name = name;
        this.topic = topic;
        this.url = url;
    }

    public String getLatestBlog(){
        return "https://lichess.org/blog";
    }

    public String getBlogsByTopic(){
        return "https://lichess.org/blog/topic/" + this.topic;
    }

    public String getCommunityBlogs(){
        return "https://lichess.org/blog/community";
    }









}

