package com.fermongroup.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Testing extends Fragment {

    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15;
    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static Testing newInstance() {
        Testing fragment = new Testing();
        return fragment;
    }

    public Testing() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.layout_testing, container, false);
        tv1 = (TextView) mView.findViewById(R.id.textView1);
        tv2 = (TextView) mView.findViewById(R.id.textView2);
        tv3 = (TextView) mView.findViewById(R.id.textView3);
        tv4 = (TextView) mView.findViewById(R.id.textView4);
        tv5 = (TextView) mView.findViewById(R.id.textView5);
        tv6 = (TextView) mView.findViewById(R.id.textView6);
        tv7 = (TextView) mView.findViewById(R.id.textView7);
        tv8 = (TextView) mView.findViewById(R.id.textView8);
        tv9 = (TextView) mView.findViewById(R.id.textView9);
        tv10 = (TextView) mView.findViewById(R.id.textView10);
        tv11 = (TextView) mView.findViewById(R.id.textView11);
        tv12 = (TextView) mView.findViewById(R.id.textView12);
        tv13 = (TextView) mView.findViewById(R.id.textView13);
        tv14 = (TextView) mView.findViewById(R.id.textView14);
        tv15 = (TextView) mView.findViewById(R.id.textView15);

        btn1 = (Button) mView.findViewById(R.id.testing_btn1);
        btn2 = (Button) mView.findViewById(R.id.testing_btn2);
        btn3 = (Button) mView.findViewById(R.id.testing_btn3);
        btn4 = (Button) mView.findViewById(R.id.testing_btn4);
        btn5 = (Button) mView.findViewById(R.id.testing_btn5);
        btn6 = (Button) mView.findViewById(R.id.testing_btn6);
        btn7 = (Button) mView.findViewById(R.id.testing_btn7);
        btn8 = (Button) mView.findViewById(R.id.testing_btn8);
        btn9 = (Button) mView.findViewById(R.id.testing_btn9);
        btn10 = (Button) mView.findViewById(R.id.testing_btn10);
        btn11 = (Button) mView.findViewById(R.id.testing_btn11);
        btn12 = (Button) mView.findViewById(R.id.testing_btn12);
        btn13 = (Button) mView.findViewById(R.id.testing_btn13);
        btn14 = (Button) mView.findViewById(R.id.testing_btn14);
        btn15 = (Button) mView.findViewById(R.id.testing_btn15);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mListener != null) mListener.onSendData("HELLOW");
            }
        });
        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onSendData(String Data);
    }

}
