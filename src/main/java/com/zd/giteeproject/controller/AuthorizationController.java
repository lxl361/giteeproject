package com.zd.giteeproject.controller;
import com.zd.giteeproject.dto.AccessTokenDTO;
import com.zd.giteeproject.dto.GiteeUser;
import com.zd.giteeproject.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
@Controller
public class AuthorizationController {
    @Autowired
    private GiteeProvider giteeProvider;
    @Value("${gitee.client.id}")
    private String clientId;
    @Value("${gitee.client.secret}")
    private String clientSecret;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           HttpServletRequest request)
    {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setGrant_type("authorization_code");
        accessTokenDTO.setClientId(clientId);
        accessTokenDTO.setClientSecret(clientSecret);
        accessTokenDTO.setRedirectUri("http://localhost:8080/callback");
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser user = giteeProvider.getUser(accessToken);
        if (user!=null){
            //登录成功
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        }else {
            //销毁session
            request.getSession().invalidate();
            return "redirect:/";
        }
    }
}
