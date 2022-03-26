package community.xyab.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import community.xyab.domain.BaseTimeEntity;
import community.xyab.domain.reply.Reply;
import community.xyab.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터를 저장할 때 사용
    private String content;

    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OrderBy("id desc")
    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Reply> replyList;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
