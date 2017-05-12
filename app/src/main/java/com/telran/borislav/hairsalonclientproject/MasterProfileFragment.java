package com.telran.borislav.hairsalonclientproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.telran.borislav.hairsalonclientproject.models.Master;

/**
 * Created by Boris on 20.04.2017.
 */

public class MasterProfileFragment extends Fragment {
    private TransferredMasterListener masterListener;
    private Master master;

    public void setMasterListener(TransferredMasterListener masterListener) {
        this.masterListener = masterListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master_profile, container, false);
        return view;
    }

    public void getMyMaster(Master master) {
        this.master = master;
        Log.d("OFFTAG", "onCreateView: olijhakjeg" + master.getAddresses() + "123");

    }


    interface TransferredMasterListener {
        void transferMaster(Master masterTransfer);
    }
}
