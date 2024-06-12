package goorm.honjaya.domain.board.dto;

import goorm.honjaya.domain.board.entity.Board;
import goorm.honjaya.domain.board.entity.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BoardPageDto {
    private Long id;
    private String title;
    private String author;
    private Category category;
    private LocalDate date;


    @Builder
    private BoardPageDto(Long id, String title, String author, Category category, LocalDate date) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.date = date;
    }

    public static BoardPageDto from(Board board) {
        return BoardPageDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .author(board.getUser().getName())
                .category(board.getCategory())
                .date(board.getCreatedAt().toLocalDate())
                .build();

    }
}
