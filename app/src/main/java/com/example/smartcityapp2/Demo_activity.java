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

public class Demo_activity extends AppCompatActivity {
    EditText name,mail,phone,pass;
    Member member;
    Button confirm;
    DatabaseReference reff;
    long mxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        member = new Member();
        name = (EditText) findViewById(R.id.editTxtName);
        mail = (EditText) findViewById(R.id.editTextmail);
        phone = (EditText) findViewById(R.id.editTxtPhone);
        pass = (EditText) findViewById(R.id.editTxtPass);


        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    System.out.println("Ondatachange....");
                    mxid = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        confirm = (Button) findViewById(R.id.ok);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println(mxid);

                String nm = name.getText().toString();
                String ml = mail.getText().toString();
                String p = phone.getText().toString();
                long credit=0;
                String pas = pass.getText().toString();

                if(nm.isEmpty() && ml.isEmpty() && pas.isEmpty() && p.isEmpty())
                {
                    Toast.makeText(Demo_activity.this, "Enter all data!", Toast.LENGTH_SHORT).show();
                }
                else{
                    member.setName(nm);
                    member.setMail(ml);
                    member.setPhone(p);
                    member.setPassword(pas);
                    member.setCredit(credit);

                    reff.child("user-"+String.valueOf(mxid+1)).setValue(member);
                    Toast.makeText(Demo_activity.this, "Done!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Demo_activity.this, Login_Activity.class);
                    startActivity(intent);
                }
            }
        });


    }
}