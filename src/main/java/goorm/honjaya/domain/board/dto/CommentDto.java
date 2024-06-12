package goorm.honjaya.domain.board.dto;

import goorm.honjaya.domain.board.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

        private Long id;
        private String content;
        private String author;
        private LocalDateTime date;
        private Long parentId;

        public static CommentDto CommentToDto(Comment comment) {
                return CommentDto.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .author(comment.getUser().getName())
                        .date(comment.getCreatedAt())
                        .parentId(comment.getParentComment() != null ?comment.getParentComment().getId():null)
                        .build();
        }

}
