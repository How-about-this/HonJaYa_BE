package goorm.honjaya.domain.chat.entity;

import goorm.honjaya.domain.base.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Chat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;
}
