package goorm.honjaya.domain.image.entity;

import goorm.honjaya.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("B")
public class BoardImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board")
    private Board board;

    @Builder
    private BoardImage(String originalImageName, String saveImageName, String extension,String imageUrl, Board board) {
        super(originalImageName, saveImageName, extension, imageUrl);
        this.board = board;
    }

}
