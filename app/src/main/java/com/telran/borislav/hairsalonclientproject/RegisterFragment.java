package com.telran.borislav.hairsalonclientproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.telran.borislav.hairsalonclientproject.R;
import com.telran.borislav.hairsalonclientproject.models.Client;
import com.telran.borislav.hairsalonclientproject.models.ClientAuthType;

/**
 * Created by Boris on 11.04.2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private EditText emailEditText,passwordEditText,passwordCheckEditText,nameEditText,lastNameEditText,phoneNumberEditText;
    private Button registerBtn;
    private RegisterButtonListener listener;
    private Client client;


    public void setListener(RegisterButtonListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        emailEditText = (EditText) view.findViewById(R.id.register_email_editi_text);
        passwordCheckEditText = (EditText) view.findViewById(R.id.register_password_check_edit_text);
        passwordEditText = (EditText) view.findViewById(R.id.register_password_main_edit_text);
        nameEditText = (EditText) view.findViewById(R.id.register_name_edit_text);
        lastNameEditText = (EditText) view.findViewById(R.id.register_last_name_edit_text);
        phoneNumberEditText = (EditText) view.findViewById(R.id.register_phone_number_edit_text);
        registerBtn = (Button) view.findViewById(R.id.register_btn);
        setListener((RegisterButtonListener) getActivity());
        registerBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.register_btn){
            if (!validate()){
                return;
            }
            String clientPhoneNumber = phoneNumberEditText.getText().toString();
            String clientEmail = emailEditText.getText().toString();
            String clientPassword = passwordEditText.getText().toString();
            String clientName = nameEditText.getText().toString();
            String clientLastName = lastNameEditText.getText().toString();
            client = new Client(clientPhoneNumber,clientEmail,clientPassword,clientName,clientLastName);
            Log.d("MY_TAG", "onClick: this is client" + client.toString());
            listener.startRegistrationTask(client);
        }
    }
    private boolean validate(){
        boolean valid = true;
        String clientPhoneNumber = phoneNumberEditText.getText().toString();
        String clientEmail = emailEditText.getText().toString();
        String clientPassword = passwordEditText.getText().toString();
        String clientName = nameEditText.getText().toString();
        String clientLastName = lastNameEditText.getText().toString();
        String clientPasswordCheck = passwordCheckEditText.getText().toString();
        if(clientEmail.isEmpty() || clientEmail == null || !clientEmail.contains("@")){
            emailEditText.setError("Enter a valid email");
            valid = false;
        }
        if (clientPassword == null || clientPassword.isEmpty() || !clientPassword.equals(clientPasswordCheck)){
            passwordEditText.setError("Enter valid password");
            valid = false;
        }

        return valid;
    }


    interface RegisterButtonListener{
        public void startRegistrationTask(Client client);
    }
}
