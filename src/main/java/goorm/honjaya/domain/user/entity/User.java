package goorm.honjaya.domain.user.entity;

import goorm.honjaya.domain.base.BaseEntity;
import goorm.honjaya.domain.board.entity.Board;
import goorm.honjaya.domain.image.entity.ProfileImage;
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

    private boolean isMatched;

    private boolean isMatching; // 매칭 중 상태 추가


    @Setter
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileImage> profileImages = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Ideal ideal;

    @Builder
    private User(Long id, String username, String name, String token, String role, UserStatus status, Profile profile, Ideal ideal,  boolean isMatched, boolean isMatching) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.token = token;
        this.role = role;
        this.status = status;
        this.profile = profile;
        this.ideal = ideal;
        this.isMatched = isMatched;
        this.isMatching = isMatching;
    }

    public void addProfileImage(ProfileImage profileImage) {
        this.profileImages.add(profileImage);
        profileImage.setUser(this);
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public void setMatching(boolean matching) {
        isMatching = matching;
    }
}
