package com.example.bryan.stork;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Bryan on 11/21/2018.
 */

public class Cargo_Tab extends Fragment {

    private EditText e1,e2,e3,e4;
    Button b1;
    boolean error;
    private DatabaseReference mDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cargo_tab, container, false);
        e1 = rootView.findViewById(R.id.weight);
        e2 = rootView.findViewById(R.id.to);
        e3 = rootView.findViewById(R.id.from);
        e4 = rootView.findViewById(R.id.area);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("trips");

        b1 = rootView.findViewById(R.id.v1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String weight = e1.getText().toString();
                String  to = e2.getText().toString();
                String from = e3.getText().toString();
                String  area = e4.getText().toString();
                if (validate(weight,to,from,area)){

                final String Trips = TextUtils.join("  |  ", new String[]{to, from, weight,area});
                mDatabase.child(currentuser).push().setValue(Trips,new DatabaseReference.CompletionListener() {

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(getActivity(), "Database error", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Database good", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getActivity(), Tickets.class));
                        }
                    }
                });
            }else{
                    Toast.makeText(getActivity(), "Fill all Spaces", Toast.LENGTH_LONG).show();

                }}
        });


        return rootView;
    }
    public Boolean validate(String user,String email,String password,String cpassword){

        if(TextUtils.isEmpty(user) ||  TextUtils.isEmpty(email) ||  TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword) ){
            return  false;
        }else{
            return true;
        }
    }


}
