package com.example.smartcityapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Confirm_box_Activity extends AppCompatActivity {
    String c_user;
    int f=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_box);
        c_user = getIntent().getStringExtra("confirm");
        f = Integer.parseInt(getIntent().getStringExtra("flag"));

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep( 2000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    if (f == 0) {

                        Intent intent = new Intent(Confirm_box_Activity.this, MainMenuActivity.class);
                        intent.putExtra("Keyname", c_user);
                        startActivity(intent);
                    }else if(f==1)
                    {
                        Intent intent = new Intent(Confirm_box_Activity.this, ProfileView_Activity.class);
                        intent.putExtra("Keyname1", c_user);
                        startActivity(intent);
                    }
                }
            }
        };thread.start();

    }
}