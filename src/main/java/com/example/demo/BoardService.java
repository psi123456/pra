package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.BoardUpdateRequest;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

    // 게시글 저장 메서드, 비밀번호 해싱 없음
 	public Board saveBoard(Board board) {
 		return boardRepository.save(board);
 	}

    // 게시글 목록 조회
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    // 게시글 ID로 조회
    public Board findById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    // 게시글 업데이트, 비밀번호 평문 비교
    public boolean updateBoardIfPasswordMatches(Long id, BoardUpdateRequest updateRequest) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            if (board.getPassword().equals(updateRequest.getPassword())) {
                board.setTitle(updateRequest.getTitle());
                board.setContent(updateRequest.getContent());
                board.setUsername(updateRequest.getUsername()); // username 업데이트 로직 추가
                boardRepository.save(board);
                return true;
            }
        }
        return false;
    }

    // 게시글 삭제, 비밀번호 평문 비교
    public boolean deleteBoardIfPasswordMatches(Long id, String inputPassword) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (!boardOptional.isPresent()) {
            return false;
        }
        Board board = boardOptional.get();

        // 비밀번호 평문 비교
        if (inputPassword.equals(board.getPassword())) {
            boardRepository.delete(board);
            return true;
        }
        return false;
    }

}
