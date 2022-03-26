package community.xyab.service;

import community.xyab.domain.board.Board;
import community.xyab.domain.board.BoardRepository;
import community.xyab.domain.reply.Reply;
import community.xyab.domain.reply.ReplyRepository;
import community.xyab.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void replySave(Long boardId, Reply reply, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다. boardId=" + boardId));
        reply.save(board, user);
        replyRepository.save(reply);
    }

    public void delete(Long replyId) {
        replyRepository.deleteById(replyId);
    }
}
