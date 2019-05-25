package com.example.nicestop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicestop.Model.Login;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.integration.android.IntentIntegrator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class menuTab extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Vehicle vehicle;
    License license;
    TextView textViewTo, textViewLicenseNo, textViewAddress1;
    private DatabaseReference reff, mDatabase;
    TextView textViewPlateNumber, textViewMake, textViewColor, textViewOwner, textViewAddress2;
    TextView textViewLocation;
    EditText editTextPlateNo, editTextLicenseNo, editTextSearch;
    Button buttonEnter, buttonSave, btnscan, buttonOk, buttonScan1, buttonLocation;
    CheckBox checkBoxDO, checkBoxSK, checkBoUV;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10, checkBox11;
    String result;
    String item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11;
    String[] items;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    FirebaseDatabase database;
    DatabaseReference ref;
    SearchView searchView;
    ImageView imageViewPic;
    private static final int RESULT_LOAD_IMAGE = 1;
    TextView textViewName;
    String name;
    private Uri selectedImage, imageUri;
    FirebaseStorage storage;
    private StorageReference storageReference;
    long counter = 0;
    boolean session;
    View nview;
    Geocoder geocoder;
    List<Address> addresses;
    LocationManager locationManager;
    String latitude,longtitude;
    int p1=500;
    int p2=510;
    int p3=520;
    int p4=530;
    int p5=540;
    int p6=550;
    int p7=560;
    int p8=570;
    int p9=580;
    int p10=590;
    int p11=600;
    private static final int REQUEST_LOCATION = 1;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displaySelectedScreen(R.id.Ticket);
        imageUri = null;

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Images");
        storageReference = FirebaseStorage.getInstance().getReference().child("Images");

        CheckUser();
        SESSION();

        //FragmentManager manager = getSupportFragmentManager();
        //final android.support.v4.app.FragmentTransaction t= manager.beginTransaction();
        //final LicenseNumberFragment lincenseNumberFragment = new LicenseNumberFragment();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("Name", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Value", textViewName.getText().toString());
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), Save.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //storage = FirebaseStorage.getInstance();
        //storageReference= storage.getReference();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        nview = navigationView.getHeaderView(0);
        textViewName = (TextView) nview.findViewById(R.id.textViewName);
        imageViewPic = (ImageView) nview.findViewById(R.id.imageViewPic);
        imageViewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), RESULT_LOAD_IMAGE);

               /* Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),RESULT_LOAD_IMAGE);
                 if(selectedImage!=null){
                   final ProgressDialog progressDialog = new ProgressDialog(menuTab.this);
                    progressDialog.setTitle("Uploading Image...");
                    progressDialog.show();

                   StorageReference ref = storageReference.child("image/"+ UUID.randomUUID().toString());
                    ref.putFile(selectedImage)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                                    progressDialog.dismiss();
                                    Uri downloadUri = taskSnapshot.getUploadSessionUri();
                                    Picasso.with(menuTab.this).load(downloadUri).fit().centerCrop().into(imageViewPic);
                                    Toast.makeText(menuTab.this, "Uploaded!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener(){
                                @Override
                                        public void onFailure(@NonNull Exception e){
                                    progressDialog.dismiss();
                                    Toast.makeText(menuTab.this, "Failed!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                                .addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>(){
                                 @Override
                                 public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                     double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                     progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                     Toast.makeText(menuTab.this, "Uploaded!", Toast.LENGTH_SHORT).show();
                                 }

                            });


                }*/
               /* switch (v.getId()){
                    case R.id.imageViewPic:
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(gallery,RESULT_LOAD_IMAGE);
                        break;
                }*/
            }
        });

        SharedPreferences resul1 = getSharedPreferences("Name", Context.MODE_PRIVATE);
        name = resul1.getString("Value", "");
        textViewName.setText(name);
        navigationView.setNavigationItemSelectedListener(this);

        //Scan
        license = new License();
        // btnscan = (Button)findViewById(R.id.buttonScan);
        checkBoxDO = (CheckBox) findViewById(R.id.checkBoxDO);
        checkBoxSK = (CheckBox) findViewById(R.id.checkBoxSK);
        checkBoUV = (CheckBox) findViewById(R.id.checkBoUV);

        textViewTo = (TextView) findViewById(R.id.textViewTo);
        textViewLicenseNo = (TextView) findViewById(R.id.textViewLicenseNo);
        textViewAddress1 = (TextView) findViewById(R.id.textViewAddress1);
        editTextLicenseNo = (EditText) findViewById(R.id.editTextLicenseNo);
        buttonOk = (Button) findViewById(R.id.buttonOk);
         /*btnscan.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //scannow();
             }
         });*/
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pn = editTextLicenseNo.getText().toString();
                if (pn.isEmpty()) {
                    Toast.makeText(menuTab.this, "Scan the Driver License!", Toast.LENGTH_SHORT).show();
                } else {
                    reff = FirebaseDatabase.getInstance().getReference().child("License");
                    reff.child(pn).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String barcode = dataSnapshot.child("Barcode").getValue().toString();
                                if (pn.equals(barcode)) {
                                    Toast.makeText(menuTab.this, "Driver's License Found! ", Toast.LENGTH_SHORT).show();
                                    String ln = dataSnapshot.child("LicenseNo").getValue().toString();
                                    String t = dataSnapshot.child("To").getValue().toString();
                                    String a = dataSnapshot.child("Address").getValue().toString();
                                    textViewTo.setText(t);
                                    textViewLicenseNo.setText(ln);
                                    textViewAddress1.setText(a);
                                }
                            } else {
                                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(menuTab.this);
                                alertdialogbuilder.setMessage("Driver License is not registered!\n" + "Scan Again ?");
                                alertdialogbuilder.setTitle("Result Scanned");
                                alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(menuTab.this, ScanBarcode.class);
                                        startActivityForResult(intent, 0);
                                        //scannow();
                                    }
                                });
                                alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                AlertDialog alertDialog = alertdialogbuilder.create();
                                alertDialog.show();
                                Toast.makeText(menuTab.this, "Driver License is not Registered! ", Toast.LENGTH_SHORT).show();
                            }

                               /*  String editCode = dataSnapshot.child("LicenseNo").getValue().toString();
                            if(result.getContents().equals(editCode)) {
                                Toast.makeText(menuTab.this,"License Number Found! ",Toast.LENGTH_SHORT).show();

                                String pl = dataSnapshot.child("Barcode").getValue().toString();
                                String m = dataSnapshot.child("To").getValue().toString();
                                String c = dataSnapshot.child("Address").getValue().toString();
                                textViewTo.setText(m);
                                textViewLicenseNo.setText(pl);
                                textViewAddress1.setText(c);

                            }
                            else
                            {
                                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(menuTab.this);
                                alertdialogbuilder.setMessage(result.getContents()+" is not registered!\n"+"Scan Again ?");
                                alertdialogbuilder.setTitle("Result Scanned");
                                alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        scannow();
                                    }
                                });
                                alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                AlertDialog alertDialog = alertdialogbuilder.create();
                                alertDialog.show();
                                Toast.makeText(menuTab.this,"License Number is not Registered! ",Toast.LENGTH_SHORT).show();
                            }*/
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        //Plate Number
        editTextPlateNo = (EditText) findViewById(R.id.editTextPlateNo);
        buttonEnter = (Button) findViewById(R.id.buttonEnter);
        buttonScan1 = (Button) findViewById(R.id.buttonScan1);
        textViewPlateNumber = (TextView) findViewById(R.id.textViewPlateNumber);
        textViewMake = (TextView) findViewById(R.id.textViewMake);
        textViewColor = (TextView) findViewById(R.id.textViewColor);
        textViewOwner = (TextView) findViewById(R.id.textViewOwner);
        textViewAddress2 = (TextView) findViewById(R.id.textViewAddress2);
        vehicle = new Vehicle();
        list = new ArrayList<>();

        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cd = editTextPlateNo.getText().toString();
                if (cd.isEmpty()) {
                    Toast.makeText(menuTab.this, "Enter Plate Number!", Toast.LENGTH_SHORT).show();
                } else {
                    ref = FirebaseDatabase.getInstance().getReference().child("vehicle");
                    ref.child(cd).addValueEventListener(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String editCode = dataSnapshot.child("plate_number").getValue().toString();
                                if (cd.equals(editCode)) {
                                    String pl = dataSnapshot.child("plate_number").getValue().toString();
                                    String m = dataSnapshot.child("make").getValue().toString();
                                    String c = dataSnapshot.child("color").getValue().toString();
                                    String o = dataSnapshot.child("owner").getValue().toString();
                                    String a = dataSnapshot.child("address").getValue().toString();
                                    textViewPlateNumber.setText(pl);
                                    textViewMake.setText(m);
                                    textViewColor.setText(c);
                                    textViewOwner.setText(o);
                                    textViewAddress2.setText(a);
                                } else {
                                    Toast.makeText(menuTab.this, "Plate Number is not Registered! ", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(menuTab.this, "Plate Number is not Registered! ", Toast.LENGTH_SHORT).show();
                            }
                       /*if(cd.equals(editCode)) {
                            String pl = dataSnapshot.child("plate_number").getValue().toString();
                            String m = dataSnapshot.child("make").getValue().toString();
                            String c = dataSnapshot.child("color").getValue().toString();
                            String o = dataSnapshot.child("owner").getValue().toString();
                            String a = dataSnapshot.child("address").getValue().toString();
                            textViewPlateNumber.setText(pl);
                            textViewMake.setText(m);
                            textViewColor.setText(c);
                            textViewOwner.setText(o);
                            textViewAddress2.setText(a);
                        }
                        else
                        {
                            Toast.makeText(menuTab.this,"Plate Number is not Registered! ",Toast.LENGTH_SHORT).show();
                        }*/
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        //Violation
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox) findViewById(R.id.checkBox6);
        checkBox7 = (CheckBox) findViewById(R.id.checkBox7);
        checkBox8 = (CheckBox) findViewById(R.id.checkBox8);
        checkBox9 = (CheckBox) findViewById(R.id.checkBox9);
        checkBox10 = (CheckBox) findViewById(R.id.checkBox10);
        checkBox11 = (CheckBox) findViewById(R.id.checkBox11);
        //Save
        editTextLicenseNo.setEnabled(false);
        buttonScan1.setEnabled(false);
        buttonOk.setEnabled(false);
        buttonEnter.setEnabled(false);
        editTextPlateNo.setEnabled(false);
        buttonScan1.setTextColor(Color.RED);
        buttonOk.setTextColor(Color.RED);
        buttonEnter.setTextColor(Color.RED);
        checkBox1.setEnabled(false);
        checkBox2.setEnabled(false);
        checkBox3.setEnabled(false);
        checkBox4.setEnabled(false);
        checkBox5.setEnabled(false);
        checkBox6.setEnabled(false);
        checkBox7.setEnabled(false);
        checkBox1.setEnabled(false);
        checkBox8.setEnabled(false);
        checkBox9.setEnabled(false);
        checkBox10.setEnabled(false);
        checkBox11.setEnabled(false);
        checkBoxDO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editTextLicenseNo.setEnabled(true);
                    buttonScan1.setEnabled(true);
                    buttonOk.setEnabled(true);
                    buttonEnter.setEnabled(true);
                    editTextPlateNo.setEnabled(true);
                    buttonScan1.setTextColor(Color.WHITE);
                    buttonOk.setTextColor(Color.WHITE);
                    buttonEnter.setTextColor(Color.WHITE);
                    checkBox1.setEnabled(true);
                    checkBox2.setEnabled(true);
                    checkBox3.setEnabled(true);
                    checkBox4.setEnabled(true);
                    checkBox5.setEnabled(true);
                    checkBox6.setEnabled(true);
                    checkBox7.setEnabled(true);
                    checkBox1.setEnabled(true);
                    checkBox8.setEnabled(true);
                    checkBox9.setEnabled(true);
                    checkBox10.setEnabled(true);
                    checkBox11.setEnabled(true);
                    checkBoxSK.setVisibility(View.INVISIBLE);
                    checkBoUV.setVisibility(View.INVISIBLE);
                } else {
                    editTextLicenseNo.setEnabled(false);
                    buttonScan1.setEnabled(false);
                    buttonOk.setEnabled(false);
                    buttonEnter.setEnabled(false);
                    editTextPlateNo.setEnabled(false);
                    buttonScan1.setTextColor(Color.RED);
                    buttonOk.setTextColor(Color.RED);
                    buttonEnter.setTextColor(Color.RED);
                    checkBox1.setEnabled(false);
                    checkBox2.setEnabled(false);
                    checkBox3.setEnabled(false);
                    checkBox4.setEnabled(false);
                    checkBox5.setEnabled(false);
                    checkBox6.setEnabled(false);
                    checkBox7.setEnabled(false);
                    checkBox1.setEnabled(false);
                    checkBox8.setEnabled(false);
                    checkBox9.setEnabled(false);
                    checkBox10.setEnabled(false);
                    checkBox11.setEnabled(false);
                    checkBoxSK.setVisibility(View.VISIBLE);
                    checkBoUV.setVisibility(View.VISIBLE);
                }
            }
        });
        checkBoxSK.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editTextLicenseNo.setEnabled(true);
                    buttonScan1.setEnabled(true);
                    buttonOk.setEnabled(true);
                    buttonEnter.setEnabled(true);
                    editTextPlateNo.setEnabled(true);
                    buttonScan1.setTextColor(Color.WHITE);
                    buttonOk.setTextColor(Color.WHITE);
                    buttonEnter.setTextColor(Color.WHITE);
                    checkBox1.setEnabled(true);
                    checkBox2.setEnabled(true);
                    checkBox3.setEnabled(true);
                    checkBox4.setEnabled(true);
                    checkBox5.setEnabled(true);
                    checkBox6.setEnabled(true);
                    checkBox7.setEnabled(true);
                    checkBox1.setEnabled(true);
                    checkBox8.setEnabled(true);
                    checkBox9.setEnabled(true);
                    checkBox10.setEnabled(true);
                    checkBox11.setEnabled(true);
                    checkBoxDO.setVisibility(View.INVISIBLE);
                    checkBoUV.setVisibility(View.INVISIBLE);
                } else {
                    editTextLicenseNo.setEnabled(false);
                    buttonScan1.setEnabled(false);
                    buttonOk.setEnabled(false);
                    buttonEnter.setEnabled(false);
                    editTextPlateNo.setEnabled(false);
                    buttonScan1.setTextColor(Color.RED);
                    buttonOk.setTextColor(Color.RED);
                    buttonEnter.setTextColor(Color.RED);
                    checkBox1.setEnabled(false);
                    checkBox2.setEnabled(false);
                    checkBox3.setEnabled(false);
                    checkBox4.setEnabled(false);
                    checkBox5.setEnabled(false);
                    checkBox6.setEnabled(false);
                    checkBox7.setEnabled(false);
                    checkBox1.setEnabled(false);
                    checkBox8.setEnabled(false);
                    checkBox9.setEnabled(false);
                    checkBox10.setEnabled(false);
                    checkBox11.setEnabled(false);
                    checkBoxDO.setVisibility(View.VISIBLE);
                    checkBoUV.setVisibility(View.VISIBLE);
                }
            }
        });
        checkBoUV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonEnter.setEnabled(true);
                    editTextPlateNo.setEnabled(true);
                    buttonEnter.setTextColor(Color.WHITE);
                    checkBox1.setEnabled(true);
                    checkBox2.setEnabled(true);
                    checkBox3.setEnabled(true);
                    checkBox4.setEnabled(true);
                    checkBox5.setEnabled(true);
                    checkBox6.setEnabled(true);
                    checkBox7.setEnabled(true);
                    checkBox1.setEnabled(true);
                    checkBox8.setEnabled(true);
                    checkBox9.setEnabled(true);
                    checkBox10.setEnabled(true);
                    checkBox11.setEnabled(true);
                    checkBoxSK.setVisibility(View.INVISIBLE);
                    checkBoxDO.setVisibility(View.INVISIBLE);

                } else {
                    buttonEnter.setEnabled(false);
                    editTextPlateNo.setEnabled(false);
                    buttonEnter.setTextColor(Color.RED);
                    checkBox1.setEnabled(false);
                    checkBox2.setEnabled(false);
                    checkBox3.setEnabled(false);
                    checkBox4.setEnabled(false);
                    checkBox5.setEnabled(false);
                    checkBox6.setEnabled(false);
                    checkBox7.setEnabled(false);
                    checkBox1.setEnabled(false);
                    checkBox8.setEnabled(false);
                    checkBox9.setEnabled(false);
                    checkBox10.setEnabled(false);
                    checkBox11.setEnabled(false);
                    checkBoxSK.setVisibility(View.VISIBLE);
                    checkBoxDO.setVisibility(View.VISIBLE);
                }
            }
        });
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(menuTab.this);
                alertdialogbuilder.setMessage("Are you sure you want to Save the Data?");
                alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @SuppressLint({"ShowToast", "NewApi"})
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*String data1,data2,data3,data4,data5,data6,data7,data8,data9,data10;
                        data1 = textViewTo.getText().toString();
                        data2 = textViewLicenseNo.getText().toString();
                        data3= textViewAddress1.getText().toString();
                        data4 = textViewPlateNumber.getText().toString();
                        data5 = textViewMake.getText().toString();
                        data6 = textViewColor.getText().toString();
                        data7 = textViewOwner.getText().toString();
                        data8 = textViewAddress2.getText().toString();
                        Random random = new Random();
                        int number = random.nextInt(1000000)+1;
                        Intent pass_intent = new Intent(menuTab.this,Save.class);
                        pass_intent.putExtra("Data0",String.valueOf(number));
                        pass_intent.putExtra("Data1",data1);
                        pass_intent.putExtra("Data2",data2);
                        pass_intent.putExtra("Data3",data3);
                        pass_intent.putExtra("Data4",data4);
                        pass_intent.putExtra("Data5",data5);
                        pass_intent.putExtra("Data6",data6);
                        pass_intent.putExtra("Data7",data7);
                        pass_intent.putExtra("Data8",data8);

                        if (checkBoxDO.isChecked()) {
                            checkBoxSK.setVisibility(View.VISIBLE);
                            checkBoUV.setVisibility(View.VISIBLE);
                            data9 = checkBoxDO.getText().toString();
                            pass_intent.putExtra("Data9",data9);
                        }

                        else if (checkBoxSK.isChecked()) {
                            checkBoxDO.setVisibility(View.VISIBLE);
                            checkBoUV.setVisibility(View.VISIBLE);
                            data9 = checkBoxSK.getText().toString();
                            pass_intent.putExtra("Data9",data9);
                        }

                        else if (checkBoUV.isChecked()) {
                            checkBoxSK.setVisibility(View.VISIBLE);
                            checkBoxDO.setVisibility(View.VISIBLE);
                            data9 = checkBoUV.getText().toString();
                            pass_intent.putExtra("Data9",data9);
                        }

                        if (checkBox1.isChecked()) {
                            data10 = checkBox1.getText().toString();
                            pass_intent.putExtra("Data10",data10);
                        }

                        else if (checkBox2.isChecked()) {
                            data10 = checkBox2.getText().toString();
                            pass_intent.putExtra("Data10",data10);
                        }
                        else if (checkBox3.isChecked()) {
                            data10 = checkBox3.getText().toString();
                            pass_intent.putExtra("Data10",data10);
                        }


                        else if (checkBox4.isChecked()) {
                            data10 = checkBox4.getText().toString();
                            pass_intent.putExtra("Data10",data10);
                        }


                        else if (checkBox5.isChecked()) {
                            data10 = checkBox5.getText().toString();
                            pass_intent.putExtra("Data10",data10);
                        }


                        else if (checkBox6.isChecked()) {
                            data10 = checkBox6.getText().toString();
                            pass_intent.putExtra("Data10",data10);
                        }


                        else if (checkBox7.isChecked()) {
                            data10 = checkBox7.getText().toString();
                            pass_intent.putExtra("Data10",data10);
                        }


                        else if (checkBox8.isChecked()) {
                            data10 = checkBox8.getText().toString();
                            pass_intent.putExtra("Data10",data10);
                        }


                        else if (checkBox9.isChecked()) {
                            data10 = checkBox9.getText().toString();
                            pass_intent.putExtra("Data10",data10);
                        }


                        else if (checkBox10.isChecked()) {
                            data10 = checkBox10.getText().toString();
                            pass_intent.putExtra("Data10",data10);
                        }


                        else if (checkBox11.isChecked()) {
                            data10 = checkBox11.getText().toString();
                            pass_intent.putExtra("Data10",data10);
                        }
                        startActivity(pass_intent);*/
                        Random random = new Random();
                        int number = random.nextInt(1000000) + 1;
                        Toast.makeText(menuTab.this, "Data is Saved!", Toast.LENGTH_SHORT);
                        SharedPreferences sharedPreferences0 = getSharedPreferences("Data0", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor0 = sharedPreferences0.edit();
                        editor0.putString("Value0", String.valueOf(number));
                        editor0.apply();

                        Toast.makeText(menuTab.this, "Data is Saved!", Toast.LENGTH_SHORT);
                        SharedPreferences sharedPreferences1 = getSharedPreferences("Data1", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor1.putString("Value1", textViewTo.getText().toString());
                        editor1.apply();

                        SharedPreferences sharedPreferences2 = getSharedPreferences("Data2", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                        editor2.putString("Value2", textViewLicenseNo.getText().toString());
                        editor2.apply();

                        SharedPreferences sharedPreferences3 = getSharedPreferences("Data3", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor3 = sharedPreferences3.edit();
                        editor3.putString("Value3", textViewAddress1.getText().toString());
                        editor3.apply();

                        SharedPreferences sharedPreferences4 = getSharedPreferences("Data4", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor4 = sharedPreferences4.edit();
                        editor4.putString("Value4", textViewPlateNumber.getText().toString());
                        editor4.apply();

                        SharedPreferences sharedPreferences5 = getSharedPreferences("Data5", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor5 = sharedPreferences5.edit();
                        editor5.putString("Value5", textViewMake.getText().toString());
                        editor5.apply();

                        SharedPreferences sharedPreferences6 = getSharedPreferences("Data6", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor6 = sharedPreferences6.edit();
                        editor6.putString("Value6", textViewColor.getText().toString());
                        editor6.apply();

                        SharedPreferences sharedPreferences7 = getSharedPreferences("Data7", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor7 = sharedPreferences7.edit();
                        editor7.putString("Value7", textViewOwner.getText().toString());
                        editor7.apply();

                        SharedPreferences sharedPreferences8 = getSharedPreferences("Data8", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor8 = sharedPreferences8.edit();
                        editor8.putString("Value8", textViewAddress2.getText().toString());
                        editor8.apply();

                        SharedPreferences sharedPreferences11 = getSharedPreferences("Data11", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor11 = sharedPreferences11.edit();
                        editor11.putString("Value11", textViewLocation.getText().toString());
                        editor1.apply();

                        if (checkBoxDO.isChecked()) {
                            checkBoxSK.setVisibility(View.VISIBLE);
                            checkBoUV.setVisibility(View.VISIBLE);
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data9", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value9", checkBoxDO.getText().toString());
                            editor9.apply();
                        } else if (checkBoxSK.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data9", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value9", checkBoxSK.getText().toString());
                            editor9.apply();
                        } else if (checkBoUV.isChecked()) {
                            checkBoxDO.setVisibility(View.VISIBLE);
                            checkBoUV.setVisibility(View.VISIBLE);
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data9", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value9", checkBoUV.getText().toString());
                            editor9.apply();

                        }

                        if (checkBox1.isChecked()) {
                            SharedPreferences sharedPreferences10 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor10 = sharedPreferences10.edit();
                            editor10.putString("Value10", checkBox1.getText().toString());
                            editor10.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1));
                            editor12.apply();
                        }

                        if (checkBox2.isChecked()) {
                            SharedPreferences sharedPreferences10 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor10 = sharedPreferences10.edit();
                            editor10.putString("Value10", checkBox2.getText().toString());
                            editor10.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2));
                            editor12.apply();
                        }


                        if (checkBox3.isChecked()) {
                            SharedPreferences sharedPreferences10 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor10 = sharedPreferences10.edit();
                            editor10.putString("Value10", checkBox3.getText().toString());
                            editor10.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3));
                            editor12.apply();
                        }


                        if (checkBox4.isChecked()) {
                            SharedPreferences sharedPreferences10 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor10 = sharedPreferences10.edit();
                            editor10.putString("Value10", checkBox4.getText().toString());
                            editor10.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4));
                            editor12.apply();
                        }



                        if (checkBox5.isChecked()) {
                            SharedPreferences sharedPreferences10 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor10 = sharedPreferences10.edit();
                            editor10.putString("Value10", checkBox5.getText().toString());
                            editor10.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5));
                            editor12.apply();
                        }


                        if (checkBox6.isChecked()) {
                            SharedPreferences sharedPreferences10 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor10 = sharedPreferences10.edit();
                            editor10.putString("Value10", checkBox6.getText().toString());
                            editor10.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p6));
                            editor12.apply();
                        }

                        if (checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences10 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor10 = sharedPreferences10.edit();
                            editor10.putString("Value10", checkBox7.getText().toString());
                            editor10.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p7));
                            editor12.apply();
                        }


                        if (checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences10 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor10 = sharedPreferences10.edit();
                            editor10.putString("Value10", checkBox8.getText().toString());
                            editor10.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p8));
                            editor12.apply();
                        }


                        if (checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences10 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor10 = sharedPreferences10.edit();
                            editor10.putString("Value10", checkBox9.getText().toString());
                            editor10.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p9));
                            editor12.apply();
                        }


                        if (checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences10 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor10 = sharedPreferences10.edit();
                            editor10.putString("Value10", checkBox10.getText().toString());
                            editor10.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p10));
                            editor12.apply();
                        }


                        if (checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p11));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox3.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox3.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p3));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox4.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox4.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p4));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox5.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox5.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p5));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox6.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox6.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p6));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p7));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p8));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p9));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p11));
                            editor12.apply();
                        }

                        if (checkBox1.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p10));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox4.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox4.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p4));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox5.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox5.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p5));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox6.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox6.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p6));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p7));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p8));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p9));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p10));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p11));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox4.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox4.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p4));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox5.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox5.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p5));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox6.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox6.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p6));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p7));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p8));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p9));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p10));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p11));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox5.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox5.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p5));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox6.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox6.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p6));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p7));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p8));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p9));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p10));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p11));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox6.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox6.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p6));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p7));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p8));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p9));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p10));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p11));
                            editor12.apply();
                        }
                        if (checkBox6.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox6.getText().toString() + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p6+p7));
                            editor12.apply();
                        }
                        if (checkBox6.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox6.getText().toString() + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p6+p8));
                            editor12.apply();
                        }
                        if (checkBox6.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox6.getText().toString() + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p6+p9));
                            editor12.apply();
                        }
                        if (checkBox6.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox6.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p6+p10));
                            editor12.apply();
                        }
                        if (checkBox6.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox6.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p6+p11));
                            editor12.apply();
                        }
                        if (checkBox7.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox7.getText().toString() + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p7+p8));
                            editor12.apply();
                        }
                        if (checkBox7.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox7.getText().toString() + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p7+p9));
                            editor12.apply();
                        }
                        if (checkBox7.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox7.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p7+p10));
                            editor12.apply();
                        }
                        if (checkBox7.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox7.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p7+p11));
                            editor12.apply();
                        }
                        if (checkBox8.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox8.getText().toString() + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p8+p9));
                            editor12.apply();
                        }
                        if (checkBox8.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox8.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p8+p10));
                            editor12.apply();
                        }
                        if (checkBox8.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox8.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p8+p11));
                            editor12.apply();
                        }
                        if (checkBox9.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox9.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p9+p10));
                            editor12.apply();
                        }
                        if (checkBox9.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox9.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p9+p11));
                            editor12.apply();
                        }
                        if (checkBox10.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox10.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p10+p11));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox3.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p3));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox4.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox4.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p4));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox5.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox5.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p5));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox6.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox6.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p6));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p7));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p8));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p9));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p10));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p11));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox4.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p4));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox5.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox5.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p5));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox6.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox6.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p6));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p7));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p8));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p9));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p10));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p11));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox4.isChecked() && checkBox5.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox4.getText().toString()
                                    + "\n" + checkBox5.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p4+p5));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox4.isChecked() && checkBox6.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox4.getText().toString()
                                    + "\n" + checkBox6.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p4+p6));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox4.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox4.getText().toString()
                                    + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p4+p7));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox4.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox4.getText().toString()
                                    + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p4+p8));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox4.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox4.getText().toString()
                                    + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p4+p9));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox4.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox4.getText().toString()
                                    + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p4+p10));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox4.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox4.getText().toString()
                                    + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p4+p11));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox5.isChecked() && checkBox6.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox5.getText().toString()
                                    + "\n" + checkBox6.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p5+p6));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox5.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox5.getText().toString()
                                    + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p5+p7));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox5.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox5.getText().toString()
                                    + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p5+p8));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox5.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox5.getText().toString()
                                    + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p5+p9));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox5.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox5.getText().toString()
                                    + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p5+p10));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox5.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox5.getText().toString()
                                    + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p5+p11));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox6.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox6.getText().toString()
                                    + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p6+p7));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox6.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox6.getText().toString()
                                    + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p6+p8));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox6.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox6.getText().toString()
                                    + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p6+p9));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox6.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox6.getText().toString()
                                    + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p6+p10));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox6.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox6.getText().toString()
                                    + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p6+p11));
                            editor12.apply();
                        }
                        if (checkBox6.isChecked() && checkBox7.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox6.getText().toString() + "\n" + checkBox7.getText().toString()
                                    + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p6+p7+p8));
                            editor12.apply();
                        }
                        if (checkBox6.isChecked() && checkBox7.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox6.getText().toString() + "\n" + checkBox7.getText().toString()
                                    + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p6+p7+p9));
                            editor12.apply();
                        }
                        if (checkBox6.isChecked() && checkBox7.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox6.getText().toString() + "\n" + checkBox7.getText().toString()
                                    + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p6+p7+p10));
                            editor12.apply();
                        }
                        if (checkBox6.isChecked() && checkBox7.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox6.getText().toString() + "\n" + checkBox7.getText().toString()
                                    + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p6+p7+p11));
                            editor12.apply();
                        }
                        if (checkBox7.isChecked() && checkBox8.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox7.getText().toString() + "\n" + checkBox8.getText().toString()
                                    + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p7+p8+p9));
                            editor12.apply();
                        }
                        if (checkBox7.isChecked() && checkBox8.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox7.getText().toString() + "\n" + checkBox8.getText().toString()
                                    + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p7+p8+p10));
                            editor12.apply();
                        }
                        if (checkBox7.isChecked() && checkBox8.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox7.getText().toString() + "\n" + checkBox8.getText().toString()
                                    + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p7+p8+p11));
                            editor12.apply();
                        }
                        if (checkBox8.isChecked() && checkBox9.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox8.getText().toString() + "\n" + checkBox9.getText().toString()
                                    + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p8+p9+p10));
                            editor12.apply();
                        }
                        if (checkBox8.isChecked() && checkBox9.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox8.getText().toString() + "\n" + checkBox9.getText().toString()
                                    + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p8+p9+p11));
                            editor12.apply();
                        }
                        if (checkBox9.isChecked() && checkBox10.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox9.getText().toString() + "\n" + checkBox10.getText().toString()
                                    + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p9+p10+p11));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox3.getText().toString() + "\n" + checkBox4.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p3+p4));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() && checkBox5.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox3.getText().toString() + "\n" + checkBox5.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p3+p5));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() && checkBox6.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox3.getText().toString() + "\n" + checkBox6.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p3+p6));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox3.getText().toString() + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p3+p7));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox3.getText().toString() + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p3+p8));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox3.getText().toString() + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p3+p9));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox3.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p3+p10));
                            editor12.apply();
                        }
                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox1.getText().toString() + "\n" + checkBox2.getText().toString()
                                    + "\n" + checkBox3.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p1+p2+p3+p11));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked() && checkBox5.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox4.getText().toString() + "\n" + checkBox5.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p4+p5));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked() && checkBox6.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox4.getText().toString() + "\n" + checkBox6.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p4+p6));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox4.getText().toString() + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p4+p7));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox4.getText().toString() + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p4+p8));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox4.getText().toString() + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p4+p9));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox4.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p4+p10));
                            editor12.apply();
                        }
                        if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox2.getText().toString() + "\n" + checkBox3.getText().toString()
                                    + "\n" + checkBox4.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p2+p3+p4+p11));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox4.isChecked() && checkBox5.isChecked() && checkBox6.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox4.getText().toString()
                                    + "\n" + checkBox5.getText().toString() + "\n" + checkBox6.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p4+p5+p6));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox4.isChecked() && checkBox5.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox4.getText().toString()
                                    + "\n" + checkBox5.getText().toString() + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p4+p5+p7));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox4.isChecked() && checkBox5.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox4.getText().toString()
                                    + "\n" + checkBox5.getText().toString() + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p4+p5+p8));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox4.isChecked() && checkBox5.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox4.getText().toString()
                                    + "\n" + checkBox5.getText().toString() + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p4+p5+p9));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox4.isChecked() && checkBox5.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox4.getText().toString()
                                    + "\n" + checkBox5.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p4+p5+p10));
                            editor12.apply();
                        }
                        if (checkBox3.isChecked() && checkBox4.isChecked() && checkBox5.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox3.getText().toString() + "\n" + checkBox4.getText().toString()
                                    + "\n" + checkBox5.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p3+p4+p5+p11));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox5.isChecked() && checkBox6.isChecked() && checkBox7.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox5.getText().toString()
                                    + "\n" + checkBox6.getText().toString() + "\n" + checkBox7.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p5+p6+p7));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox5.isChecked() && checkBox6.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox5.getText().toString()
                                    + "\n" + checkBox6.getText().toString() + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p5+p6+p8));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox5.isChecked() && checkBox6.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox5.getText().toString()
                                    + "\n" + checkBox6.getText().toString() + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p5+p6+p9));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox5.isChecked() && checkBox6.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox5.getText().toString()
                                    + "\n" + checkBox6.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p5+p6+p10));
                            editor12.apply();
                        }
                        if (checkBox4.isChecked() && checkBox5.isChecked() && checkBox6.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox4.getText().toString() + "\n" + checkBox5.getText().toString()
                                    + "\n" + checkBox6.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p4+p5+p6+p11));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox6.isChecked() && checkBox7.isChecked() && checkBox8.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox6.getText().toString()
                                    + "\n" + checkBox7.getText().toString() + "\n" + checkBox8.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p6+p7+p8));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox6.isChecked() && checkBox7.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox6.getText().toString()
                                    + "\n" + checkBox7.getText().toString() + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p6+p7+p9));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox6.isChecked() && checkBox7.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox6.getText().toString()
                                    + "\n" + checkBox7.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p6+p7+p10));
                            editor12.apply();
                        }
                        if (checkBox5.isChecked() && checkBox6.isChecked() && checkBox7.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox5.getText().toString() + "\n" + checkBox6.getText().toString()
                                    + "\n" + checkBox7.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p5+p6+p7+p11));
                            editor12.apply();
                        }
                        if (checkBox6.isChecked() && checkBox7.isChecked() && checkBox8.isChecked() && checkBox9.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox6.getText().toString() + "\n" + checkBox7.getText().toString()
                                    + "\n" + checkBox8.getText().toString() + "\n" + checkBox9.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p6+p7+p8+p9));
                            editor12.apply();
                        }
                        if (checkBox6.isChecked() && checkBox7.isChecked() && checkBox8.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox6.getText().toString() + "\n" + checkBox7.getText().toString()
                                    + "\n" + checkBox8.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p6+p7+p8+p10));
                            editor12.apply();
                        }
                        if (checkBox6.isChecked() && checkBox7.isChecked() && checkBox8.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox6.getText().toString() + "\n" + checkBox7.getText().toString()
                                    + "\n" + checkBox8.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p6+p7+p8+p11));
                            editor12.apply();
                        }
                        if (checkBox7.isChecked() && checkBox8.isChecked() && checkBox9.isChecked() && checkBox10.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox7.getText().toString() + "\n" + checkBox8.getText().toString()
                                    + "\n" + checkBox9.getText().toString() + "\n" + checkBox10.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p7+p8+p9+p10));
                            editor12.apply();
                        }
                        if (checkBox7.isChecked() && checkBox8.isChecked() && checkBox9.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox7.getText().toString() + "\n" + checkBox8.getText().toString()
                                    + "\n" + checkBox9.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p7+p8+p9+p11));
                            editor12.apply();
                        }
                        if (checkBox8.isChecked() && checkBox9.isChecked() && checkBox10.isChecked() && checkBox11.isChecked()) {
                            SharedPreferences sharedPreferences9 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                            editor9.putString("Value10", checkBox8.getText().toString() + "\n" + checkBox9.getText().toString()
                                    + "\n" + checkBox10.getText().toString() + "\n" + checkBox11.getText().toString());
                            editor9.apply();
                            SharedPreferences sharedPreferences12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                            editor12.putString("Value12", String.valueOf(p8+p9+p10+p11));
                            editor12.apply();
                        }

                        //Intent intent = new Intent(menuTab.this, Save.class);
                        //startActivity(intent);
                    }
                });
                alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertdialogbuilder.create();
                alertDialog.show();
                Toast.makeText(menuTab.this, "Data is Saved! ", Toast.LENGTH_SHORT).show();

            }
        });
       /* violationTags();
        editTextSearch = (EditText)findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    violationTags();
                }
                else{
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        //Location
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        buttonLocation = (Button) findViewById(R.id.buttonLocation);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);

       buttonLocation.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
               if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                   buildAlertMessageNoGps();
               }else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                   getLocation();
               }
           }
       });



        /*

        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
                } else {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    try {
                        String city = hereLocation(location.getLatitude(), location.getLongitude());
                        textViewLicenseNo.setText(city);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(menuTab.this, "Not Found! ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });*/

    }

    private void getLocation(){
        if(ActivityCompat.checkSelfPermission(menuTab.this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (menuTab.this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(menuTab.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }else{
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location != null){
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                latitude = String.valueOf(latti);
                longtitude = String.valueOf(longi);
                geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latti,longi,1);
                    String address = addresses.get(0).getAddressLine(0);
                    textViewLocation.setText(address);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(menuTab.this, "Unable to Trace your Location! ", Toast.LENGTH_SHORT).show();
            }
        }
    }
    protected void buildAlertMessageNoGps(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog,final int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog,final int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    try {
                        String city = hereLocation(location.getLatitude(), location.getLongitude());
                        textViewLicenseNo.setText(city);
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(menuTab.this, "Not Found! ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Permission not granted! ", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private String hereLocation(double lat, double lon){
        String cityName = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try{
            addresses = geocoder.getFromLocation(lat,lon,10);
            if(addresses.size()>0){
               for(Address adr: addresses){
                   if(adr.getLocality()!=null && adr.getLocality().length()>0){
                       cityName = adr.getLocality();
                       break;
                   }
               }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return cityName;
    }*/

    public void scanBarcode(View view){
        Intent intent= new Intent(this, ScanBarcode.class);
        startActivityForResult(intent,0);
    }


    public void searchItem(String textToSearch){
        for (String item:items){
            if(item.contains(textToSearch)){
                list.remove(item);
            }
        }
    }

    private void violationTags(){
        item1 = checkBox1.getText().toString();
        item2 = checkBox2.getText().toString();
        item3 = checkBox3.getText().toString();
        item4 = checkBox4.getText().toString();
        item5 = checkBox5.getText().toString();
        item6 = checkBox6.getText().toString();
        item7 = checkBox7.getText().toString();
        item8 = checkBox8.getText().toString();
        item9 = checkBox9.getText().toString();
        item10 = checkBox10.getText().toString();
        item11 = checkBox11.getText().toString();
        items = new String[]{item1,item2,item3,item4,item5,item6,item7,item8,item9,item10,item11};
        list = new ArrayList<>(Arrays.asList(items));

    }

    public void SESSION(){
        session = Boolean.valueOf(Login.read(getApplicationContext(),"session","false"));
        if(!session){

        }else{

        }


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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_scan, menu);
    }
   public void CheckUser(){
        /*Boolean Check = Boolean.valueOf(UtilsClipCode.readSharedSetting(menuTab.this,"ClipCodes","true"));
        Intent introIntent = new Intent(menuTab.this,MainActivity.class);
        introIntent.putExtra("ClipCodes",Check);
        if(Check){
            startActivity(introIntent);
        }*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }/* else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        /*if(item.getItemId() == R.id.ActionScan){
                scannow();
        }*/

        return super.onOptionsItemSelected(item);
    }
    private void displaySelectedScreen(int id){
        Fragment fragment = null;
        switch (id)
        {
            case R.id.Ticket:
                menuTab.this.setTitle("Ticket");
                //Intent l = new Intent(menuTab.this, ScrollTicket.class);
                //startActivity(l);

                //fragment=new ScanFragment();
                break;
            case R.id.MyProfile:
                Intent m = new Intent(getApplicationContext(), Violators.class);
                startActivity(m);
                break;
            case R.id.LogOut:
               // UtilsClipCode.saveSharedSetting(menuTab.this,"ClipCodes","true");
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
                alertdialogbuilder.setMessage("Are you sure you want Logout?");
                alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Login.save(getApplicationContext(),"session","false");
                        Intent l = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(l);
                        finish();
                    }
                });
                alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = alertdialogbuilder.create();
                alertDialog.show();
                break;
        }
        if(fragment!=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.Ticket,fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        displaySelectedScreen(id);
        return true;
    }
        @SuppressLint("ShowToast")
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==0){
            if(resultCode== CommonStatusCodes.SUCCESS){
                if(data!=null){
                    Barcode barcode = data.getParcelableExtra("barcode");
                    editTextLicenseNo.setText(barcode.displayValue);
                }else {

                }
            }
        }else {

        }
            super.onActivityResult(requestCode, resultCode, data);
            /*super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data !=null && data.getData()!=null){
                imageUri = data.getData();
                StorageReference reference = storageReference.child("Image"+".jpg");
                reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        final String downloadUri = taskSnapshot.getUploadSessionUri().toString();
                        Map<String, String> map = new HashMap<>();
                        map.put("image",downloadUri);
                        mDatabase.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Picasso.with(menuTab.this).load(downloadUri).into(imageViewPic);
                                }

                            }
                        });
                    }
                });

            }*/
            //final IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            //editTextLicenseNo.setText(result.getContents());
            /*else if(requestCode != RESULT_LOAD_IMAGE && resultCode != RESULT_OK && data ==null && data.getData()==null)
            {
                final IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
                editTextLicenseNo.setText(result.getContents());
            }*/
           /* if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data !=null && data.getData()!=null){
                selectedImage = data.getData();
                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                    imageViewPic.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
                imageViewPic.setImageURI(selectedImage);
            }
            else if(requestCode != RESULT_LOAD_IMAGE && resultCode != RESULT_OK && data ==null && data.getData()==null){
                Intent s = new Intent(getApplicationContext(), menuTab.class);
                startActivity(s);
            }
            else{
                final IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            editTextLicenseNo.setText(result.getContents());}*/



                  /* buttonOk.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           final String pn =  editTextLicenseNo.getText().toString();
                           reff = FirebaseDatabase.getInstance().getReference().child("License");
                           reff.child(pn).addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                   if(dataSnapshot.exists()){
                                       License license = dataSnapshot.getValue(License.class);
                                       String ln = license.getLicenseNo().toString();
                                       String m = license.getTo().toString();
                                       String c = license.getAddress().toString();
                                       textViewTo.setText(m);
                                       editTextLicenseNo.setText(ln);
                                       textViewAddress1.setText(c);

                                   } else {
                                       AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(menuTab.this);
                                       alertdialogbuilder.setMessage(result.getContents() + " is not registered!\n" + "Scan Again ?");
                                       alertdialogbuilder.setTitle("Result Scanned");
                                       alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               scannow();
                                           }
                                       });
                                       alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {

                                           }
                                       });
                                       AlertDialog alertDialog = alertdialogbuilder.create();
                                       alertDialog.show();
                                       Toast.makeText(menuTab.this, "License Number is not Registered! ", Toast.LENGTH_SHORT).show();
                                   }


                               /*  String editCode = dataSnapshot.child("LicenseNo").getValue().toString();
                            if(result.getContents().equals(editCode)) {
                                Toast.makeText(menuTab.this,"License Number Found! ",Toast.LENGTH_SHORT).show();

                                String pl = dataSnapshot.child("Barcode").getValue().toString();
                                String m = dataSnapshot.child("To").getValue().toString();
                                String c = dataSnapshot.child("Address").getValue().toString();
                                textViewTo.setText(m);
                                textViewLicenseNo.setText(pl);
                                textViewAddress1.setText(c);

                            }
                            else
                            {
                                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(menuTab.this);
                                alertdialogbuilder.setMessage(result.getContents()+" is not registered!\n"+"Scan Again ?");
                                alertdialogbuilder.setTitle("Result Scanned");
                                alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        scannow();
                                    }
                                });
                                alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                AlertDialog alertDialog = alertdialogbuilder.create();
                                alertDialog.show();
                                Toast.makeText(menuTab.this,"License Number is not Registered! ",Toast.LENGTH_SHORT).show();
                            }
                               }
                               @Override
                               public void onCancelled(@NonNull DatabaseError databaseError) {

                               }
                           });
                       }
                   });*/

        }

   public void scannow(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(Scan.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("SCAN");
        integrator.initiateScan();
    }
}
