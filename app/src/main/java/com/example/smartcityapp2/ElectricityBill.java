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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ElectricityBill extends AppCompatActivity {
    String name="",mail="",phone="";
    EditText billno,consumer,amnt;
    Button e_pay;
    String b_no,c_no,user,temp_amnt;
    long amnt2,database_amnt;
    long mid=0;
    DatabaseReference reff,reff2;
    Bill_Records records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_bill);

        billno = (EditText)findViewById(R.id.billno);
        consumer = (EditText)findViewById(R.id.consumer);
        amnt = (EditText)findViewById(R.id.amount);
        e_pay = (Button) findViewById(R.id.E_pay);
        user = getIntent().getStringExtra("e_user");
        records = new Bill_Records();

        reff = FirebaseDatabase.getInstance().getReference().child("Member").child(user);
        reff2 = FirebaseDatabase.getInstance().getReference().child("ElectricityBill");
        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mid = snapshot.getChildrenCount();
                System.out.println("child count--> " + mid);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        e_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ElectricityBill.this, "Hold For Proceed", Toast.LENGTH_SHORT).show();
            }
        });
        e_pay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                b_no = billno.getText().toString();
                c_no = consumer.getText().toString();
                temp_amnt = amnt.getText().toString();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if( !(b_no.isEmpty()) || !(c_no.isEmpty()) || !(temp_amnt.isEmpty()))
                {

                    amnt2 = Long.parseLong(temp_amnt);
                    reff.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                name = snapshot.child("name").getValue().toString();
                                mail = snapshot.child("mail").getValue().toString();
                                phone = snapshot.child("phone").getValue().toString();
                                database_amnt = Long.parseLong(snapshot.child("credit").getValue().toString());
                                if(amnt2<=database_amnt)
                                {


                                    System.out.println(mail);
                                    if( !(name.isEmpty()) || !(mail.isEmpty()) || !(phone.isEmpty())){
                                        System.out.println(name);
                                        records.setBillNo(b_no);
                                        records.setConsumerno(c_no);
                                        records.setAmount(amnt2);
                                        records.setName(name);
                                        records.setMail(mail);
                                        records.setPhone(phone);
                                        records.setDate(date);

                                        reff2.child("Bill->"+String.valueOf(mid+1)).setValue(records);
                                        System.out.println("inside of 2nd if  till last and works");
                                        database_amnt = database_amnt - amnt2;
                                        reff.child("credit").setValue(database_amnt);
                                        Intent intent = new Intent(ElectricityBill.this, Confirm_Box_2.class);
                                        intent.putExtra("confirm2",user);
                                        startActivity(intent);

                                    }else{
                                        Toast.makeText(ElectricityBill.this,"something went wrong in database!", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(ElectricityBill.this,"You don't have enough cash!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                }else{
                    Toast.makeText(ElectricityBill.this,"input all data!", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
    }

}