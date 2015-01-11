package com.fermongroup.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fermongroup.app.Joystick.OnJoystickMoveListener;


public class Controls extends Fragment {

    private Joystick joystickA;
    private Joystick joystickB;
    private TextView TextjoystickA;
    private TextView TextjoystickB;
    private TextView AnglejoystickA;
    private TextView AnglejoystickB;
    private TextView PowerjoystickA;
    private TextView PowerjoystickB;
    private OnCommunicationListener mListener;

    public static Controls newInstance() {
        Controls fragment = new Controls();
        return fragment;
    }

    public Controls() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_controls, container, false);
        joystickA = (Joystick) rootView.findViewById(R.id.joystickA);
        TextjoystickA = (TextView) rootView.findViewById(R.id.TextjoystickA);
        PowerjoystickA = (TextView) rootView.findViewById(R.id.PowerjoystickA);
        AnglejoystickA = (TextView) rootView.findViewById(R.id.AnglejoystickA);
        joystickB = (Joystick) rootView.findViewById(R.id.joystickB);
        TextjoystickB = (TextView) rootView.findViewById(R.id.TextjoystickB);
        PowerjoystickB = (TextView) rootView.findViewById(R.id.PowerjoystickB);
        AnglejoystickB = (TextView) rootView.findViewById(R.id.AnglejoystickB);
        joystickA.setOnJoystickMoveListener(new OnJoystickMoveListener() {
            @Override
            public void onValueChanged(int angle, int power, int direction) {
                AnglejoystickA.setText(" " + String.valueOf(angle) + "°");
                PowerjoystickA.setText(" " + String.valueOf(power) + "%");
                switch (direction) {
                    case Joystick.FRONT:
                        TextjoystickA.setText(R.string.JoyStick_Front);
                        break;

                    case Joystick.FRONT_RIGHT:
                        TextjoystickA.setText(R.string.JoyStick_Front_Right);
                        break;

                    case Joystick.RIGHT:
                        TextjoystickA.setText(R.string.JoyStick_Right);
                        break;

                    case Joystick.RIGHT_BOTTOM:
                        TextjoystickA.setText(R.string.JoyStick_Bottom_Right);
                        break;

                    case Joystick.BOTTOM:
                        TextjoystickA.setText(R.string.JoyStick_Bottom);
                        break;

                    case Joystick.BOTTOM_LEFT:
                        TextjoystickA.setText(R.string.JoyStick_Left_Bottom);
                        break;

                    case Joystick.LEFT:
                        TextjoystickA.setText(R.string.JoyStick_Left);
                        break;

                    case Joystick.LEFT_FRONT:
                        TextjoystickA.setText(R.string.JoyStick_Front_Left);
                        break;

                    default:
                        TextjoystickA.setText(R.string.JoyStick_Center);

                }
            }
        }, Joystick.DEFAULT_LOOP_INTERVAL);

        joystickB.setOnJoystickMoveListener(new OnJoystickMoveListener() {
            @Override
            public void onValueChanged(int angle, int power, int direction) {
                String msg = "D" + String.valueOf(direction) +
                        "P" + String.valueOf(power) +
                        "A" + String.valueOf(angle);
                mListener.onSendData(msg);
                PowerjoystickB.setText(" " + String.valueOf(angle) + "°");
                AnglejoystickB.setText(" " + String.valueOf(power) + "%");
                switch (direction) {
                    case Joystick.FRONT:
                        TextjoystickB.setText(R.string.JoyStick_Front);
                        break;

                    case Joystick.FRONT_RIGHT:
                        TextjoystickB.setText(R.string.JoyStick_Front_Right);
                        break;

                    case Joystick.RIGHT:
                        TextjoystickB.setText(R.string.JoyStick_Right);
                        break;

                    case Joystick.RIGHT_BOTTOM:
                        TextjoystickB.setText(R.string.JoyStick_Bottom_Right);
                        break;

                    case Joystick.BOTTOM:
                        TextjoystickB.setText(R.string.JoyStick_Bottom);
                        break;

                    case Joystick.BOTTOM_LEFT:
                        TextjoystickB.setText(R.string.JoyStick_Left_Bottom);
                        break;

                    case Joystick.LEFT:
                        TextjoystickB.setText(R.string.JoyStick_Left);
                        break;

                    case Joystick.LEFT_FRONT:
                        TextjoystickB.setText(R.string.JoyStick_Front_Left);
                        break;

                    default:
                        TextjoystickB.setText(R.string.JoyStick_Center);
                }
            }
        }, Joystick.DEFAULT_LOOP_INTERVAL);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
           mListener = (OnCommunicationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnCommunicationListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCommunicationListener {
        public void onSendData (String Data);
    }

}
