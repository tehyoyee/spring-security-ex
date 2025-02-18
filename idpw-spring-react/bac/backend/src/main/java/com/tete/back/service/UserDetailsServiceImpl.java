package com.tete.back.service;
import com.tete.back.entity.User;
import com.tete.back.entity.UserDetailsImpl;
import com.tete.back.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("START loadUserByUsername");
        // 사용자 이름으로 사용자 검색
        try {
            User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException("User not found with username: " + username));
            System.out.println("END loadUserByUsername");
            return new UserDetailsImpl(
                    user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole().getAuthorities(),
                    user.getRole()// 권한 목록
            );
        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        // UserDetailsImpl 객체 생성하여 반환
        return null;
    }

}

