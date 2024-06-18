package goorm.honjaya.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Setter
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

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matched_user_id")
    private User matchedUser;

    @Builder
    public User(Long id, String username, String name, String token, String role, UserStatus status, Profile profile, Ideal ideal, boolean isMatched, boolean isMatching, User matchedUser) {
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
        this.matchedUser = matchedUser;
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
    public void setMatchedUser(User matchedUser) {
        this.matchedUser = matchedUser;
    }
}
