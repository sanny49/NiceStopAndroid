package com.example.nicestop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Violators extends AppCompatActivity {
    ImageView button_back;
    ListView listViewViolators;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter <String> adapter;
    Violator violator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violators);
        //onClickImageButtonListener();
        Toolbar toolbar = findViewById(R.id.toolbarViolator);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Violators");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        violator = new Violator();
        listViewViolators = (ListView)findViewById(R.id.listViewViolators);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Violator");
        list = new ArrayList<>();
       adapter = new ArrayAdapter<String>(this, R.layout.violator_info,R.id.textViewTo,list);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    violator = ds.getValue(Violator.class);
                    list.add("TVB No: "+violator.getTVBNo().toString()+"\n"+violator.getType().toString()+"\nDate: "+violator.getDate().toString()
                            +"\nTime: "+violator.getTime().toString()+"\nTo: "+violator.getTo().toString()+"\nLicense No: "+violator.getLicenseNo().toString()
                            +"\nAddress: "+violator.getAddress1().toString()+"\nPlate No: "+violator.getPlateNo().toString()+"\nMake: "+violator.getMake().toString()
                            +"\nColor: "+violator.getColor().toString()+"\nOwner: "+violator.getOwner().toString()+"\nAddress: "+violator.getAddress2().toString()
                            +"\nViolation: "+violator.getViolation().toString()+"\nEnforcer: "+violator.getEnforcer().toString()+"\n\n");
                }
                listViewViolators.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

   /* public void onClickImageButtonListener(){
        button_back = (ImageView)findViewById(R.id.imageViewBack2);
        button_back.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(".menuTab");
                        startActivity(intent);
                    }
                }
        );
    }*/
}