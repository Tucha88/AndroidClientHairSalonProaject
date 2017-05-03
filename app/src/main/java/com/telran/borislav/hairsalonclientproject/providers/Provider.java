package com.telran.borislav.hairsalonclientproject.providers;

import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Boris on 12.04.2017.
 */

public class Provider {
    private static final String BASE_URL = "https://hair-salon-personal.herokuapp.com/";
    private static Provider instance = null;
    private Provider(){
    }

    public static Provider getInstance(){
        if(instance == null){
            instance = new Provider();
        }
        return instance;
    }

    public Response post(String path, String data, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
//        client.setConnectTimeout(15, TimeUnit.SECONDS);
//        client.setReadTimeout(15,TimeUnit.SECONDS);
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(type,data);
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .post(requestBody)
                .addHeader("Authorization",token)
                .build();

        Response response = client.newCall(request).execute();
//        Log.d("My_TAG", response.body().string());
        return response;
    }
}
