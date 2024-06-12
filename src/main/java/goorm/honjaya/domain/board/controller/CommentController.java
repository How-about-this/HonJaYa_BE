package goorm.honjaya.domain.board.controller;

import goorm.honjaya.domain.board.Service.CommentService;
import goorm.honjaya.domain.board.dto.CommentDto;
import goorm.honjaya.global.auth.CustomOAuth2User;
import goorm.honjaya.global.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 조회
    @GetMapping("/boards/{boardsId}/comments")
    public ApiResponse<List<CommentDto>> getAllComments(@PathVariable Long boardsId) {
        List<CommentDto> comments = commentService.getAllCommentList(boardsId);
        return ApiResponse.success(comments);
    }

    //댓글 생성
    @PostMapping("/boards/{boardsId}/comments")
    public ApiResponse<CommentDto> saveComment(@RequestBody CommentDto commentDto,
                                               @PathVariable Long boardsId,
                                               @AuthenticationPrincipal CustomOAuth2User customOAuth2User){
        CommentDto saverComment = commentService.saveComment(commentDto, boardsId, customOAuth2User);
        return ApiResponse.success(saverComment);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentsId}")
    public ApiResponse<CommentDto> updateComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable Long commentsId) {
        CommentDto updatedComment = commentService.updateComment(commentDto, commentsId);
        return ApiResponse.success(updatedComment);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentsId}")
    public ApiResponse<?> deleteComment(@PathVariable Long commentsId) {
        commentService.deleteComment(commentsId);
        return ApiResponse.success();
    }

}
