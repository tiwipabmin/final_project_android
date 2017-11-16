package kmitl.it13.millibear.eatallday.model;

/**
 * Created by tiwip on 11/16/2017.
 */

public class Friend {

    private boolean request;
    private boolean response;
    private String receiver;
    private String sender;

    public Friend() {
    }

    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
