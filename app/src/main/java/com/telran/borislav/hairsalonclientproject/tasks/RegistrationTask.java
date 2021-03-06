package com.telran.borislav.hairsalonclientproject.tasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.telran.borislav.hairsalonclientproject.MainActivity;
import com.telran.borislav.hairsalonclientproject.SecondActivity;
import com.telran.borislav.hairsalonclientproject.models.Client;
import com.telran.borislav.hairsalonclientproject.models.Token;
import com.telran.borislav.hairsalonclientproject.providers.Provider;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Boris on 12.04.2017.
 */

public class RegistrationTask extends AsyncTask<Void, Void, String> {
    private Client auth;
    private String path;
    private Context context;

    public RegistrationTask(Client auth, String path, Context context) {
        this.auth = auth;
        this.path = path;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        MainActivity activity1 = (MainActivity) context;
        activity1.doOnPreExecute();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = "Registration ok!";
        Gson gson = new Gson();
        String json = gson.toJson(auth);
        try {
            Response response = Provider.getInstance().post(path, json, "");
            if (response.code() < 400) {
                String responseBody = response.body().string();
                if (!responseBody.isEmpty()) {
                    Token token = gson.fromJson(responseBody, Token.class);
                    SharedPreferences sharedPreferences = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("TOKEN", token.getToken());
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
    protected void onPostExecute(String s) {
        if (s.equals("Registration ok!")) {
            MainActivity activity = (MainActivity) context;
            activity.getFragmentManager().popBackStack();
            activity.startActivityForResult(new Intent(context, SecondActivity.class), 1);
        } else {
            MainActivity activity1 = (MainActivity) context;
            activity1.doOnPostExecute();
            activity1.toastMethod(s);
        }

    }
}
