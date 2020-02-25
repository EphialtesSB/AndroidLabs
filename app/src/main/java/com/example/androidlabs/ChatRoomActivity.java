package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private List<Message> messages = new ArrayList<Message>();
    private MyListAdapter myAdapter;
    SQLiteDatabase db;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        ListView list = findViewById(R.id.list);

        loadDataFromDatabase();

        list.setAdapter(myAdapter = new MyListAdapter());



        //This listens for items being clicked in the list view
        list.setOnItemLongClickListener(( parent,  view,  position,  id) -> {
            showMessage( position );
            return true;
        });


    }


        public void sendmessage (View v) {
            editText = (EditText)findViewById(R.id.editText6);
            String message = editText.getText().toString();
            int send = 1;
            addMessage(message, send);
        }

        public void receivemessage (View v) {
            editText = (EditText)findViewById(R.id.editText6);
            String message = editText.getText().toString();
            int receive = 0;
            addMessage(message, receive);
         }


    class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return messages.size();
        }

        public Message getItem(int position) {
            return messages.get(position);
        }

        public long getItemId(int position) {
            return getItem(position).getId();
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();
            Message thismessage = getItem(position);

            //make a new row:
            if(thismessage.isSent) {
                    newView = inflater.inflate(R.layout.sendlayout, parent, false);

            } else {
                newView = inflater.inflate(R.layout.receivelayout, parent, false);


            }
            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.messagetext);
            tView.setText(thismessage.getMessage());

            //return it to be put in the table
            return newView;

        }


    }

    private void loadDataFromDatabase() {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String [] columns = {MyOpener.COL_ID, MyOpener.COL_MESSAGE, MyOpener.COL_SENDRECEIVE};
        //query all the results from the database:
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);


        //Now the results object has rows of results that match the query.
        //find the column indices:
        int sendreceivedColIndex = results.getColumnIndex(MyOpener.COL_SENDRECEIVE);
        int messageColumnIndex = results.getColumnIndex(MyOpener.COL_MESSAGE);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
         while (results.moveToNext()){

            String message = results.getString(messageColumnIndex);
            int sendReceive = results.getInt(sendreceivedColIndex);
            boolean sendrec = (sendReceive == 1);
            long id = results.getLong(idColIndex);


            //add the new Contact to the array list:
            messages.add(new Message(message, sendrec, id));
        }

    }

    protected void showMessage(int position)
    {
        Message selectedMessage = messages.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to delete this?")
                .setMessage("The selected row is: " + position + "\nThe database id is: " + selectedMessage.getId())


                .setPositiveButton("Yes", (click, b) -> {
                    deleteMessage(selectedMessage); //remove the contact from database
                    messages.remove(position); //remove the contact from contact list
                    myAdapter.notifyDataSetChanged();

                })
                .setNegativeButton("No", (click, b) -> {

                })
                .create().show();
    }

    protected void addMessage(String message, int isSent){
        //add to the database and get the new ID
        ContentValues newRowValues = new ContentValues();
        int newInt = isSent;

        //Now provide a value for every database column defined in MyOpener.java:
        //put string name in the NAME column:
        newRowValues.put(MyOpener.COL_SENDRECEIVE, newInt);
        newRowValues.put(MyOpener.COL_MESSAGE, message);
        //put string email in the EMAIL column:


        //Now insert in the database:
        long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

        boolean isSentBool = (isSent == 1);

        Message newmessage = new Message (message, isSentBool, newId);

        messages.add(newmessage);
        editText = (EditText)findViewById(R.id.editText6);
        editText.setText("");
        myAdapter.notifyDataSetChanged();
    }

    protected void deleteMessage(Message m)
    {
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[] {Long.toString(m.getId())});
    }


    private class Message {
           String message;
           boolean isSent;
           long id;

        public Message(String m, boolean i, long id) {
            this.message = m;
            this.isSent = i;
            this.id = id;
        }


        public Message(String m, boolean i) {
               this(m, i, 0);
           }

        public String getMessage (){
            return message;
        }

           public long getId (){
            return id;
           }
    }
}