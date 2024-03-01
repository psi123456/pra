package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.BoardDeleteRequest;
import com.example.demo.BoardUpdateRequest;

@RestController
@RequestMapping("/boards")
public class BoardController {
    
    @Autowired
    private BoardService boardService;

    // 게시글 생성
    @PostMapping
    public Board createBoard(@RequestBody Board board) {
        return boardService.saveBoard(board);
    }

    // 게시글 목록 조회
    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }
    @PutMapping("/{id}/view")
    public ResponseEntity<?> increaseViewCount(@PathVariable Long id) {
        Board board = boardService.findById(id);
        if (board == null) {
            return ResponseEntity.notFound().build();
        }
        board.setViewCount(board.getViewCount() + 1);
        boardService.saveBoard(board);
        return ResponseEntity.ok().build();
    }
    
 // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id, @RequestBody BoardDeleteRequest deleteRequest) {
        boolean isDeleted = boardService.deleteBoardIfPasswordMatches(id, deleteRequest.getPassword());
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("비밀번호가 일치하지 않습니다.");
        }
    }
    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable Long id, @RequestBody BoardUpdateRequest updateRequest) {
        boolean isUpdated = boardService.updateBoardIfPasswordMatches(id, updateRequest);
        if (isUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("비밀번호가 일치하지 않습니다.");
        }
    }
   

    
}
