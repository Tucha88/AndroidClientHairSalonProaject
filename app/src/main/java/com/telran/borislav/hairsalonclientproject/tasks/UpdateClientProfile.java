package com.telran.borislav.hairsalonclientproject.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.telran.borislav.hairsalonclientproject.models.Client;
import com.telran.borislav.hairsalonclientproject.models.Token;
import com.telran.borislav.hairsalonclientproject.providers.Provider;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Boris on 21.05.2017.
 */

public class UpdateClientProfile extends AsyncTask<Void, Void, String> {
    private Client client;
    private Context context;
    private Token token;
    private String path;
    private FinishUpdateListener listener;

    public UpdateClientProfile(Client client, Context context, Token token, String path, FinishUpdateListener listener) {
        this.client = client;
        this.context = context;
        this.token = token;
        this.path = path;
        this.listener = listener;
    }

    public void setListener(FinishUpdateListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = "OK";
        Gson gson = new Gson();
        String data = gson.toJson(client, Client.class);
        try {
            Response response = Provider.getInstance().put(path, data, token.getToken());
            Log.d("TAG", "doInBackground: " + response.message());
            if (response.code() < 400) {
                String responseBody = response.body().string();
                if (!responseBody.isEmpty()) {
//                    Client client = gson.fromJson(responseBody, Client.class);
//                    SharedPreferences sharedPreferences = context.getSharedPreferences("PERSONAL", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("CLIENT", gson.toJson(client, Client.class));
//                    editor.commit();

                    return result;

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
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result.equals("OK")) {
            listener.finishSuccess();
        } else {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }

    public interface FinishUpdateListener {
        void finishSuccess();
    }
}
