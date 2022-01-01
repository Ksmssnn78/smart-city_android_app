package com.example.smartcityapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileView_Activity extends AppCompatActivity {
    EditText name,mail,phone,pass;
    TextView cash;
    Member member;
    Button update;
    String user;
    long mxid = 0;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        member = new Member();
        name = (EditText) findViewById(R.id.editTxtprName);
        mail = (EditText) findViewById(R.id.editTxtprMail);
        phone = (EditText) findViewById(R.id.editTxtprPhone);
        pass = (EditText) findViewById(R.id.editTxtprPass);
        cash = (TextView) findViewById(R.id.textViewCash);
        update = (Button) findViewById(R.id.btnupdate);

        user = getIntent().getStringExtra("Keyname1");
        reff = FirebaseDatabase.getInstance().getReference().child("Member").child(user);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                String nm = snapshot.child("name").getValue().toString();
                String ml = snapshot.child("mail").getValue().toString();
                String phn = snapshot.child("phone").getValue().toString();
                String pas = snapshot.child("password").getValue().toString();
                String cas = snapshot.child("credit").getValue().toString();

                name.setHint(nm);
                mail.setHint(ml);
                phone.setHint(phn);
                pass.setHint(pas);
                cash.setHint(cas);
            }

            @Override
            public void onCancelled( DatabaseError error) {
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileView_Activity.this, "Hold for update data", Toast.LENGTH_SHORT).show();
            }
        });
        update.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
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
                String nm = name.getText().toString();
                String ml = mail.getText().toString();
                String p = phone.getText().toString();
                String pas = pass.getText().toString();

                if(!nm.isEmpty() )
                {
                    reff.child("name").setValue(nm);
                }
                if(!ml.isEmpty() )
                {
                    reff.child("mail").setValue(ml);
                }
                if(!p.isEmpty() )
                {
                    reff.child("phone").setValue(p);
                }
                if(!pas.isEmpty() )
                {
                    reff.child("password").setValue(pas);
                }
                Toast.makeText(ProfileView_Activity.this, "Done!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void prowall(View view) {
        Intent intent = new Intent(ProfileView_Activity.this, Wallet_Activity.class);
        intent.putExtra("Keyname2",user);
        startActivity(intent);
    }

    public void sosset(View view) {
        Intent intent = new Intent(ProfileView_Activity.this, SOS_Activity.class);
        intent.putExtra("SOS",user);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileView_Activity.this, MainMenuActivity.class);
        intent.putExtra("Keyname",user);
        startActivity(intent);
    }
}