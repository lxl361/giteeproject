package com.zd.giteeproject.controller;
import com.zd.giteeproject.dto.AccessTokenDTO;
import com.zd.giteeproject.dto.GiteeUser;
import com.zd.giteeproject.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizationController {
    @Autowired
    private GiteeProvider giteeProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           HttpServletRequest request)
    {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setGrant_type("authorization_code");
        accessTokenDTO.setClientId("381c6a3595b626ac533fcc2af52197559e6475982dcb6119455167f28e13bf60");
        accessTokenDTO.setClientSecret("e552e7ab8bf4eb939856c344f6af08fdc3a3aa8206a89046cb88d24587eb49d0");
        accessTokenDTO.setRedirectUri("http://localhost:8080/callback");
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser user = giteeProvider.getUser(accessToken);
        if (user!=null){
            //登录成功
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
