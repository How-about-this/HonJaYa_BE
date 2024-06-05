package goorm.honjaya.domain.image.entity;

import goorm.honjaya.domain.board.entity.Board;
import goorm.honjaya.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql = "update ProfileImage set deleted = true where id = ?")
@DiscriminatorValue("P")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    private Boolean deleted = Boolean.FALSE;

    @Builder
    private ProfileImage(String originalImageName, String saveImageName, String extension,String imageUrl, User user) {
        super(originalImageName, saveImageName, extension, imageUrl);
        this.user = user;
    }

}
