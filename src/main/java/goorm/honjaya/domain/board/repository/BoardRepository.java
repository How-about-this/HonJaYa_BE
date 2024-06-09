package goorm.honjaya.domain.board.repository;

import goorm.honjaya.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
