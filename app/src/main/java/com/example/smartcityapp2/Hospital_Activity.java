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

public class Hospital_Activity extends AppCompatActivity {
    TextView name1,name2,name3;
    Button phone1,phone2,phone3;
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Hospital");
    Hospitaldata hospitaldata;
    String p_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        hospitaldata= new Hospitaldata() ;
        name1= (TextView ) findViewById(R.id.Txtviewnam1);
        name2= (TextView ) findViewById(R.id.Txtviewnam2);
        name3= (TextView ) findViewById(R.id.Txtviewnam3);
        phone1= (Button ) findViewById(R.id.btnh1);
        phone2= (Button ) findViewById(R.id.btnh2);
        phone3= (Button ) findViewById(R.id.btnh3);

        reff = FirebaseDatabase.getInstance().getReference().child("Hospital").child("user1");

        reff.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange( DataSnapshot snapshot)
            {
                String nm1 = snapshot.child("name1").getValue().toString();
                String nm2 = snapshot.child("name2").getValue().toString();
                String nm3 = snapshot.child("name3").getValue().toString();
                String ph1 = snapshot.child("phone1").getValue().toString();
                String ph2 = snapshot.child("phone2").getValue().toString();
                String ph3 = snapshot.child("phone3").getValue().toString();

                name1.setText(nm1);
                name2.setText(nm2);
                name3.setText(nm3);
                phone1.setText(ph1);
                phone2.setText(ph2);
                phone3.setText(ph3);
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });


    }

    public void make_call_2(View view) {

        if(view.getId() == R.id.btnh1){
            p_no = phone1.getText().toString();
            System.out.println(p_no);
        }else if(view.getId() == R.id.btnh2){
            p_no = phone2.getText().toString();
        }else if(view.getId() == R.id.btnh3){
            p_no = phone3.getText().toString();
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