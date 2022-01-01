package com.example.smartcityapp2;

import androidx.annotation.NonNull;
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

public class MainActivity extends AppCompatActivity {
    Button confirm,cancel;
    EditText edappliFname, edappliLname, edfatherFname, edfatherLname, edmotherFname, edmotherLname, edbdaydate, edbdayno, edgender, edpermaadd, edpreseadd, edpostal, edcity, edstate;
    String N_user;
    NIDRQST nidrqst;
    long nmxid=0;
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("NIDRQST");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nidrqst = new NIDRQST();
        edappliFname = (EditText) findViewById(R.id.editTxtAppliFname);
        edappliLname = (EditText) findViewById(R.id.editTxtAppliLname);
        edfatherFname = (EditText) findViewById(R.id.editTxtFatherFname);
        edfatherLname = (EditText) findViewById(R.id.editTxtFatherLname);
        edmotherFname = (EditText) findViewById(R.id.editTxtMotherFname);
        edmotherLname = (EditText) findViewById(R.id.editTxtMotherLname);
        edbdaydate = (EditText) findViewById(R.id.editTxtBirthDate);
        edbdayno = (EditText) findViewById(R.id.editTxtBirthCerti);
        edgender = (EditText) findViewById(R.id.editTxtGender);
        edpermaadd = (EditText) findViewById(R.id.editTxtPermaadd);
        edpreseadd = (EditText) findViewById(R.id.editTxtPresadd);
        edpostal = (EditText) findViewById(R.id.editTxtPostal);
        edcity = (EditText) findViewById(R.id.editTxtCity);
        edstate = (EditText) findViewById(R.id.editTxtState);
        N_user = getIntent().getStringExtra("N_user");

        reff = FirebaseDatabase.getInstance().getReference().child("NIDRQST");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    nmxid = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        //Button_Work...........
        confirm = findViewById(R.id.btnConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String appFnm = edappliFname.getText().toString().trim();
                String appLnm = edappliLname.getText().toString().trim();
                String fatherFnm = edfatherFname.getText().toString().trim();
                String fatherLnm = edfatherLname.getText().toString().trim();
                String motherFnm = edmotherFname.getText().toString().trim();
                String motherLnm = edmotherLname.getText().toString().trim();
                String gndr = edgender.getText().toString().trim();
                String bdayd = edbdaydate.getText().toString();
                String bdayc = edbdayno.getText().toString();
                String permadd = edpermaadd.getText().toString().trim();
                String presadd = edpreseadd.getText().toString().trim();
                String pstal = edpostal.getText().toString();
                String cty = edcity.getText().toString().trim();
                String stat = edstate.getText().toString().trim();

                if(appFnm.isEmpty()||appLnm.isEmpty()||fatherFnm.isEmpty()||fatherLnm.isEmpty()||motherFnm.isEmpty()||motherLnm.isEmpty()||gndr.isEmpty()||permadd.isEmpty()||presadd.isEmpty()||cty.isEmpty()||stat.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Enter all data!", Toast.LENGTH_SHORT).show();
                } else{
                    nidrqst.setAppliFname(appFnm);
                    nidrqst.setAppliLname(appLnm);
                    nidrqst.setFatherFname(fatherFnm);
                    nidrqst.setFatherLname(fatherLnm);
                    nidrqst.setMotherFname(motherFnm);
                    nidrqst.setMotherLname(motherLnm);
                    nidrqst.setBdaydate(bdayd);
                    nidrqst.setBdayno(bdayc);
                    nidrqst.setGender(gndr);
                    nidrqst.setPermaadd(permadd);
                    nidrqst.setPreseadd(presadd);
                    nidrqst.setPostal(pstal);
                    nidrqst.setCity(cty);
                    nidrqst.setState(stat);

                    reff.child("niduser"+String.valueOf(nmxid+1)).setValue(nidrqst);
                    Toast.makeText(MainActivity.this, "Succesfull!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, Confirm_box_Activity.class);
                    intent.putExtra("confirm",N_user);
                    startActivity(intent);
                }
            }
        });

        cancel = findViewById(R.id.btnnidcancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                intent.putExtra("Keyname",N_user);
                startActivity(intent);
            }
        });
    }
}