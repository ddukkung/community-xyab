package community.xyab.config.auth;

import community.xyab.domain.user.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class PrincipalDetail implements UserDetails {

    private User user;

    public PrincipalDetail(User user) {
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> user.getRoleKey());
        return collection;
    }

    // 사용자 패스워드
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 사용자 아이디
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되었는지
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있는지
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 패스워드가 만료되지 않았는지
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화되어 있는지
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 사용자 이메일
    public String getEmail() {
        return user.getEmail();
    }

    // 사용자 닉네임
    public String getNickname() {
        return user.getNickname();
    }

    // 사용자 pk
    public Long getId() {
        return user.getId();
    }

}
