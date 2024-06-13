package goorm.honjaya.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
        private Long authorId;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime date;
        private Long parentId;

        public static CommentDto CommentToDto(Comment comment) {
                return CommentDto.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .author(comment.getUser().getName())
                        .authorId(comment.getUser().getId())
                        .date(comment.getCreatedAt())
                        .parentId(comment.getParentComment() != null ?comment.getParentComment().getId():null)
                        .build();
        }

}
