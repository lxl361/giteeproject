package com.zd.giteeproject.provider;
import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zd.giteeproject.dto.AccessTokenDTO;
import com.zd.giteeproject.dto.GiteeUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

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
            JsonParser jp = new JsonParser();
            //将字符串解析成JSON对象
            JsonObject jo = jp.parse(string).getAsJsonObject();
            //获取access_token值
            String accessToken = jo.get("access_token").getAsString();
            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GiteeUser getUser(String access_token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token="+access_token)
                .addHeader("Authorization", "access_token"+access_token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GiteeUser giteeUser = JSON.parseObject(string,GiteeUser.class);
            return giteeUser;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
