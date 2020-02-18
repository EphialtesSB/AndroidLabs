package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

// import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab3layoutfile);
        EditText email = findViewById(R.id.email);
        EditText pass = findViewById(R.id.pass);
        Button test1 = (findViewById(R.id.button));
       // test1.setOnClickListener(bt -> savedSharedPrefs ( email.getText().toString(), pass.getText().toString()));

        SharedPreferences prefs = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        String savedEmail = prefs.getString("email", "def");
        email.setText(savedEmail);

    }

    @Override
    protected void onResume()
    {
        super.onResume();


    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //EditText email = findViewById(R.id.email);
       // EditText pass = findViewById(R.id.pass);
        //savedSharedPrefs ( email.getText().toString(), pass.getText().toString());


    }

    public void savedSharedPrefs(String input1, String input2) {
        // Creating a shared pref object
        // with a file name "MySharedPref" in private mode
        SharedPreferences prefs = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = prefs.edit();
        myEdit.putString("email", input1);
        myEdit.putString("pass", input2);
        myEdit.commit();




    }



    public void loginbutton (View v) {
        Intent nextPage = new Intent(this, ProfileActivity.class);
        startActivity(nextPage);
    }
}

        /*Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), " testestest" , Snackbar.LENGTH_LONG);
        snackbar.show();*/




       /*  // Showing Snackbar
        String resultemail = prefs.getString("email", "def");
        String resultpass = prefs.getString("pass", "def");
        Snackbar snackbar 2= Snackbar.make(findViewById(android.R.id.content), resultemail + " " + resultpass, Snackbar.LENGTH_LONG);
        snackbar.show(); */

