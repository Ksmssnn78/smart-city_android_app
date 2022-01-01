package com.example.smartcityapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BirthCertificate_activity extends AppCompatActivity {
    EditText bedappliFname, bedappliLname, bedfatherFname, bedfatherLname, bedmotherFname, bedmotherLname, bedbdaydate, bedgender, bedpermaadd, bedpreseadd, bedpostal, bedcity, bedstate;
    Button confirm,cancel;
    BirthDATA birthDATA;
    String B_user;
    long mxid=0;
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("BirthDATA");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_certificate);

        birthDATA = new BirthDATA();
        bedappliFname = (EditText) findViewById(R.id.BeditTxtAppliFname);
        bedappliLname = (EditText) findViewById(R.id.BeditTxtAppliLname);
        bedfatherFname = (EditText) findViewById(R.id.BeditTxtFatherFname);
        bedfatherLname = (EditText) findViewById(R.id.BeditTxtFatherLname);
        bedmotherFname = (EditText) findViewById(R.id.BeditTxtMotherFname);
        bedmotherLname = (EditText) findViewById(R.id.BeditTxtMotherLname);
        bedbdaydate = (EditText) findViewById(R.id.BeditTxtBirthDate);
        bedgender = (EditText) findViewById(R.id.BeditTxtgender);
        bedpermaadd = (EditText) findViewById(R.id.BeditTxtPermaadd);
        bedpreseadd = (EditText) findViewById(R.id.BeditTxtPresadd);
        bedpostal = (EditText) findViewById(R.id.BeditTxtPostal);
        bedcity = (EditText) findViewById(R.id.BeditTxtCity);
        bedstate = (EditText) findViewById(R.id.BeditTxtState);

        B_user = getIntent().getStringExtra("B_user");

        reff = FirebaseDatabase.getInstance().getReference().child("BirthDATA");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    mxid = (snapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


        confirm = findViewById(R.id.btnConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bappFnm = bedappliFname.getText().toString().trim();
                String bappLnm = bedappliLname.getText().toString().trim();
                String bfatherFnm = bedfatherFname.getText().toString().trim();
                String bfatherLnm = bedfatherLname.getText().toString().trim();
                String bmotherFnm = bedmotherFname.getText().toString().trim();
                String bmotherLnm = bedmotherLname.getText().toString().trim();
                String bgndr = bedgender.getText().toString().trim();
                String bbdayd = bedbdaydate.getText().toString().trim();
                String bpermadd = bedpermaadd.getText().toString().trim();
                String bpresadd = bedpreseadd.getText().toString().trim();
                String bpstal = bedpostal.getText().toString().trim();
                String bcty = bedcity.getText().toString().trim();
                String bstat = bedstate.getText().toString().trim();

                if(bappFnm.isEmpty()||bappLnm.isEmpty()||bfatherFnm.isEmpty()||bfatherLnm.isEmpty()||bmotherFnm.isEmpty()||bmotherLnm.isEmpty()||bgndr.isEmpty()||bpermadd.isEmpty())
                {
                    Toast.makeText(BirthCertificate_activity.this, "Enter all data!", Toast.LENGTH_SHORT).show();
                }
                else{
                    birthDATA.setBappliFname(bappFnm);
                    birthDATA.setBappliLname(bappLnm);
                    birthDATA.setBfatherFname(bfatherFnm);
                    birthDATA.setBfatherLname(bfatherLnm);
                    birthDATA.setBmotherFname(bmotherFnm);
                    birthDATA.setBmotherLname(bmotherLnm);
                    birthDATA.setBgender(bgndr);
                    birthDATA.setBbdaydate(bbdayd);
                    birthDATA.setBpermaadd(bpermadd);
                    birthDATA.setBpreseadd(bpresadd);
                    birthDATA.setBpostal(bpstal);
                    birthDATA.setBcity(bcty);
                    birthDATA.setBstate(bstat);

                    reff.child("birthentry"+String.valueOf(mxid+1)).setValue(birthDATA);
                    Toast.makeText(BirthCertificate_activity.this, "Succesfull!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(BirthCertificate_activity.this, Confirm_box_Activity.class);
                    intent.putExtra("confirm",B_user);
                    startActivity(intent);
                }
            }
        });

        cancel = findViewById(R.id.btnpasscancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BirthCertificate_activity.this, MainMenuActivity.class);
                intent.putExtra("Keyname",B_user);
                startActivity(intent);
            }
        });
    }
}