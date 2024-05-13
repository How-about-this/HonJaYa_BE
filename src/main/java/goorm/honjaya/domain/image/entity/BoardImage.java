package goorm.honjaya.domain.image.entity;

import goorm.honjaya.domain.board.entity.Board;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("B")
public class BoardImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board")
    private Board board;
}
