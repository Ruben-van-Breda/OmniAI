package omni.chat;

public class ChatQuery {

    private String query;

    public ChatQuery() {};

    public ChatQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return query;
    }
}
