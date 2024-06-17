package goorm.honjaya.domain.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity // JPA 엔티티로 선언
@Getter
@Setter
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MessageType type;
    private String msg;
    private String sender;
    private String roomNum;
    private String isOwnMessage;
    private String createdAt;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}
