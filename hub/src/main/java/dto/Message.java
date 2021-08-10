package dto;

import javax.validation.constraints.NotNull;
import java.util.Date;


public class Message {
    @NotNull(message = "Message Id is mandatory")
    private String messageId;
    @NotNull(message = "SenderId is mandatory")
    private String senderId;
    @NotNull(message = "MDN is mandatory")
    private String MDN;
    @NotNull(message = "Message Body is mandatory")
    private String body;

    public Date getAcceptedTime() {
        return acceptedTime;
    }

    public void setAcceptedTime(Date acceptedTime) {
        this.acceptedTime = acceptedTime;
    }

    private Date  acceptedTime;




    public Message(String messageId, String senderId, String MDN, String body) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.MDN = MDN;
        this.body = body;
    }


    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
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

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", senderId='" + senderId + '\'' +
                ", MDN='" + MDN + '\'' +
                ", body='" + body + '\'' +
                ", acceptedTime=" + acceptedTime +
                '}';
    }
}
