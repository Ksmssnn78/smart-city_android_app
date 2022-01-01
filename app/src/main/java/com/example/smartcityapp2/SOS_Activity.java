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

public class SOS_Activity extends AppCompatActivity {
    Button confirm;
    EditText name, contact, msg;
    String emuser;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        confirm = (Button) findViewById(R.id.btnsosconfirm);
        name = (EditText) findViewById(R.id.editTxtemname);
        contact = (EditText) findViewById(R.id.editTxtemphn);
        msg = (EditText) findViewById(R.id.editTxtemsg);

        emuser = getIntent().getStringExtra("SOS");
        reff = FirebaseDatabase.getInstance().getReference().child("Member").child(emuser);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                if(snapshot.child("e_name").exists() && snapshot.child("e_phone").exists() && snapshot.child("e_msg").exists()) {

                    String nm = snapshot.child("e_name").getValue().toString();
                    String phn = snapshot.child("e_phone").getValue().toString();
                    String msgg = snapshot.child("e_msg").getValue().toString();

                    name.setHint(nm);
                    contact.setHint(phn);
                    msg.setHint(msgg);
                }else
                {
                    name.setHint("(Name) no data set yet");
                    contact.setHint("(Contact) no data set yet");
                    msg.setHint("(Message) no data set yet");
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SOS_Activity.this, "Hold for update or set data", Toast.LENGTH_SHORT).show();
            }
        });
        confirm.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                String e_name = name.getText().toString();
                String e_phone = contact.getText().toString();
                String e_msg = msg.getText().toString();

                if(e_name.isEmpty() && e_msg.isEmpty() && e_phone.isEmpty()){
                    Toast.makeText(SOS_Activity.this, "insert all data", Toast.LENGTH_SHORT).show();
                }else {
                    if(!e_name.isEmpty() )
                    {
                        reff.child("e_name").setValue(e_name);
                    }
                    if(!e_phone.isEmpty() )
                    {
                        reff.child("e_phone").setValue(e_phone);
                    }
                    if(!e_msg.isEmpty() )
                    {
                        reff.child("e_msg").setValue(e_msg);
                    }

                    Toast.makeText(SOS_Activity.this, "Emergency Contact Saved!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SOS_Activity.this, Confirm_box_Activity.class);
                    intent.putExtra("confirm",emuser);
                    intent.putExtra("flag",1);

                    startActivity(intent);

                }
                return false;
            }
        });
    }
}