package community.xyab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    // 회원가입
    @GetMapping("/auth/user/save")
    public String userSave() {
        return "layout/user/user-save";
    }

    // 로그인
    @GetMapping("/auth/user/login")
    public String userLogin() {
        return "layout/user/user-login";
    }

    // 회원정보 수정
    @GetMapping("/user/update")
    public String userUpdate() {
        return "layout/user/user-update";
    }

    // 회원 탈퇴
    @GetMapping("/user/delete")
    public String userDelete() {
        return "layout/user/user-delete";
    }
}
