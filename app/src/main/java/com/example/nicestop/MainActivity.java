package com.example.nicestop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.nicestop.Model.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    private DatabaseReference ref;
    EditText editTextCode, editTextPassword;
    Button buttonCode;
    User user;
    private List<User> users = new ArrayList<>();
    boolean session;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SESSION();
        editTextCode = (EditText) findViewById(R.id.editTextCode);
        ref = FirebaseDatabase.getInstance().getReference().child("Enforcer");
        editTextCode = (EditText) findViewById(R.id.editTextCode);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonCode = (Button) findViewById(R.id.buttonCode);

        buttonCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final String cd = editTextCode.getText().toString();
                    final String pass = editTextPassword.getText().toString();
                if (cd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Your Code!", Toast.LENGTH_SHORT).show();
                }
                else if(pass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter Your Password!", Toast.LENGTH_SHORT).show();
                }
                else {
                    ref.child("1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                   String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    //Intent s = new Intent(MainActivity.this, menuTab.class);
                                    //startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("2").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("3").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("4").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("5").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("6").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("7").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("8").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("9").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("10").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("11").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("12").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    //UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("13").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("14").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("15").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("16").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("17").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();

                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("18").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    //UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("19").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    // UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    ref.child("20").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String code = dataSnapshot.child("Code").getValue().toString();
                                String pw= dataSnapshot.child("Password").getValue().toString();
                                if (cd.equals(code)&& pass.equals(pw)) {
                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Value", fn+" "+ln);
                                    editor.apply();
                                    //UtilsClipCode.saveSharedSetting(MainActivity.this, "ClipCodes", "false");
                                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Login.save(getApplicationContext(),"session","true");
                                    Intent s = new Intent(MainActivity.this, menuTab.class);
                                    startActivity(s);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Code is Wrong!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

    }
    public void SESSION(){
        session = Boolean.valueOf(Login.read(getApplicationContext(),"session","false"));
        if(!session){

        }else{

        }


    }
   /* @Override
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
    }*/
    }

   /* private void signIn(final String Code) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Code).exists()){
                    if(!Code.isEmpty()){

                        if(login.getCode().equals(Code))
                        {
                            Toast.makeText(MainActivity.this,"Login Success!",Toast.LENGTH_SHORT).show();
                            Intent s = new Intent(getApplicationContext(),menuTab.class);
                            startActivity(s);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Code is Wrong!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Code is not Registered!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/
   /* public void onClickImageButtonListener(){
        button_code = (Button) findViewById(R.id.buttonCode);
        button_code.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(".menuTab");
                        startActivity(intent);
                    }
                }
        );
    }

   public void OnLogin(View view) {
        String code = et_code.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, code);


    }*/

