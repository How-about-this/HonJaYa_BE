package goorm.honjaya.domain.board.dto;

import goorm.honjaya.domain.board.entity.Board;
import goorm.honjaya.domain.board.entity.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private Long authorId;
    private Category category;
    private LocalDate date;


    public static BoardDto toDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getUser().getName())
                .authorId(board.getUser().getId())
                .category(board.getCategory())
                .date(board.getCreatedAt().toLocalDate())
                .build();
    }

}
