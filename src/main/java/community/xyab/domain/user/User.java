package community.xyab.domain.user;

import community.xyab.domain.BaseTimeEntity;
import community.xyab.domain.board.Board;
import community.xyab.domain.reply.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // 회원 탈퇴 시 게시글 모두 삭제
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boardList = new ArrayList<>();

    // 회원 탈퇴 시 댓글 모두 삭제
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    // 비밀번호 암호화 메소드
    public void setPassword(String password) {
        this.password = password;
    }

    // 권한 메소드
    public String getRoleKey() {
        return this.role.getKey();
    }

    public void update(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }
}
