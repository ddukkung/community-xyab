package community.xyab.domain.reply;

import community.xyab.domain.board.Board;
import community.xyab.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String content;

    // 어느 게시글에 작성했는지
    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    // 누가 댓글을 작성했는지
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public void save(Board board, User user) {
        this.board = board;
        this.user = user;
    }
}
