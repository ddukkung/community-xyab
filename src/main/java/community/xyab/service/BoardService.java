package community.xyab.service;

import community.xyab.domain.board.Board;
import community.xyab.domain.board.BoardRepository;
import community.xyab.domain.user.User;
import community.xyab.dto.board.BoardSaveRequestDto;
import community.xyab.dto.board.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시글 저장
    @Transactional
    public Long save(BoardSaveRequestDto boardSaveRequestDto, User user) {
        boardSaveRequestDto.setUser(user);
        return boardRepository.save(boardSaveRequestDto.toEntity()).getId();
    }

    // 게시글 목록 조회
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public Object detail(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    // 게시글 수정
    @Transactional
    public Long update(BoardUpdateRequestDto boardUpdateRequestDto, Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        board.update(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContent());
        return id;
    }

    // 게시글 조회수 증가
    @Transactional
    public void updateCount(Long id) {
        boardRepository.updateCount(id);
    }
}
