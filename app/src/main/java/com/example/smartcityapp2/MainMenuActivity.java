package com.example.smartcityapp2;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenuActivity extends AppCompatActivity {
    Button NID,PASSPORT,B_DAY,POLICE,LOCATION,AIR,WEATHER,FIRE,HOSPITAL,LOGOUT,GAS,WATER,ELECTRICITY,SOS;
    TextView profile;
    String keyname;
    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        keyname = getIntent().getStringExtra("Keyname");
        reff = FirebaseDatabase.getInstance().getReference().child("Member").child(keyname);

        profile = (TextView) findViewById(R.id.TxtviewProfile);

        NID = (Button)findViewById(R.id.btnnid);
        NID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, MainActivity.class);
                intent.putExtra("N_user",keyname);
                startActivity(intent);
            }
        });
        B_DAY = (Button)findViewById(R.id.btnbday);
        B_DAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, BirthCertificate_activity.class);
                intent.putExtra("B_user",keyname);
                startActivity(intent);
            }
        });
        PASSPORT = (Button)findViewById(R.id.btnpassport);
        PASSPORT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, Passport_activity.class);
                intent.putExtra("P_user",keyname);
                startActivity(intent);
            }
        });
        POLICE = (Button)findViewById(R.id.btnpolice);
        POLICE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, PoliceStation_Activity.class);
                startActivity(intent);
            }
        });

        HOSPITAL = (Button)findViewById(R.id.btnhospital);
        HOSPITAL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, Hospital_Activity.class);
                startActivity(intent);
            }
        });

        FIRE = (Button)findViewById(R.id.btnfire);
        FIRE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, Activity_FireService.class);
                startActivity(intent);
            }
        });

        LOCATION = (Button)findViewById(R.id.btnloc);
        LOCATION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, MapsActivity_Location.class);
                startActivity(intent);
            }
        });

        AIR = (Button)findViewById(R.id.btnair);
        AIR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, MapsActivity_air_check.class);
                startActivity(intent);
            }
        });

        WEATHER = (Button)findViewById(R.id.btnweat);
        WEATHER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, Weather_Activity.class);
                startActivity(intent);
            }
        });
        LOGOUT = (Button)findViewById(R.id.btnlogout);
        LOGOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, Login_Activity.class);
                startActivity(intent);
            }
        });

        GAS = (Button)findViewById(R.id.btngas);
        GAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, GasBill.class);
                intent.putExtra("g_user",keyname);
                startActivity(intent);
            }
        });
        WATER = (Button)findViewById(R.id.btnwater);
        WATER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, WaterBill.class);
                intent.putExtra("w_user",keyname);
                startActivity(intent);
            }
        });

        ELECTRICITY = (Button) findViewById(R.id.btnelectricity);
        ELECTRICITY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, ElectricityBill.class);
                intent.putExtra("e_user",keyname);
                startActivity(intent);
            }
        });

        SOS = (Button) findViewById(R.id.btnsos);
        SOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenuActivity.this, "Hold For Activate SOS", Toast.LENGTH_SHORT).show();
            }
        });

        SOS.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(snapshot.child("e_name").exists() && snapshot.child("e_phone").exists() && snapshot.child("e_msg").exists()){

                            String emname = snapshot.child("e_name").getValue().toString();
                            String number = snapshot.child("e_phone").getValue().toString();
                            String messageToSend = snapshot.child("e_msg").getValue().toString();

                            SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);
                            Toast.makeText(MainMenuActivity.this, "Emergency message sent", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainMenuActivity.this, Confirm_Box_4.class);
                            intent.putExtra("Confirm4",keyname);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainMenuActivity.this, "Haven't set emergency contact", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

                return false;
            }
        });



        //profile name..............
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String nm = snapshot.child("name").getValue().toString();

                profile.setText("Hi "+nm);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }

    public void prf(View view) {
        Intent intent = new Intent(MainMenuActivity.this, ProfileView_Activity.class);
        intent.putExtra("Keyname1",keyname);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        //do nothing;
    }
}