package com.zd.giteeproject.provider;
import com.alibaba.fastjson.JSON;
import com.zd.giteeproject.dto.AccessTokenDTO;
import com.zd.giteeproject.dto.GiteeUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class GiteeProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token?grant_type=authorization_code&"+"code="+accessTokenDTO.getCode()+"&"+"client_id="+accessTokenDTO.getClientId()+"&"+"redirect_uri="+accessTokenDTO.getRedirectUri()+"&"+"client_secret="+accessTokenDTO.getClientSecret())
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String accessToken = string.split(",")[0].split(":")[1];
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GiteeUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token="+ accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GiteeUser giteeUser = JSON.parseObject(string, GiteeUser.class);
            System.out.println("giteeUser:"+giteeUser);
            return giteeUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
