package com.example.androidlabs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


public class DetailFragment extends Fragment {


    private Bundle dataFromActivity;
    private long id;
    private AppCompatActivity parentActivity;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_detail, container, false);


        dataFromActivity = getArguments();

        id = dataFromActivity.getLong("Id");
        TextView idView = result.findViewById(R.id.id);
        idView.setText("ID=" + id);

        CheckBox CheckBox = result.findViewById(R.id.checkBox4);
        CheckBox.setChecked(dataFromActivity.getBoolean("isSent"));


        TextView messagetxt = result.findViewById(R.id.message);
        messagetxt.setText(dataFromActivity.getString("message"));

        Button hide = result.findViewById(R.id.hide);
        hide.setOnClickListener( clk -> {

            //Tell the parent activity to remove
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });


        return result;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (AppCompatActivity)context;
    }


}
