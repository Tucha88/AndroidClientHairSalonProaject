package com.telran.borislav.hairsalonclientproject.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.telran.borislav.hairsalonclientproject.PersonalProfileFragment;
import com.telran.borislav.hairsalonclientproject.models.Client;
import com.telran.borislav.hairsalonclientproject.models.Token;
import com.telran.borislav.hairsalonclientproject.providers.Provider;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Boris on 14.05.2017.
 */

public class GetClientProfileTask extends AsyncTask<Void, Void, String> {
    public AsyncResponse delegate = null;
    private PersonalProfileFragment profileFragment;
    private Token token;
    private String path;
    private Context context;

    public GetClientProfileTask(Token token, String path, Context context, AsyncResponse delegate) {
        this.token = token;
        this.path = path;
        this.context = context;
        this.delegate = delegate;
    }


    @Override
    protected String doInBackground(Void... voids) {
        String result = "OK";
        Gson gson = new Gson();
        try {
            Response response = Provider.getInstance().get(path, token.getToken());
            Log.d("TAG", "doInBackground: " + response.message());
            if (response.code() < 400) {
                String responseBody = response.body().string();
                if (!responseBody.isEmpty()) {
                    Client client = gson.fromJson(responseBody, Client.class);
                    SharedPreferences sharedPreferences = context.getSharedPreferences("PERSONAL", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("CLIENT", gson.toJson(client, Client.class));
                    editor.commit();

                } else {
                    result = "Server did not answer!";
                }

            } else if (response.code() == 409) {
                result = response.body().string();
            } else {
                result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = "Connection error!";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        delegate.processFinish();

    }

    public interface AsyncResponse {
        void processFinish();

    }
}
