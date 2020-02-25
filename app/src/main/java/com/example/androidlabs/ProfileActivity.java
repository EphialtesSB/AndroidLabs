package com.example.androidlabs;


import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ImageButton mimageButton = findViewById(R.id.picButton);

    }


    public void gotochat(View V) {
        Intent nextPage = new Intent(this, ChatRoomActivity.class);
        startActivity(nextPage);
    }

}