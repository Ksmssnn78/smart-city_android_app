package com.example.smartcityapp2;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Wallet_Activity extends AppCompatActivity {
    EditText cardno,cardcvv,carddate,amnt1;
    Button wallbtn;
    String crd1,cv1,cdt1,crd2,cv2,cdt2,temp_amnt;
    String user;
    int f=0;
    long amount1,amount2,credit;
    DatabaseReference reference,reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        cardno = (EditText)findViewById(R.id.editTxtcardno);
        carddate = (EditText)findViewById(R.id.editTxtcarddate);
        cardcvv = (EditText)findViewById(R.id.editTxtcardcvv);
        amnt1 = (EditText)findViewById(R.id.editTxtAmnt);
        wallbtn = (Button)findViewById(R.id.wallbtn);

        user = getIntent().getStringExtra("Keyname2");
        //System.out.println(user);
        reference2 = FirebaseDatabase.getInstance().getReference().child("Member").child(user);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    credit = Integer.parseInt(snapshot.child("credit").getValue().toString());
                    System.out.println(credit);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    public void wallet(View view) {
        crd1 = cardno.getText().toString();
        cdt1 = carddate.getText().toString();
        cv1 = cardcvv.getText().toString();
        temp_amnt = amnt1.getText().toString();

        if(!(crd1.isEmpty()) || !(cdt1.isEmpty()) || !(cv1.isEmpty()) || !(temp_amnt.isEmpty()))
        {
            amount1 = Integer.parseInt(temp_amnt);
            reference = FirebaseDatabase.getInstance().getReference().child("bankAcc");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        crd2 = snapshot.child("accountNo").getValue().toString();
                        cdt2 = snapshot.child("expDate").getValue().toString();
                        cv2 = snapshot.child("pin").getValue().toString();
                        amount2 = Integer.parseInt(snapshot.child("amount").getValue().toString());

                        System.out.println(cdt1);


                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
        }else{
            Toast.makeText(Wallet_Activity.this,"please insert credential", Toast.LENGTH_SHORT).show();
        }

        if( crd1.equals(crd2) && cdt1.equals(cdt2) && cv1.equals(cv2) && f==1 )
        {

            if(amount2>=amount1)
            {
                amount2 = amount2-amount1;
                reference.child("amount").setValue(amount2);
                credit = credit+amount1;
                reference2.child("credit").setValue(credit);

                Intent intent = new Intent(Wallet_Activity.this, Confirm_Box_3.class);
                intent.putExtra("confirm3",user);
                startActivity(intent);
                //System.out.println("adding cash done!!");

            }else{
                Toast.makeText(Wallet_Activity.this,"Not Enough Balance!", Toast.LENGTH_SHORT).show();
            }
        }else if( f == 0 ){
            f=1;
            Toast.makeText(Wallet_Activity.this,"Press again to confirm!", Toast.LENGTH_SHORT).show();
        }else{
            f=0;
            Toast.makeText(Wallet_Activity.this,"Wrong Credential!", Toast.LENGTH_SHORT).show();
        }

    }
}