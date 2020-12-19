package online.chinascliuyitao.acm.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import online.chinascliuyitao.acm.dto.AccessTokenDTO;
import online.chinascliuyitao.acm.dto.GithubUser;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO)
    {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO),mediaType);
        System.out.println(JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string =  response.body().string();
            System.out.println(string);
            String tokenStr = string.split("&")[0].split("=")[1];
            return tokenStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken)
    {
        System.out.println(accessToken);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.github.com/user").header("Authorization","token "+accessToken).build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(string);
            GithubUser githubUser =  JSON.parseObject(string,GithubUser.class);
            return githubUser;
        }catch (IOException e)
        {

        }
        return null;

    }
}
