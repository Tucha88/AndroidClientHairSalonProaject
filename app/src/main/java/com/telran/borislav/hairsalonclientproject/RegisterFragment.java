package com.telran.borislav.hairsalonclientproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.telran.borislav.hairsalonclientproject.models.Client;

/**
 * Created by Boris on 11.04.2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private EditText emailEditText, passwordEditText, passwordCheckEditText, nameEditText, lastNameEditText, phoneNumberEditText;
    private Button registerBtn, cancelRegistrationBtn;
    private RegisterButtonListener listener;
    private Client client;
    private LinearLayout registrationLayout;
    private ProgressBar progressBar;


    public void setListener(RegisterButtonListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        emailEditText = (EditText) view.findViewById(R.id.register_email_edit_text);
        passwordCheckEditText = (EditText) view.findViewById(R.id.register_password_check_edit_text);
        passwordEditText = (EditText) view.findViewById(R.id.register_password_main_edit_text);
        nameEditText = (EditText) view.findViewById(R.id.register_name_edit_text);
        lastNameEditText = (EditText) view.findViewById(R.id.register_last_name_edit_text);
        phoneNumberEditText = (EditText) view.findViewById(R.id.register_phone_number_edit_text);
        registerBtn = (Button) view.findViewById(R.id.register_btn);
        cancelRegistrationBtn = (Button) view.findViewById(R.id.cancel_registration_btn);
        setListener((RegisterButtonListener) getActivity());
        registerBtn.setOnClickListener(this);
        cancelRegistrationBtn.setOnClickListener(this);
        registrationLayout = (LinearLayout) view.findViewById(R.id.registration_linear_layout);
        progressBar = (ProgressBar) view.findViewById(R.id.registration_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.register_btn) {
            if (!validate()) {
                return;
            }
            String clientPhoneNumber = phoneNumberEditText.getText().toString();
            String clientEmail = emailEditText.getText().toString();
            String clientPassword = passwordEditText.getText().toString();
            String clientName = nameEditText.getText().toString();
            String clientLastName = lastNameEditText.getText().toString();
            client = new Client(clientPhoneNumber, clientEmail, clientPassword, clientName, clientLastName);
            Log.d("MY_TAG", "onClick: this is client" + client.toString());
            listener.startRegistrationTask(client);
        }
        if (view.getId() == R.id.cancel_registration_btn) {
            getActivity().getFragmentManager().popBackStack();
        }

    }

    private boolean validate() {
        boolean valid = true;
        String clientPhoneNumber = phoneNumberEditText.getText().toString();
        String clientEmail = emailEditText.getText().toString();
        String clientPassword = passwordEditText.getText().toString();
        String clientName = nameEditText.getText().toString();
        String clientLastName = lastNameEditText.getText().toString();
        String clientPasswordCheck = passwordCheckEditText.getText().toString();
        if (clientEmail.isEmpty() || clientEmail == null || !clientEmail.contains("@")) {
            emailEditText.setError("Enter a valid email");
            valid = false;
        }
        if (clientPassword == null || clientPassword.isEmpty() || !clientPassword.equals(clientPasswordCheck)) {
            passwordEditText.setError("Enter valid password");
            valid = false;
        }

        return valid;
    }


    public void doOnPostExecute() {
        registrationLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

    }

    public void doOnPreExecute() {
        registrationLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

    }


    interface RegisterButtonListener {
        void startRegistrationTask(Client client);
    }
}
