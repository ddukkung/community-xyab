package community.xyab.config.auth;

import community.xyab.domain.user.User;
import community.xyab.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // DB에 username이 있는지 확인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username));
        // 시큐리티의 세션에 유저 정보 저장
        return new PrincipalDetail(principal);
    }
}
