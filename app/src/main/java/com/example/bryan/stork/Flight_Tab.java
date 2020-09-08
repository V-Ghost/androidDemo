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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Bryan on 11/21/2018.
 */
public class Flight_Tab extends Fragment {
    private ListView lv;
    private ArrayList<String> array = new ArrayList<>();
    private ArrayList<String> array2 = new ArrayList<>();
    private ArrayList<String> array3 = new ArrayList<>();
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.flight_tab, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("flights");
        lv = (ListView) rootView.findViewById(R.id.listf);
        final ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, array);
//        lv.setAdapter(aa);
        lv.setAdapter(aa);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                final String Destination = dataSnapshot.getValue(String.class);
                final String Time = dataSnapshot.getKey();
                array2.add(Time);
                array3.add(Destination);
//                array.add(value);
//                aa.notifyDataSetChanged();

//                for (final DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                    String Destination = ds.getKey();
//                 String Time = ds.getValue(String.class);
                String Trips = TextUtils.join("  |  ", new String[]{Destination,Time});
                array.add(Trips);
                aa.notifyDataSetChanged();
//
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        String d = array3.get(position);
                        String k = array2.get(position);
                        Intent intent = (new Intent(getActivity(), Ticket_Checkout.class));
//                                TextView textView4 = findViewById(R.id.textView30);
//                                String user = textView4.getText().toString();
                        intent.putExtra("time",d);
                        intent.putExtra("destination",k);

//                                String selectedFromList =(String) (Time.getItemAtPosition(TripID));
                        startActivity(intent);



                    }
                });
//
//                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootView;
    }








}
