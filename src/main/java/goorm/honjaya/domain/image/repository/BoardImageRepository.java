package goorm.honjaya.domain.image.repository;

import goorm.honjaya.domain.board.entity.Board;
import goorm.honjaya.domain.image.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {

    List<BoardImage> findByBoard_Id(Long boardId);

}
