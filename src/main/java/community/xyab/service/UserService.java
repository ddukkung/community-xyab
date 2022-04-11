package community.xyab.service;

import community.xyab.config.auth.PrincipalDetail;
import community.xyab.domain.user.User;
import community.xyab.domain.user.UserRepository;
import community.xyab.dto.user.UserDeleteRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long save(User user) {
        String hashPw = encoder.encode(user.getPassword());
        user.setPassword(hashPw); // 암호화된 패스워드를 db에 저장
        return userRepository.save(user).getId();
    }

    @Transactional
    public Long update(User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        User userEntity = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. id=" + user.getId()));
        userEntity.update(encoder.encode(user.getPassword()), user.getNickname());
        principalDetail.setUser(userEntity);
        return userEntity.getId();
    }

    public void delete(UserDeleteRequestDto userDeleteRequestDto) {
        User user = userRepository.findById(userDeleteRequestDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        if (encoder.matches(userDeleteRequestDto.getPassword(), user.getPassword())) {
            userRepository.deleteById(userDeleteRequestDto.getId());
            SecurityContextHolder.clearContext();
        } else {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }
    }
}
