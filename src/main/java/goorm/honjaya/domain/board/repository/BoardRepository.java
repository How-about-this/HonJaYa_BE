package goorm.honjaya.domain.board.repository;

import goorm.honjaya.domain.board.entity.Board;
import goorm.honjaya.domain.board.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByCategory(Pageable pageable, Category category);
}
