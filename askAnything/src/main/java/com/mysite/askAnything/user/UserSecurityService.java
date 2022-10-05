package com.mysite.askAnything.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.build.Plugin;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        SiteUser siteUser = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)){
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
            log.info("관리자 {}님이 로그인하였습니다.", siteUser.getUsername());
        }else if ("manager".equals(username)){
            authorities.add(new SimpleGrantedAuthority(UserRole.MANAGER.getValue()));
            log.info("매니저 {}님이 로그인하였습니다.", siteUser.getUsername());
        }else{
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
            log.info("사용자 {}님이 로그인하였습니다.", siteUser.getUsername());
        }
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}

