package com.fermongroup.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Logs extends ListFragment {

    private List<ListViewItem> InLog;
    private ListViewLogAdapter mListViewLogAdapter;
    DatabaseHandler db;

    public class ListViewItem {
        public final Drawable icon;
        public final String information;
        public ListViewItem(Drawable icon, String information) {
            this.icon = icon;
            this.information = information;
        }
    }
    public class ListViewLogAdapter extends ArrayAdapter<ListViewItem> {

        public ListViewLogAdapter(Context context, List<ListViewItem> items) {
            super(context, R.layout.layout_logs, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.layout_logs, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.Icon = (ImageView) convertView.findViewById(R.id.Icon);
                viewHolder.Information = (TextView) convertView.findViewById(R.id.Information);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ListViewItem item = getItem(position);
            viewHolder.Icon.setImageDrawable(item.icon);
            viewHolder.Information.setText(item.information);
            return convertView;
        }
        private class ViewHolder {
            ImageView Icon;
            TextView Information;
        }
    }

    public static Logs newInstance() {
        Logs fragment = new Logs();
        return fragment;
    }

    public Logs() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InLog = new ArrayList<ListViewItem>();
        db = new DatabaseHandler(getActivity());
        FillInlogs();
        mListViewLogAdapter = new ListViewLogAdapter(getActivity(), InLog);
        setListAdapter(mListViewLogAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateLog();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getListView().setDivider(null);
        super.onActivityCreated(savedInstanceState);
    }

    private void FillInlogs(){
        List<LogReg> LogRegs = db.getAllLogs();
        Resources resources = getResources();
        InLog.clear();
        for (LogReg mLogReg: LogRegs){
            switch (mLogReg.getType()){
                case 0: //Input
                    InLog.add(0, new ListViewItem(resources.getDrawable(R.drawable.input),
                            mLogReg.getInformation()));
                    break;
                case 1: //Output
                    InLog.add(0, new ListViewItem(resources.getDrawable(R.drawable.output),
                            mLogReg.getInformation()));
                    break;
                case 2: //info
                    InLog.add(0, new ListViewItem(resources.getDrawable(R.drawable.info),
                            mLogReg.getInformation()));
                    break;
                case 3: //info
                    InLog.add(0, new ListViewItem(resources.getDrawable(R.drawable.error),
                            mLogReg.getInformation()));
                    break;
            }
        }

    }
    public void UpdateLog(){
       FillInlogs();
       mListViewLogAdapter.notifyDataSetChanged();
    }
}
