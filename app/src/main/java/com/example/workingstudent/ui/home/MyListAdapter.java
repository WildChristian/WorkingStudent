package com.example.workingstudent.ui.home;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workingstudent.R;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {

    private final Activity context;
    private final ArrayList<String> maincontext;


    public MyListAdapter(Activity context, ArrayList<String> maincontext) {
        super();
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maincontext=maincontext;


    }

    @Override
    public int getCount() {
        return maincontext.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listdesign, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        Log.i("","getview wurde aufgerufen");
        titleText.setText(maincontext.get(position));

        return rowView;
    };
}