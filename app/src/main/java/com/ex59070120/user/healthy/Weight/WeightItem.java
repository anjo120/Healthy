package com.ex59070120.user.healthy.Weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import com.ex59070120.user.healthy.*;

public class WeightItem extends ArrayAdapter<Weight> {
    List<Weight> weights = new ArrayList<Weight>();
    Context context;

    public WeightItem(Context context, int resource, List<Weight> objects) {
        super(context, resource, objects);
        this.weights = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View _weightItem = LayoutInflater.from(context).inflate(R.layout.fragment_weight_item, parent, false);
        TextView _date = (TextView) _weightItem.findViewById(R.id.weight_date);
        TextView _weight = (TextView) _weightItem.findViewById(R.id.weight_item);
        TextView _status = (TextView) _weightItem.findViewById(R.id.weight_status);
        Weight _row = weights.get(position);
        _date.setText(_row.getDate());
        _weight.setText(String.valueOf(_row.getWeight()));
        _status.setText(_row.getStatus());
        return _weightItem;
    }
}
