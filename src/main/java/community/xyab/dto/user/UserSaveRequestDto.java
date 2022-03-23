package community.xyab.dto.user;

import community.xyab.domain.user.Role;
import community.xyab.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserSaveRequestDto {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private Role role;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .role(Role.USER)
                .build();
    }
}
