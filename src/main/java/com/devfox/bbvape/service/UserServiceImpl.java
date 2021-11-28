package com.devfox.bbvape.service;

import com.devfox.bbvape.model.Setting;
import com.devfox.bbvape.model.User;
import com.devfox.bbvape.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SettingService settingService;

    @Override
    public Optional<User> findUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Code signup(User user) {
        Code code = new Code();

        Optional<User> userCheck = findUser(user.getUsername());

        Setting setting = settingService.loadSetting();

        if (userCheck.isEmpty()) {

            User userInfo = new User();

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String email = user.getUsername();
            int s = email.indexOf("@");
            String nickname = email.substring(0, s);

            userInfo.setUsername(user.getUsername());
            userInfo.setNickname(nickname);
            userInfo.setPoint(setting.getSitePointSignup());
            userInfo.setRole("ROLE_REGISTER");
            userInfo.setPassword(encoder.encode(user.getPassword()));
            userInfo.setAdult(false);
            userInfo.setAccountNonExpired(true);
            userInfo.setAccountNonLocked(true);
            userInfo.setCredentialsNonExpired(true);
            userInfo.setEnabled(true);
            userInfo.setMarketing(user.isMarketing());
            userInfo.setRegdate(new Date());

            User result = userRepository.save(userInfo);

            if (user.getUsername().equals(result.getUsername())) {
                code.setCode(1);
                code.setMsg("회원가입을 축하드립니다. 로그인해주세요.");
            }
        } else {
            code.setCode(2);
            code.setMsg("이미 가입되셨습니다. 로그인해주세요.");
        }

        return code;
    }
}
