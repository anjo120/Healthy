package com.ex59070120.user.healthy.Sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ex59070120.user.healthy.R;

import java.util.ArrayList;
import java.util.List;

public class SleepItem extends ArrayAdapter<Sleep> {
    List<Sleep> sleeps = new ArrayList<Sleep>();
    Context context;

    public SleepItem(Context context, int resource, List<Sleep> objects) {
        super(context, resource, objects);
        this.sleeps = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View _sleepItem = LayoutInflater.from(context).inflate(R.layout.fragment_sleep_item, parent, false);
        TextView _date = (TextView) _sleepItem.findViewById(R.id.sleep_date);
        TextView _time_sleep = (TextView) _sleepItem.findViewById(R.id.sleep_sleep);
        TextView _time_wakeup = (TextView) _sleepItem.findViewById(R.id.sleep_wakeup);
        TextView _time_dream = (TextView) _sleepItem.findViewById(R.id.sleep_dream);
        Sleep _row = sleeps.get(position);
        _date.setText(_row.getDate());
        _time_sleep.setText(String.valueOf(_row.getTime_sleep()));
        _time_wakeup.setText(String.valueOf(_row.getTime_wakeup()));
        _time_dream.setText(String.valueOf(_row.getTime_dream()));
        return _sleepItem;
    }
}
