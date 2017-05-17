package com.telran.borislav.hairsalonclientproject;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.telran.borislav.hairsalonclientproject.models.ClientAuthType;
import com.telran.borislav.hairsalonclientproject.tasks.LoginTask;
import com.telran.borislav.hairsalonclientproject.utils.Util;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText loginEditText, passwordEditText;
    private TextView createAccountTextView;
    private Button loginBtn;
    private TransactionControllerListener listener1;
    private FrameLayout progressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginEditText = (EditText) view.findViewById(R.id.email_login_text_edit);
        passwordEditText = (EditText) view.findViewById(R.id.password_text_edit);
        createAccountTextView = (TextView) view.findViewById(R.id.register_text_link);
        loginBtn = (Button) view.findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
        createAccountTextView.setOnClickListener(this);
        setListener1((TransactionControllerListener) getActivity());
        progressBar = (FrameLayout) view.findViewById(R.id.login_progress_bar);
        return view;
    }


    public void setListener1(TransactionControllerListener listener) {
        this.listener1 = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                ClientAuthType client = new ClientAuthType();
                client.setClientEmail(loginEditText.getText().toString());
                client.setClientPassword(passwordEditText.getText().toString());
                new LoginTask(client, "login/login/", getActivity()).execute();
                break;
            case R.id.register_text_link:
                listener1.goToNextFragment(Util.START_SIGNEUP);

                break;
        }

    }

    public void doOnPostExecute() {
        progressBar.setVisibility(View.INVISIBLE);

    }

    public void doOnPreExecute() {
        progressBar.setOnClickListener(null);
        progressBar.setVisibility(View.VISIBLE);

    }

    interface TransactionControllerListener {
        void goToNextFragment(int whatFragmentToGo);
    }


}
