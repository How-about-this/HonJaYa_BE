package goorm.honjaya.domain.board.dto;

import goorm.honjaya.domain.board.entity.Board;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String username;


    public static BoardDto toDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .username(board.getUser().getUsername())
                .build();
    }

}
