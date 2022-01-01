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

public class Passport_activity extends AppCompatActivity {
    EditText peditTxtAppliFname, peditTxtAppliLname, peditTxtNID, peditTxtFatherFname, peditTxtFatherLname, peditTxtMotherFname, peditTxtMotherLname, peditTxtBirthDate, peditTxtPermaadd, peditTxtPresadd, peditTxtPostal, peditTxtCity, peditTxtState;
    long pmxid = 0;
    Button confirm, cancel;
    PassDATA passDATA;
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("PassDATA");
    String P_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport);

        passDATA = new PassDATA();
        peditTxtAppliFname = (EditText) findViewById(R.id.peditTxtAppliFname);
        peditTxtAppliLname = (EditText) findViewById(R.id.peditTxtAppliLname);
        peditTxtNID = (EditText) findViewById(R.id.peditTxtNID);
        peditTxtFatherFname = (EditText) findViewById(R.id.peditTxtFatherFname);
        peditTxtFatherLname = (EditText) findViewById(R.id.peditTxtFatherLname);
        peditTxtMotherFname = (EditText) findViewById(R.id.peditTxtMotherFname);
        peditTxtMotherLname = (EditText) findViewById(R.id.peditTxtMotherLname);
        peditTxtBirthDate = (EditText) findViewById(R.id.peditTxtBirthDate);
        peditTxtPermaadd = (EditText) findViewById(R.id.peditTxtPermaadd);
        peditTxtPresadd = (EditText) findViewById(R.id.peditTxtPresadd);
        peditTxtPostal = (EditText) findViewById(R.id.peditTxtPostal);
        peditTxtCity = (EditText) findViewById(R.id.peditTxtCity);
        peditTxtState = (EditText) findViewById(R.id.peditTxtState);
        P_user = getIntent().getStringExtra("P_user");

        reff = FirebaseDatabase.getInstance().getReference().child("PassDATA");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    pmxid = (snapshot.getChildrenCount());
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

                String pappFnm = peditTxtAppliFname.getText().toString().trim();
                String pappLnm = peditTxtAppliLname.getText().toString().trim();
                String pnid = peditTxtNID.getText().toString().trim();
                String pfatherFnm = peditTxtFatherFname.getText().toString().trim();
                String pfatherLnm = peditTxtFatherLname.getText().toString().trim();
                String pmotherFnm = peditTxtMotherFname.getText().toString().trim();
                String pmotherLnm = peditTxtMotherLname.getText().toString().trim();
                String pbdayd = peditTxtBirthDate.getText().toString().trim();
                String ppermadd = peditTxtPermaadd.getText().toString().trim();
                String ppresadd = peditTxtPresadd.getText().toString().trim();
                String ppstal = peditTxtPostal.getText().toString().trim();
                String pcty = peditTxtCity.getText().toString().trim();
                String pstat = peditTxtState.getText().toString().trim();
                
                if(pappFnm.isEmpty()||pappLnm.isEmpty()||pnid.isEmpty()||pfatherFnm.isEmpty()||pfatherLnm.isEmpty()||pmotherFnm.isEmpty()||pmotherLnm.isEmpty()||pbdayd.isEmpty()||ppermadd.isEmpty()||ppresadd.isEmpty()||ppstal.isEmpty()||pcty.isEmpty()||pstat.isEmpty())
                {
                    Toast.makeText(Passport_activity.this, "Enter all data!", Toast.LENGTH_SHORT).show();
                }else{
                    passDATA.setPappliFname(pappFnm);
                    passDATA.setPappliLname(pappLnm);
                    passDATA.setPnid(pnid);
                    passDATA.setPfatherFname(pfatherFnm);
                    passDATA.setPfatherLname(pfatherLnm);
                    passDATA.setPmotherFname(pmotherFnm);
                    passDATA.setPmotherLname(pmotherLnm);
                    passDATA.setPbdaydate(pbdayd);
                    passDATA.setPpermaadd(ppermadd);
                    passDATA.setPpreseadd(ppresadd);
                    passDATA.setPpostal(ppstal);
                    passDATA.setPcity(pcty);
                    passDATA.setPstate(pstat);

                    reff.child("passportentry"+(pmxid+1)).setValue(passDATA);
                    Toast.makeText(Passport_activity.this, "Enter all data!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Passport_activity.this, Confirm_box_Activity.class);
                    intent.putExtra("confirm",P_user);
                    startActivity(intent);

                }
            }
        });

        cancel = findViewById(R.id.btnpasscancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Passport_activity.this, MainMenuActivity.class);
                intent.putExtra("Keyname",P_user);
                startActivity(intent);
            }
        });

    }
}