package com.telran.borislav.hairsalonclientproject.providers;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Boris on 12.04.2017.
 */

public class Provider {
    private static final String BASE_URL = "https://hair-salon-personal.herokuapp.com/";
    private static Provider instance = null;

    private Provider() {
    }

    public static Provider getInstance() {
        if (instance == null) {
            instance = new Provider();
        }
        return instance;
    }

    public Response post(String path, String data, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(type, data);
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .post(requestBody)
                .addHeader("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }

    public Response put(String path, String data, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(type, data);
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .put(requestBody)
                .addHeader("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }

    public Response get(String path, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .get()
                .addHeader("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }
}
