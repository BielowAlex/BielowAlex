package com.BielowCorp.dod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.BielowCorp.dod.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Text> {

    public ListAdapter(Context context, ArrayList<Text> texts){

        super(context, R.layout.text,texts);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Text text = getItem(position);
        if(view == null){view = LayoutInflater.from(getContext()).inflate(R.layout.text,parent,false);}
        TextView textView = view.findViewById(R.id.textList);
        textView.setText(text.getText());

        return view;

    }
}
