package goorm.honjaya.domain.member.entity;

import goorm.honjaya.domain.base.BaseEntity;
import goorm.honjaya.domain.board.entity.Board;
import goorm.honjaya.domain.image.entity.ProfileImage;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String token;

    private String role;

    private String status;

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ProfileImage> profileImages = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private Profile profile;

    @OneToOne(mappedBy = "member")
    private Ideal ideal;
}
