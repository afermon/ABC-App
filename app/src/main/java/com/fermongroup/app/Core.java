package com.fermongroup.app;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class Core extends ActionBarActivity
        implements  Testing.OnFragmentInteractionListener,Home.OnHomeListener,
        NavigationDrawerCallbacks {
    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private static final String TAG = "Andruino";
    Handler h;
    final int RECIEVE_MESSAGE = 1;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();
    private ConnectedThread mConnectedThread;
    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // MAC-address of Bluetooth module (you must edit this line)
    private static String address = "20:13:11:12:04:32";
    private CharSequence mTitle;
    DatabaseHandler db;

    private int OnFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btAdapter = BluetoothAdapter.getDefaultAdapter();// get Bluetooth adapter
        setContentView(R.layout.layout_core);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout)
                findViewById(R.id.drawer), mToolbar);

        db = new DatabaseHandler(this);
        //BLUETOOTH
        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE: // if receive massage
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);//string from bytes[]
                        sb.append(strIncom);// append string
                        int endOfLineIndex = sb.indexOf("\r\n");// determine the end-of-line
                        if (endOfLineIndex > 0) {// if end-of-line,
                            String sbprint = sb.substring(0, endOfLineIndex);// extract string
                            sb.delete(0, sb.length());// and clear
                            AddLog(0, sbprint);
                            ComFragments(sbprint);
                        }
                        break;
                }
            };
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.core, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_logs) {
            db.deleteAllLogs();
            UpdateLogFragment();
            Toast.makeText(this, "Logs Deleted!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        OnFrag = position;
        Fragment OnFragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position){
            case 1:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                OnFragment = new Controls();
                mTitle = getString(R.string.title_control);
                break;
            case 2:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                OnFragment = new Testing();
                mTitle = getString(R.string.testing);
                break;
            case 3:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                OnFragment = Logs.newInstance();
                mTitle = getString(R.string.title_logs);
                break;
            case 4:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                OnFragment = new About();
                mTitle = getString(R.string.title_about);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                OnFragment = Home.newInstance(btAdapter.isEnabled(), address);
                mTitle = getString(R.string.title_home);
                break;
        }
        //Be Sure no null Fragments
        if (OnFragment != null) fragmentManager.beginTransaction()
                .replace(R.id.container, OnFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO:Check if connect is needed
        //ConnectBluetooth(address);
    }

    @Override
    public void onPause() {
        super.onPause();
        //DisconnectBluetooth();
    }

    public void SendMessage(){//JUST TESTING
        mConnectedThread.SendData("Testing");
    }

    private void ComFragments(String msg){
        //TODO: implement method Active fragment

    }

    private void AddLog(int mTYPE, String MSG){
        Log.d(TAG, "(" + mTYPE + ") "+MSG);
        db.addLog(new LogReg(mTYPE, MSG));
        UpdateLogFragment();
    }

    public void UpdateLogFragment () {
        if (OnFrag == 2){
            Logs LogF = (Logs) getSupportFragmentManager().findFragmentById(R.id.container);
            if (LogF != null) {
                LogF.UpdateLog();
            }
        }
    }
    /** Bluetooth Code **/
    private void errorExit(String title, String message){
        AddLog( 3,  title + " " + message);
        finish();
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod(
                        "createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                AddLog( 3,  "Could not create Insecure RFComm Connection");
            }
        }
        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    public void TurnOnBluetooth() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if(btAdapter==null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                AddLog(2, "Bluetooth Already ON");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,0);
                AddLog(2, "Bluetooth ON");
            }
        }
    }

    public void TurnOffBluetooth() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if(btAdapter != null) {
            if (btAdapter.isEnabled()) {
                btAdapter.disable();
                AddLog(2, "Bluetooth OFF");
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {// Get the input and output streams, using temp objects because are final
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()
            while (true) {// Keep listening to the InputStream until an exception occurs
                try {
                    bytes = mmInStream.read(buffer);// Get number of bytes and message in "buffer"
                    // Send to message queue Handler
                    h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        public void SendData(String message) {
            AddLog(1, message);
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                AddLog(3, e.getMessage());
            }
        }
    }

    public void ConnectBluetooth(String MacAddress) {
        BluetoothDevice device = btAdapter.getRemoteDevice(MacAddress);
        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "Socket create failed: " + e.getMessage());
        }
        btAdapter.cancelDiscovery(); //cancel discover
        try {
            btSocket.connect();
            AddLog(2, "Successful connection ");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "Connection failure" + e2.getMessage());
            }
        }
        AddLog(2, "Create Socket");// Create a data stream so we can talk to server.
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
    }

    public void DisconnectBluetooth(){
        AddLog(2, "Disconnect");
        try     {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "Failed to close socket." + e2.getMessage());
        }
    }

    public List<String> PairedDevices(){
        List<String> s = new ArrayList<String>();
        if(btAdapter != null) {
            if (btAdapter.isEnabled()){
                Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
                for (BluetoothDevice bt : pairedDevices) {
                    s.add(bt.getName());
                    AddLog(2, bt.getName());
                };
            }
        }
        return s;
    }
}
