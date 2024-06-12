package goorm.honjaya.domain.board.controller;

import goorm.honjaya.domain.board.service.BoardService;
import goorm.honjaya.domain.board.dto.BoardDto;
import goorm.honjaya.domain.board.dto.BoardPageDto;
import goorm.honjaya.global.auth.CustomOAuth2User;
import goorm.honjaya.global.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class BoardController {
    private final BoardService boardService;
    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //페이징
    @GetMapping("/boards")
    public Page<BoardPageDto> getBoards(
            @PageableDefault (page= 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "category", required = false) String category) {
        log.info("전체 게시글 조회");
        log.info(category);
        return boardService.getBoardsWithPagination(pageable, category);
    }

    //id별 조회
    @GetMapping("/boards/{id}")
    public ApiResponse<BoardDto> getBoardById(@PathVariable Long id) {
        BoardDto boardDto = boardService.findBoardById(id);
        log.info("{}의 게시글 조회", id);
        return ApiResponse.success(boardDto);
    }

    //저장
    @PostMapping("/boards")
    public ApiResponse<BoardDto> createBoard(@RequestBody BoardDto boardDto,@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        BoardDto board = boardService.save(boardDto, customOAuth2User);
        log.info("user:{} 저장",customOAuth2User);
        return ApiResponse.success(board);
    }

    //수정
    @PutMapping("/boards/{id}")
    public ApiResponse<?> updateBoard(@PathVariable Long id, @RequestBody BoardDto boardDto) {
        try{
            BoardDto updatedBoard = boardService.update(id, boardDto);
            log.info("{}아이디의 보드 수정완료",id);
            return ApiResponse.success(updatedBoard);
        }catch (Exception e){
            return ApiResponse.error("에러발생"+ e.getMessage());
        }
    }

    //삭제
    @DeleteMapping("/boards/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        log.info("삭제 완료");
    }
}
