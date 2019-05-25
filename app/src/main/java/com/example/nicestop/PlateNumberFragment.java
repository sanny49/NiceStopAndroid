package com.example.nicestop;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlateNumberFragment extends Fragment {
 TextView textViewPlateNumber,textViewMake,textViewColor,textViewOwner,textViewAddress;
 EditText editTextPlateNo;
 Button buttonEnter;
DatabaseReference reff;
    public PlateNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
       View view= inflater.inflate(R.layout.fragment_plate_number, container, false);
        editTextPlateNo = (EditText) view.findViewById(R.id.editTextPlateNo);
        buttonEnter = (Button) view.findViewById(R.id.buttonEnter);
        textViewPlateNumber = (TextView)view.findViewById(R.id.textViewPlateNumber);
        textViewMake = (TextView)view.findViewById(R.id.textViewMake);
        textViewColor = (TextView)view.findViewById(R.id.textViewColor);
        textViewOwner = (TextView)view.findViewById(R.id.textViewOwner);
        textViewAddress = (TextView)view.findViewById(R.id.textViewAddress);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cd = editTextPlateNo.getText().toString();
                reff = FirebaseDatabase.getInstance().getReference().child("Vehicle");
                reff.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String editCode = dataSnapshot.child("plate_number").getValue().toString();
                        if(cd.equals(editCode)) {
                            String pl = dataSnapshot.child("plate_number").getValue().toString();
                            String m = dataSnapshot.child("make").getValue().toString();
                            String c = dataSnapshot.child("color").getValue().toString();
                            String o = dataSnapshot.child("owner").getValue().toString();
                            String a = dataSnapshot.child("address").getValue().toString();
                            textViewPlateNumber.setText("Plate No :  "+pl);
                            textViewMake.setText("Make :        "+m);
                            textViewColor.setText("Color :        "+c);
                            textViewOwner.setText("Owner :       "+o);
                            textViewAddress.setText("Address :    "+a);
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Plate Number is not Registered! ",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

       return view;

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_plate_number, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.ActionPlateNumber){
            Toast.makeText(getActivity(),"Click on "+ item.getTitle(),Toast.LENGTH_SHORT).show();
        }
        return true;

    }

}
