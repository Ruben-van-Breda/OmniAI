package omni.chat;

public class ChatQueryResponse {
    public String reference;
    public String queryResponse;

    public ChatQueryResponse(String reference, String queryResponse) {
        this.reference = reference;
        this.queryResponse = queryResponse;
    }

    public ChatQueryResponse() {}

    @Override
    public String toString() {
        return "{ Ref: \"" + reference + "\". Response: \"" + queryResponse + "\" } ";
    }
}
