package goorm.honjaya.domain.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private MessageType type;
    private String msg;
    private String sender;
    private String roomNum;
    private String isOwnMessage;
    private String createAt;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
//    private String id;
//    private String msg;
//    private String sender;
//    private String receiver;
//    private String roomNum;
//    private String isOwnMessage;
//    private String createAt;
}