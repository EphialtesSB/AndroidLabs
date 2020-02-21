package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<String> elements = new ArrayList<>(Arrays.asList());
    private List<Message> messages = new ArrayList<Message>();
    private MyListAdapter myAdapter;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView list = findViewById(R.id.list);

        list.setAdapter(myAdapter = new MyListAdapter());

        /*Button receiveButton = (Button)findViewById(R.id.receiveButton);
        receiveButton.setOnClickListener(this);
        Button sendButton = (Button)findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this); */
    }


        public void sendmessage (View v) {
            editText = (EditText)findViewById(R.id.editText6);
            String message = editText.getText().toString();
            editText.setText("");
            elements.add(message);

            Message newmessage = new Message (message, true);
            messages.add(newmessage);
            myAdapter.notifyDataSetChanged();


        }

        public void receivemessage (View v) {
            editText = (EditText)findViewById(R.id.editText6);
            String message = editText.getText().toString();
            editText.setText("");
            Message newmessage = new Message (message, false);
            messages.add(newmessage);
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
            Message thismessage = messages.get(position);

            //make a new row:
            if(thismessage.send) {
                    newView = inflater.inflate(R.layout.sendlayout, parent, false);

            } else {
                newView = inflater.inflate(R.layout.receivelayout, parent, false);


            }
            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.messagetext);
            tView.setText( getItem(position).toString() );

            //return it to be put in the table
            return newView;

        }


    }



    private class Message {
           String message;
           boolean send;

           public Message (String message, boolean send) {
               this.message  = message;
               this.send = send;
           }
    }
}