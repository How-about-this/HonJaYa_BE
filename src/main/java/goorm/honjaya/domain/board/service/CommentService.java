package goorm.honjaya.domain.board.service;

import goorm.honjaya.domain.board.dto.CommentDto;
import goorm.honjaya.domain.board.entity.Board;
import goorm.honjaya.domain.board.entity.Comment;
import goorm.honjaya.domain.board.repository.BoardRepository;
import goorm.honjaya.domain.board.repository.CommentRepository;
import goorm.honjaya.domain.user.entity.User;
import goorm.honjaya.domain.user.repository.UserRepository;
import goorm.honjaya.global.auth.CustomOAuth2User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    //댓글 조회
//    public List<CommentDto> getAllCommentList(Long boardsId, Long commentsId){
//        boardRepository.findById(boardsId).orElseThrow(
//                () -> new IllegalArgumentException("아이디 존재하지 않음"));
//        List<Comment> comments = commentRepository.findByBoardIdOrderByModifiedAtDesc(commentsId);
//        log.info("게시판 아이디: {}", boardsId);
//        return comments.stream()
//                .map(CommentDto::CommentToDto)
//                .collect(Collectors.toList());
//    }
    public List<CommentDto> getAllCommentList(Long boardsId){
        boardRepository.findById(boardsId).orElseThrow(
                () -> new IllegalArgumentException("아이디 존재하지 않음"));
        List<Comment> comments = commentRepository.findByBoardIdOrderByCreatedAt(boardsId);
        log.info("게시판 아이디: {}", boardsId);
        log.info(comments.toString());
        return comments.stream()
                .map(CommentDto::CommentToDto)
                .collect(Collectors.toList());
    }

    //댓글 생성
    //댓글 Id가 있으면 parentId가 되고 childComment 생성
    @Transactional
    public CommentDto saveComment(CommentDto commentDto, Long boardsId, CustomOAuth2User customOAuth2User) {
        String username = customOAuth2User.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("user 존재하지 않음"));
        Board board = boardRepository.findById(boardsId).orElseThrow(
                () -> new IllegalArgumentException("아이디 존재하지 않음"));
        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setUser(user);
        comment.setContent(commentDto.getContent());

        if(commentDto.getParentId()!= null) {
            Comment parentComment = commentRepository.findById(commentDto.getParentId()).orElseThrow(
                    () -> new IllegalArgumentException("부모 댓글이 존재하지 않음"));
            parentComment.addChildComment(comment);
        }
        commentRepository.save(comment);
        return CommentDto.CommentToDto(comment);
    }

    // 댓글 수정
    @Transactional
    public CommentDto updateComment(CommentDto commentDto, Long commentsId) {

        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                () -> new IllegalArgumentException("댓글 아이디가 존재하지 않음"));

        comment.setContent(commentDto.getContent());

        if (commentDto.getParentId() != null) {
            Comment parentComment = commentRepository.findById(commentDto.getParentId()).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않음"));
            comment.setParentComment(parentComment);
        } else {
            comment.setParentComment(null);
        }
        commentRepository.save(comment);
        return CommentDto.CommentToDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글 아이디가 존재하지 않음"));
        if (comment.getParentComment() != null) {
            comment.getParentComment().getChildren().remove(comment);
        }
        commentRepository.delete(comment);
    }
}
