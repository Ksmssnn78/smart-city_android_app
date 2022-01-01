package com.example.smartcityapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Confirm_Box_4 extends AppCompatActivity {
    String c_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_box4);

        c_user = getIntent().getStringExtra("confirm4");

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep( 2000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(Confirm_Box_4.this,MainMenuActivity.class);
                    intent.putExtra("Keyname",c_user);
                    startActivity(intent);
                }
            }
        };thread.start();
    }
}