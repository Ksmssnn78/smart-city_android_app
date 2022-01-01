package com.example.smartcityapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_FireService extends AppCompatActivity {

    TextView fname1, fname2;
    Button fphone1, fphone2;
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Fire Service");
    FireData fireData;
    String p_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_service);
        fireData = new FireData();
        fname1 = (TextView) findViewById(R.id.textViewf1);
        fname2 = (TextView) findViewById(R.id.textViewf2);
        fphone1 = (Button) findViewById(R.id.fbtnc1);
        fphone2 = (Button) findViewById(R.id.fbtnc2);

        reff = FirebaseDatabase.getInstance().getReference().child("Fire Service").child("user-1");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String fnm1 = snapshot.child("fname1").getValue().toString();
                String fnm2 = snapshot.child("fname2").getValue().toString();
                String fph1 = snapshot.child("fphone1").getValue().toString();
                String fph2 = snapshot.child("fphone2").getValue().toString();

                fname1.setText(fnm1);
                fname2.setText(fnm2);
                fphone1.setText(fph1);
                fphone2.setText(fph2);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void make_call_3(View view) {

        if(view.getId() == R.id.fbtnc1){
            p_no = fphone1.getText().toString();
            System.out.println(p_no);
        }else if(view.getId() == R.id.fbtnc2){
            p_no = fphone2.getText().toString();
        }
        if(p_no.isEmpty()){
            //do nothing
        }else{
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:"+p_no));
            startActivity(call);
        }
    }
}