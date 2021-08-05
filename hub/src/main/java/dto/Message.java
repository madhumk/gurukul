package dto;

public class Message {

    private int messageId;
    private String senderId;
    private String MDN;
    private String body;



    public Message(int messageId, String senderId, String MDN, String body) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.MDN = MDN;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", senderId='" + senderId + '\'' +
                ", MDN='" + MDN + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMDN() {
        return MDN;
    }

    public void setMDN(String MDN) {
        this.MDN = MDN;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
