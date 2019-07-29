package com.huang.community.commons;

import com.alibaba.fastjson.JSON;
import com.huang.community.dto.AccessTokenDTO;
import com.huang.community.entity.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubAuthorizeHelper {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        String res = null;
        MediaType JSONType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSONType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            res = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (res.split("&")[0]).split("=")[1];
    }

    public GithubUser getGithubUser(String accessToken) {
        GithubUser githubUser = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            githubUser = JSON.parseObject(response.body().string(), GithubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return githubUser;
    }
}
