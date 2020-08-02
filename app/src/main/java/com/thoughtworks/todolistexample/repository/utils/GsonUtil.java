package com.thoughtworks.todolistexample.repository.utils;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GsonUtil {
    public static  <T> T getData(String url, Class<T> klass) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();

        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }
        return new Gson().fromJson(body.string(), klass);
    }
}
