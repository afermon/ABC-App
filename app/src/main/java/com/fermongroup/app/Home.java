package com.fermongroup.app;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 04/01/2015.
 */
public class Home extends Fragment {
    
    private Switch btswitch;
    private Button btnsearch, btnconnect;
    private Spinner SpinDevices;
    private static final String ARG_PARAM1 = "btstatus";
    private static final String ARG_PARAM2 = "mac";
    private Boolean mBtstatus;
    private String mMac;
    private OnHomeListener mListener;
    List<String> s = new ArrayList<String>();
    ArrayAdapter<String> AdapterDevices;

    public static Home newInstance(Boolean btstatus, String mac) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, btstatus);
        args.putString(ARG_PARAM2, mac);
        fragment.setArguments(args);
        return fragment;
    }

    public Home() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBtstatus = getArguments().getBoolean(ARG_PARAM1);
            mMac = getArguments().getString(ARG_PARAM2);
        }
        FillDevices();
        AdapterDevices = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, s);
        AdapterDevices.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_home, container, false);
        SpinDevices = (Spinner) rootView.findViewById(R.id.SpinDevices);
        btswitch = (Switch) rootView.findViewById(R.id.btswitch);
        btswitch.setChecked(mBtstatus);

        btswitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    mListener.TurnOnBluetooth();
                    btswitch.setText(getString(R.string.bluetooth_status_on));
                }else{
                    mListener.TurnOffBluetooth();
                    btswitch.setText(getString(R.string.bluetooth_status_off));
                }

            }
        });
        if(btswitch.isChecked()){
            btswitch.setText(getString(R.string.bluetooth_status_on));
        }else{
            btswitch.setText(getString(R.string.bluetooth_status_off));
        }
        SpinDevices.setAdapter(AdapterDevices);

        btnsearch = (Button) rootView.findViewById(R.id.btnsearch);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FillDevices();
            }
        });
        btnconnect = (Button) rootView.findViewById(R.id.btnconnect);
        btnconnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            }
        });


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnHomeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHomeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void FillDevices(){
        s.clear();
        s = mListener.PairedDevices();
        if (AdapterDevices != null) AdapterDevices.notifyDataSetChanged();
    }

    public interface OnHomeListener {
        public void TurnOnBluetooth();
        public void TurnOffBluetooth();
        public List<String> PairedDevices();
    }
}