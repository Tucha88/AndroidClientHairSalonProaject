package com.telran.borislav.hairsalonclientproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.telran.borislav.hairsalonclientproject.utils.Util;

public class MainActivity extends AppCompatActivity implements LoginFragment.TransactionControllerListener/*, RegisterFragment.RegisterButtonListener*/ {
    private FragmentManager manager;
    private FragmentTransaction transaction;

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
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        if (!sharedPreferences.getString("TOKEN","").isEmpty()){
            startActivityForResult(new Intent(this, SecondActivity.class),1);

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1){
                finish();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {

        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void goToNextFragment(int whatFragmentToGo) {
        Fragment fragment1 = new RegisterFragment();
        switch (whatFragmentToGo){
            case Util.START_SIGNEUP:
                transaction = manager.beginTransaction();
                transaction.replace(R.id.framgent_container,fragment1,"FRAG_REGISTER");
                transaction.addToBackStack("FRAG3");
                transaction.commit();
                break;
        }

    }
    public void toastMethod(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    public void doOnPostExecute() {
        RegisterFragment fragment = (RegisterFragment) manager.findFragmentByTag("FRAG_REGISTER");
        fragment.doOnPostExecute();
    }

    public void doOnPreExecute() {
        RegisterFragment fragment = (RegisterFragment) manager.findFragmentByTag("FRAG_REGISTER");
        fragment.doOnPreExecute();
    }

    public void doOnPostExecuteLogin() {
        LoginFragment fragment = (LoginFragment) manager.findFragmentByTag("FRAG_LOGIN");
        fragment.doOnPostExecute();
    }

    public void doOnPreExecuteLogin() {
        LoginFragment fragment = (LoginFragment) manager.findFragmentByTag("FRAG_LOGIN");
        fragment.doOnPreExecute();
    }

}
