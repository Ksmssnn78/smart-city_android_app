package com.example.smartcityapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Activity extends AppCompatActivity {
    Button login;
    EditText user, passwd;
    String m1,m2,pass1,pass2;
    long id = 0;
    long flag=0;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText)findViewById(R.id.username_login);
        passwd = (EditText)findViewById(R.id.pass_login);

        reference = FirebaseDatabase.getInstance().getReference().child("Member");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    System.out.println("Ondatachange....");
                    id = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }

    public void check_credential(View v) {

        m1 = user.getText().toString();
        pass1 = passwd.getText().toString();
        reference = FirebaseDatabase.getInstance().getReference().child("Member");

        long i =1;
        if(!(m1.isEmpty()) || !(pass1.isEmpty()))
        {
            for(i=1;i<=id;i++){
                String users = "user-"+String.valueOf(i);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    boolean f = true;
                    int count=0;
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        if (snapshot.exists() && flag != 1) {
                            m2 = snapshot.child(users).child("mail").getValue().toString();
                            pass2 = snapshot.child(users).child("password").getValue().toString();
                            System.out.println(m2);
                            System.out.println(pass2);
                            if(m1.equals(m2) && pass1.equals(pass2))
                            {
                                flag =1;
                                f = false;
                                Intent intent = new Intent(Login_Activity.this, MainMenuActivity.class);
                                intent.putExtra("Keyname",users);
                                startActivity(intent);
                            }else if(f){
                                System.out.println("id value:" +id);
                                String tmp = users.substring(5,users.length());
                                count = Integer.parseInt(tmp);
                                System.out.println("count value:" +count);
                                if(count == id)
                                {
                                    Toast.makeText(Login_Activity.this,"Wrong credential", Toast.LENGTH_SHORT).show();
                                }

                            }

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
                System.out.println("loop: "+i);
            }
        }
        else{
            Toast.makeText(Login_Activity.this,"please insert credential", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Login_Activity.this,Welcome_Activity.class);
        startActivity(intent);
    }


}