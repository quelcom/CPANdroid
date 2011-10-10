package com.github.quelcom.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.quelcom.cpandroid.R;
import com.github.quelcom.model.json.ReleaseSearch;

public class ReleaseAdapter extends ArrayAdapter<ReleaseSearch>{
    public ReleaseAdapter(Activity activity, List<ReleaseSearch> records) {
        super(activity, 0, records);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) getContext();
        LayoutInflater inflater = activity.getLayoutInflater();

        // Inflate the views from XML
        View rowView = inflater.inflate(R.layout.row, null);
        ReleaseSearch r = getItem(position);

        TextView textView2 = (TextView) rowView.findViewById(R.id.text2);
        textView2.setText(r.getDownloadUrl());

        return rowView;
    }
}
