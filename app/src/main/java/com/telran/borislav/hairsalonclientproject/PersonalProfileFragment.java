package com.telran.borislav.hairsalonclientproject;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.telran.borislav.hairsalonclientproject.tasks.ClientProfileTask;

/**
 * Created by Boris on 20.04.2017.
 */

public class PersonalProfileFragment extends Fragment implements View.OnClickListener {
    private TextView clientFullName, clientEmail, clientPhoneNumber;
    private Button findMasterBtn;
    private ImageView favoriteMasters, editProfile;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_profile, container, false);
        clientEmail = (TextView) view.findViewById(R.id.profile_client_email_text);
        clientFullName = (TextView) view.findViewById(R.id.profile_client_name_text);
        clientPhoneNumber = (TextView) view.findViewById(R.id.profile_client_phone_number_text);
        findMasterBtn = (Button) view.findViewById(R.id.profile_client_make_appointment_btn);
        favoriteMasters = (ImageView) view.findViewById(R.id.profile_client_favorite_masters);
        editProfile = (ImageView) view.findViewById(R.id.profile_client_edit);
        findMasterBtn.setOnClickListener(this);
        favoriteMasters.setOnClickListener(this);
        editProfile.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_client_make_appointment_btn:
                Toast.makeText(getActivity(), "Make appointment", Toast.LENGTH_LONG).show();
                break;
            case R.id.profile_client_favorite_masters:
                Toast.makeText(getActivity(), "client favorite", Toast.LENGTH_LONG).show();
                break;
            case R.id.profile_client_edit:
                Toast.makeText(getActivity(), "Edit profile client", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
