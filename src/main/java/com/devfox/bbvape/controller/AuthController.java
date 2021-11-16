package com.devfox.bbvape.controller;

import com.devfox.bbvape.model.User;
import com.devfox.bbvape.service.Code;
import com.devfox.bbvape.service.UserService;
import com.devfox.bbvape.util.PageScriptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @RequestMapping("/user/insert")
    public void userInsert(
            HttpServletResponse response,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam boolean terms,
            @RequestParam(required = false) boolean marketing
    ) throws IOException {

        if (terms) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setMarketing(marketing);

            Code code = userService.signup(user);

            if (code.getCode() == 1) {
                PageScriptUtil.alertAndMove(response, code.getMsg(), "/signin");
            } if (code.getCode() == 2) {
                PageScriptUtil.alertAndMove(response, code.getMsg(), "/signin");
            } else {
                PageScriptUtil.alertAndBack(response, "문제가 발생하였습니다. 다시 진행해주세요.");
            }
        } else {
            PageScriptUtil.alertAndBack(response, "이용약관과 개인정보처리방침에 동의하지 않으셨습니다.");
        }
    }
}
