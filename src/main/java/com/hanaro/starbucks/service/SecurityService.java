package com.hanaro.starbucks.service;

import com.hanaro.starbucks.entity.Member;
import com.hanaro.starbucks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        List<Member> findUser = this.userRepository.findByUserId(userId);
        System.out.println(userId);
        if (findUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        Member user = findUser.get(0);

        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("ADMIN".equals(user.getUserRole())) {
            authorities.add(new SimpleGrantedAuthority(user.getUserRole()));
        } else {
            authorities.add(new SimpleGrantedAuthority(user.getUserRole()));
        }
        return new User(user.getUserId(), user.getUserPw(), authorities);
    }
}
