package com.telran.borislav.hairsalonclientproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.telran.borislav.hairsalonclientproject.models.Master;
import com.telran.borislav.hairsalonclientproject.models.Services;
import com.telran.borislav.hairsalonclientproject.models.StateVO;
import com.telran.borislav.hairsalonclientproject.utils.MySpinnerAdapter;

import java.util.ArrayList;

/**
 * Created by Boris on 20.04.2017.
 */

public class MasterProfileFragment extends Fragment implements View.OnClickListener {
    private TransferredMasterListener masterListener;
    private Master master;
    private TextView masterName, masterEmail, masterAddress, masterDesctiption;
    private Button makeAppointmentBtn, cancelBtn;
    private Spinner masterServicer;


    public void setMasterListener(TransferredMasterListener masterListener) {
        this.masterListener = masterListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master_profile, container, false);
        masterAddress = (TextView) view.findViewById(R.id.profile_master_address_text);
        masterDesctiption = (TextView) view.findViewById(R.id.profile_master_description_text);
        masterEmail = (TextView) view.findViewById(R.id.profile_master_email_text);
        masterName = (TextView) view.findViewById(R.id.profile_master_name_text);
        masterServicer = (Spinner) view.findViewById(R.id.profile_master_services_text);
        makeAppointmentBtn = (Button) view.findViewById(R.id.profile_master_make_appointment_btn);
        cancelBtn = (Button) view.findViewById(R.id.profile_master_cancel_btn);

        try {
            masterName.setText(master.getName() + " " + master.getLastName());
            masterAddress.setText(master.getAddresses());
            masterEmail.setText(master.getEmail());
            ArrayList<StateVO> listVOs = new ArrayList<>();

            for (Services services : master.getSerivce()) {
                StateVO stateVO = new StateVO();
                Log.d("TAG", "Services " + " " + services.getService());
                stateVO.setTitle(services.getService());
                stateVO.setSelected(false);
                listVOs.add(stateVO);
            }

            MySpinnerAdapter mySpinnerAdapter = new MySpinnerAdapter(getActivity(), 0, listVOs);
            masterServicer.setAdapter(mySpinnerAdapter);

        } catch (Exception e) {
        }
        makeAppointmentBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        return view;
    }

    public void getMyMaster(Master master) {
        this.master = master;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_master_make_appointment_btn:
                break;
            case R.id.profile_master_cancel_btn:
                getActivity().getFragmentManager().popBackStack();
                break;
        }

    }


    interface TransferredMasterListener {
        void transferMaster(Master masterTransfer);
    }
}
