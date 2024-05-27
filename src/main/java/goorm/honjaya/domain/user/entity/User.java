package goorm.honjaya.domain.user.entity;

import goorm.honjaya.domain.base.BaseEntity;
import goorm.honjaya.domain.board.entity.Board;
import goorm.honjaya.domain.image.entity.ProfileImage;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String name;

    @Setter
    private String token;

    private String role;

    private String status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileImage> profileImages = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Ideal ideal;

    @Builder
    private User(Long id, String username, String name, String token, String role, String status, Profile profile, Ideal ideal) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.token = token;
        this.role = role;
        this.status = status;
        this.profile = profile;
        this.ideal = ideal;
    }

    public void addProfileImage(ProfileImage profileImage) {
        this.profileImages.add(profileImage);
        profileImage.setUser(this);
    }
}
