package com.devfox.bbvape.service;

import com.devfox.bbvape.model.User;
import com.devfox.bbvape.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VapeUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        User foundUser = user.orElseThrow(() -> new UsernameNotFoundException(username));
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("없는 유저입니다. username : " + username);
        } else {
            return foundUser;
        }

    }

}
