package com.telran.borislav.hairsalonclientproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.telran.borislav.hairsalonclientproject.models.Client;
import com.telran.borislav.hairsalonclientproject.tasks.RegistrationTask;
import com.telran.borislav.hairsalonclientproject.utils.Util;

public class MainActivity extends AppCompatActivity implements LoginFragment.TransactionControllerListener, RegisterFragment.RegisterButtonListener {
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private LoginFragment loginFragment;
    private RegisterFragment controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getFragmentManager();
        Fragment fragment = new LoginFragment();
        transaction = manager.beginTransaction();
        transaction.add(R.id.framgent_container,fragment,"FRAG_LOGIN");
        transaction.addToBackStack("FRAG1");
        transaction.commit();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {

        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void goToNextFragment(int whatFragmentToGo,Client client) {
        Fragment fragment1 = new RegisterFragment();
//        controller = (RegisterFragment) getFragmentManager().findFragmentById(R.id.framgent_container);
//        controller.setListener(this);
        switch (whatFragmentToGo){
            case Util.START_SIGNEUP:
                transaction = manager.beginTransaction();
                transaction.replace(R.id.framgent_container,fragment1,"FRAG_REGISTER");
                transaction.addToBackStack("FRAG3");
                transaction.commit();
                break;
            case Util.START_LOGIN:
                new RegistrationTask(client,"login/client/",this).execute();
                break;
        }

    }
    public void toastMethod(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void startRegistrationTask(Client client) {
        new RegistrationTask(client,"register/client/",this).execute();

    }
}
