package omni.chat;

import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class ChatServiceResponse {
    public ChatQuery query;
    public List<ChatQueryResponse> queryResponses;

    public ChatServiceResponse() {}

    public ChatServiceResponse(ChatQuery query, List<ChatQueryResponse> queryResponses) {
        this.query = query;
        this.queryResponses = queryResponses;
    }

    @Override
    public String toString() {
        return "ChatServiceResponse: { Query: \"" + query + "\". Responses: " + StringUtils.join(queryResponses, ";") + " } ";
    }
}
