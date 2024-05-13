package goorm.honjaya.domain.chat.entity;

import goorm.honjaya.domain.base.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;
}
