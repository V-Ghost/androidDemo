package com.example.bryan.stork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Loginup extends AppCompatActivity {

    public void  click(View view){

        startActivity(new Intent(Loginup.this,Signup.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginup);
        edittextEmail = findViewById(R.id.login_email);
        edittextPassword = findViewById(R.id.login_password);
        mProgress = new ProgressDialog(Loginup.this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

    }
    TextView edittextEmail;
    TextView edittextPassword;
    FirebaseAuth firebaseAuth;
    ProgressDialog mProgress;


    public void login(View view){
        String email = edittextEmail.getText().toString().trim();
        String password = edittextPassword.getText().toString().trim();
        firebaseAuth = FirebaseAuth.getInstance();

        if (!validate(email,password)){
            Toast.makeText(Loginup.this,"Fill All Spaces", Toast.LENGTH_LONG).show();

        }else {
            mProgress.show();
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Loginup.this,"Login Successful",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Loginup.this, Home_screen.class));
                        mProgress.dismiss();
                    }else{
                        mProgress.dismiss();
                        Toast.makeText(Loginup.this,"Unsuccessful",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    }

    public boolean validate(String email,String password){
        if(TextUtils.isEmpty(email) ||  TextUtils.isEmpty(password) ){
            return  false;
        }else{
            return true;
        }


    }

}
