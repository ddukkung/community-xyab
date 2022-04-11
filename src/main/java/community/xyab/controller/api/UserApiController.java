package community.xyab.controller.api;

import community.xyab.config.auth.PrincipalDetail;
import community.xyab.domain.user.User;
import community.xyab.dto.user.UserDeleteRequestDto;
import community.xyab.dto.user.UserSaveRequestDto;
import community.xyab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    // 회원 저장
    @PostMapping("/auth/api/v1/user")
    public Long save(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        return userService.save(userSaveRequestDto.toEntity());
    }

    // 회원 정보 수정
    @PutMapping("/api/v1/user")
    public Long update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        return userService.update(user, principalDetail);
    }

    // 회원 탈퇴
    @DeleteMapping("/api/v1/user")
    public void delete(@RequestBody UserDeleteRequestDto userDeleteRequestDto) {
        userService.delete(userDeleteRequestDto);
    }
}
