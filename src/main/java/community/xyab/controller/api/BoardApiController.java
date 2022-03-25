package community.xyab.controller.api;

import community.xyab.config.auth.PrincipalDetail;
import community.xyab.dto.board.BoardSaveRequestDto;
import community.xyab.dto.board.BoardUpdateRequestDto;
import community.xyab.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    // 게시글 작성
    @PostMapping("/api/v1/board")
    public Long save(@RequestBody BoardSaveRequestDto boardSaveRequestDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        return boardService.save(boardSaveRequestDto, principalDetail.getUser());
    }

    // 게시글 삭제
    @DeleteMapping("/api/v1/board/{id}")
    public Long delete(@PathVariable Long id) {
        boardService.delete(id);
        return id;
    }

    // 게시글 수정
    @PutMapping("/api/v1/board/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
       return boardService.update(boardUpdateRequestDto, id);
    }
}
