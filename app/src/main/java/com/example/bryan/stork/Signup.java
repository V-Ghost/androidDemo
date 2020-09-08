package com.example.bryan.stork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        user   = (EditText)findViewById(R.id.signup_username);
        email  = (EditText)findViewById(R.id.signup_email);
        password   = (EditText)findViewById(R.id.signup_password);
        cpassword  = (EditText)findViewById(R.id.signup_cpassword);
        signup = (Button)findViewById(R.id.signup);
        payment = (EditText)findViewById(R.id.signup_paymentnumber);
        pin = (EditText)findViewById(R.id.pin);
        mProgress = new ProgressDialog(Signup.this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
    }
    TextView user ,email,cpassword,password,payment,pin;
    Button signup;
    FirebaseAuth firebaseAuth ;
    ProgressDialog mProgress;
   private DatabaseReference mDatabase;

    public void nextclick(View view) {

       final String username =  user.getText().toString();
        final String semail =  email.getText().toString();
       final String spassword =  password.getText().toString();
       final String scpassword =  cpassword.getText().toString();
       final String spin =  pin.getText().toString();
       final String spayment =  payment.getText().toString();

        firebaseAuth = FirebaseAuth.getInstance();
        Boolean error = true;

        if (!validatePassword(spassword,scpassword)){

            Toast.makeText(Signup.this,"Passwords don't match",Toast.LENGTH_SHORT).show();
            error = false;
        }
        if (!validate(username,semail,spassword,scpassword ,spin,spayment)){
            Toast.makeText(Signup.this,"Fill All Spaces",Toast.LENGTH_LONG).show();
            error = false;
        }

        if (error) {
            mProgress.show();

            mDatabase = FirebaseDatabase.getInstance().getReference().child("users");



            firebaseAuth.createUserWithEmailAndPassword(semail, spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Signup.this, "SignUp Succesful", Toast.LENGTH_SHORT).show();
                        users user = new users(username,semail,spassword,spayment,spin);
                        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        DatabaseReference newPostRef =mDatabase;
                        newPostRef.child(currentuser).setValue(user,new
                                DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference
                                            databaseReference) {

                                        if (databaseError != null) {
                                            Toast.makeText(Signup.this, "Database error", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                        startActivity(new Intent(Signup.this, Home_screen.class));
                        mProgress.dismiss();
                    } else {
                        mProgress.dismiss();
                        Toast.makeText(Signup.this, "SignUp unSuccessful", Toast.LENGTH_SHORT).show();


                    }
                }
            });

        }


    }
    public Boolean validate(String user,String email,String password,String cpassword,String n,String p){

        if(TextUtils.isEmpty(user) ||  TextUtils.isEmpty(email) ||  TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword) || TextUtils.isEmpty(n) || TextUtils.isEmpty(p) ){
            return  false;
        }else{
            return true;
        }
    }

    public Boolean validatePassword(String password,String cpassword){

        if(password.equals(cpassword)){
            return true;

        }else{
            return false;
        }


    }



}