package com.telran.borislav.hairsalonclientproject.tasks;

import android.os.AsyncTask;

import com.telran.borislav.hairsalonclientproject.PersonalProfileFragment;
import com.telran.borislav.hairsalonclientproject.models.Token;

/**
 * Created by Boris on 14.05.2017.
 */

public class ClientProfileTask extends AsyncTask<Void, Void, Void> {
    private PersonalProfileFragment profileFragment;
    private Token token;
    private String path;


    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}
