package com.tegeltech.jenkinscommits.http;

import com.tegeltech.jenkinscommits.exception.CommunicationException;
import lombok.AllArgsConstructor;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@AllArgsConstructor
public class HttpClient {

    private OkHttpClient httpClient;

    public String get(String url)  {
        Request request = new Request.Builder().url(url).build();
        Call call = httpClient.newCall(request);
        try {
            Response response = call.execute();
            if(response.code()!=200) {
                throw new CommunicationException("HTTP " + response.code() + " " + response.message());
            }
            return response.body().string();
        } catch (IOException e) {
            throw new CommunicationException("Failed to execute request", e);
        }
    }
}
