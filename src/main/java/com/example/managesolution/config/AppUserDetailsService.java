package com.example.managesolution.config;

import com.example.managesolution.data.domain.AppUser;
import com.example.managesolution.mapper.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserMapper appUserMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserMapper.findByEmail(email);
        if (appUser == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다 : " +email);
        }
        return new AppUserDetails(appUser);
    }
}
