package com.example.bryan.stork;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Ticket_Checkout extends AppCompatActivity {
private TextView d,t,price;
private EditText quan;
Button b1;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket__checkout);
        d = findViewById(R.id.dest);
        t = findViewById(R.id.timet);
        quan = findViewById(R.id.quantity);
        b1 = findViewById(R.id.ordert);
        price = findViewById(R.id.price);
        Intent intent = getIntent();
        final String destination = intent.getStringExtra("destination");
        final String time = intent.getStringExtra("time");

        d.setText("Destination :" + time);
        t.setText("time :" + destination);
        price.setText("price: " +  "$400"  + " per ticket");


//        int random = (int )(Math.random() * 50 + 1);
//        int p = Integer.parseInt(pins);
//        int dprice = random * p;
//        String sprice =  "$" +String.valueOf(dprice) ;
//        price.setText(sprice);


        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("trips");

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pins = quan.getText().toString();
                    String Trips = TextUtils.join("  |  ", new String[]{destination, time, pins});
                    if (validate(pins)){

                        mDatabase.child(currentuser).push().setValue(Trips, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError != null) {
                                    Toast.makeText(Ticket_Checkout.this, "Database error", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(Ticket_Checkout.this, "Tickets Have Been Purchased", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Ticket_Checkout.this, Tickets.class));
                                }
                            }
                        });
                    }else{
                        Toast.makeText(Ticket_Checkout.this, "Fill All Spaces", Toast.LENGTH_LONG).show();
                    } }
            });

    }
    public Boolean validate(String user){

        if(TextUtils.isEmpty(user)  ){
            return  false;
        }else{
            return true;
        }
    }
}
