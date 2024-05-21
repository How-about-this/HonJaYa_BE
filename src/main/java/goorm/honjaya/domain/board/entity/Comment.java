package goorm.honjaya.domain.board.entity;

import goorm.honjaya.domain.base.BaseEntity;
import goorm.honjaya.domain.user.entity.User;
import jakarta.persistence.*;

@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borad_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
