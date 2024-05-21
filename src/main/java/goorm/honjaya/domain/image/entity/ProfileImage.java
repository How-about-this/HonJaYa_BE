package goorm.honjaya.domain.image.entity;

import goorm.honjaya.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("P")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public ProfileImage(String imageUrl) {
        super(imageUrl);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
