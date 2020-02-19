package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<String> elements = new ArrayList<>(Arrays.asList());
    private MyListAdapter myAdapter;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView list = findViewById(R.id.list);

        list.setAdapter(myAdapter = new MyListAdapter());

    }


        public void sendmessage (View v) {
            editText = (EditText)findViewById(R.id.editText6);
            String message = editText.getText().toString();
            editText.setText("");
            elements.add(message);
            myAdapter.notifyDataSetChanged();


        }

        public void receivemessage (View v) {
            editText = (EditText)findViewById(R.id.editText6);
            String message = editText.getText().toString();
            editText.setText("");
            elements.add(message);
            myAdapter.notifyDataSetChanged();


         }


    class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return elements.size();
        }

        public Object getItem(int position) {
            return elements.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            //make a new row:
            if(newView == null) {
                newView = inflater.inflate(R.layout.row_layout, parent, false);

            }
            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.textView4);
            tView.setText( getItem(position).toString() );

            //return it to be put in the table
            return newView;
        }


    }



    //private class Message {


    //}
}