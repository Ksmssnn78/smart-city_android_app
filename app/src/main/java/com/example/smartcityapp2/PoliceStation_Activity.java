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

public class PoliceStation_Activity extends AppCompatActivity {
    TextView pname1,pname2,pname3;
    Button pphone1,pphone2,pphone3;
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Police");
    PoliceData policeData;
    String p_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_station);
        policeData= new PoliceData() ;
        pname1= (TextView ) findViewById(R.id.Txtviewnam1);
        pname2= (TextView ) findViewById(R.id.Txtviewnam2);
        pname3= (TextView ) findViewById(R.id.Txtviewnam3);
        pphone1= (Button ) findViewById(R.id.btncnt1);
        pphone2= (Button ) findViewById(R.id.btncnt2);
        pphone3= (Button ) findViewById(R.id.btncnt3);

        reff = FirebaseDatabase.getInstance().getReference().child("Police").child("puser1");

        reff.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange( DataSnapshot snapshot)
            {
                String pnm1 = snapshot.child("pname1").getValue().toString();
                String pnm2 = snapshot.child("pname2").getValue().toString();
                String pnm3 = snapshot.child("pname3").getValue().toString();
                String pph1 = snapshot.child("pphone1").getValue().toString();
                String pph2 = snapshot.child("pphone2").getValue().toString();
                String pph3 = snapshot.child("pphone3").getValue().toString();


                pname1.setText(pnm1);
                pname2.setText(pnm2);
                pname3.setText(pnm3);
                pphone1.setText(pph1);
                pphone2.setText(pph2);
                pphone3.setText(pph3);
            }
            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }

    public void make_call(View view) {
        if(view.getId() == R.id.btncnt1){
            p_no = pphone1.getText().toString();
            System.out.println(p_no);
        }else if(view.getId() == R.id.btncnt2){
            p_no = pphone2.getText().toString();
        }else if(view.getId() == R.id.btncnt3){
            p_no = pphone3.getText().toString();
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