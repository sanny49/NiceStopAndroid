package com.example.nicestop;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public class Save extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;

    OutputStream outputStream;
    InputStream inputStream;
    Thread thread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile  boolean stopWorking;
    TextView lblPrintername,textViewRP,textViewCC,textViewTVB,textViewTCT,textViewT,textViewLN,textViewAdd1,textViewPN,textViewM,
            textViewO,textViewAdd2,textViewC,textViewV,textViewDate,textViewTime,textViewCheck,textViewAuto,textViewEnforcer,textViewL,
            textViewPrice;
    String v0;
    Button buttonConnect,buttonDisconnect,buttonPrint,buttonSend;
    ImageView button_back;
    FirebaseDatabase database;
    DatabaseReference ref;
    Violator violator;
    String value0,value1,value2,value3,value4,value5,value6,value7,value8,value9,value10,value11,value12,currentDate,time,name;
    String data0,data1,data2,data3,data4,data5,data6,data7,data8,data9,data10;
    long maxId =0;
    long counter =0;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        Calendar calendar = Calendar.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbarSave);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Saved");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        textViewDate = findViewById(R.id.textViewDate);
        textViewDate.setText("Date: "+currentDate);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        time = format.format(calendar.getTime());
        textViewTime = findViewById(R.id.textViewTime);
        textViewTime.setText("Time: "+time);
        buttonConnect = (Button) findViewById(R.id.buttonConnect);
        buttonDisconnect = (Button) findViewById(R.id.buttonDisconnect);
        buttonPrint = (Button) findViewById(R.id.buttonPrint);
        lblPrintername= (TextView) findViewById(R.id.lblPrintername);
        textViewRP= (TextView) findViewById(R.id.textViewRP);
        textViewCC= (TextView) findViewById(R.id.textViewCC);
        textViewTVB= (TextView) findViewById(R.id.textViewTVB);
        textViewTCT= (TextView) findViewById(R.id.textViewTCT);
        textViewT= (TextView) findViewById(R.id.textViewT);
        textViewLN= (TextView) findViewById(R.id.textViewLN);
        textViewAdd1 = (TextView) findViewById(R.id.textViewAdd1);
        textViewPN= (TextView) findViewById(R.id.textViewPN);
        textViewM= (TextView) findViewById(R.id.textViewM);
        textViewO= (TextView) findViewById(R.id.textViewO);
        textViewAdd2= (TextView) findViewById(R.id.textViewAdd2);
        textViewC= (TextView) findViewById(R.id.textViewC);
        textViewV= (TextView) findViewById(R.id.textViewV);
        textViewCheck =(TextView) findViewById(R.id.textViewCheck);
        textViewEnforcer =(TextView) findViewById(R.id.textViewEnforcer);
        textViewL =(TextView) findViewById(R.id.textViewL);
        textViewPrice =(TextView) findViewById(R.id.textViewPrice);
        String date = new SimpleDateFormat("MMddyyyy", Locale.getDefault()).format(new Date());
        textViewAuto =(TextView) findViewById(R.id.textViewAuto);
        //textViewAuto.setText("TVB No: "+date);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Violator");
        violator = new Violator();
       // openFragment();
        /*Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            data0 = bundle.getString("Data0");
            data1 = bundle.getString("Data1");
            data2 = bundle.getString("Data2");
            data3 = bundle.getString("Data3");
            data4 = bundle.getString("Data4");
            data5 = bundle.getString("Data5");
            data6 = bundle.getString("Data6");
            data7 = bundle.getString("Data7");
            data8 = bundle.getString("Data8");
            data9 = bundle.getString("Data9");
            data10 = bundle.getString("Data10");
            v0 = date + data0;
            textViewAuto.setText("TVB No: " + v0);
            textViewT.setText("To:  " + data1);
            textViewLN.setText("License No.:  " + data2);
            textViewAdd1.setText("Address:  " + data3);
            textViewPN.setText("Plate No.:  " + data4);
            textViewM.setText("Make:  " + data5);
            textViewC.setText("Color:  " + data6);
            textViewO.setText("Owner:  " + data7);
            textViewAdd2.setText("Address:  " + data8);
            textViewCheck.setText(data9);
            textViewV.setText("Violation:  " + data10);

        }*/
        SharedPreferences result1 = getSharedPreferences("Name", Context.MODE_PRIVATE);
        name = result1.getString("Value","");
        //textViewEnforcer.setText("Enforcer: "+name);
        textViewEnforcer.setText("Enforcer: "+"Sanny Marinduque");
        SharedPreferences result0 = getSharedPreferences("Data0", Context.MODE_PRIVATE);
        value0 = result0.getString("Value0","");
        v0 = date+value0;
        textViewAuto.setText("TVB No: "+v0);
        SharedPreferences resul1 = getSharedPreferences("Data1", Context.MODE_PRIVATE);
        value1 = resul1.getString("Value1","");
        //textViewT.setText("To:  "+value1);
        textViewT.setText("To:  "+"Sanny Marinduque");
        SharedPreferences result2 = getSharedPreferences("Data2", Context.MODE_PRIVATE);
        value2 = result2.getString("Value2","");
        //textViewLN.setText("License No.:  "+value2);
        textViewLN.setText("License No.:  "+"2002032200030696");
        SharedPreferences result3 = getSharedPreferences("Data3", Context.MODE_PRIVATE);
        value3 = result3.getString("Value3","");
        //textViewAdd1.setText("Address:  "+value3);
        textViewAdd1.setText("Address:  "+"Parian");
        SharedPreferences result4 = getSharedPreferences("Data4", Context.MODE_PRIVATE);
        value4 = result4.getString("Value4","");
        //textViewPN.setText("Plate No.:  "+value4);
        textViewPN.setText("Plate No.:  "+"GTI100");
        SharedPreferences result5 = getSharedPreferences("Data5", Context.MODE_PRIVATE);
        value5 = result5.getString("Value5","");
        //textViewM.setText("Make:  "+value5);
        textViewM.setText("Make:  "+"Toyota");
        SharedPreferences result6 = getSharedPreferences("Data6", Context.MODE_PRIVATE);
        value6 = result6.getString("Value6","");
        //textViewC.setText("Color:  "+value6);
        textViewC.setText("Color:  "+"Blue");
        SharedPreferences result7 = getSharedPreferences("Data7", Context.MODE_PRIVATE);
        value7 = result7.getString("Value7","");
        //textViewO.setText("Owner:  "+value7);
        textViewO.setText("Owner:  "+"Sanny Marinduque");
        SharedPreferences result8 = getSharedPreferences("Data8", Context.MODE_PRIVATE);
        value8 = result8.getString("Value8","");
        //textViewAdd2.setText("Address:  "+value8);
        textViewAdd2.setText("Address:  "+"Parian, Cebu City");
        SharedPreferences result9 = getSharedPreferences("Data9", Context.MODE_PRIVATE);
        value9 = result9.getString("Value9"," ");
        //textViewCheck.setText(value9);
        textViewCheck.setText("Data only");
        SharedPreferences result10 = getSharedPreferences("Data10", Context.MODE_PRIVATE);
        value10 = result10.getString("Value10","");
        //textViewV.setText("Violation:  "+value10);
        textViewV.setText("Violation:  "+"Art VI Sec.1  -DTS NO ENTRY"+"\nArt VI Sec.4(1) -DTS Go Signal");
        SharedPreferences result11 = getSharedPreferences("Data11", Context.MODE_PRIVATE);
        value11 = result11.getString("Value11","");
        textViewL.setText("Location:  "+value11);
        SharedPreferences result12 = getSharedPreferences("Data12", Context.MODE_PRIVATE);
        value12 = result12.getString("Value12","");
        //textViewPrice.setText("Price:  "+value12);
        textViewPrice.setText("Price:  "+"1010");
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    FindBluetoothDevice();
                    openBluetoothPrinter();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        buttonDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    disconnectBT();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        buttonPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    printData();

                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        //onClickImageButtonListener();

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

    private void  getValues(){
       /* violator.setEnforcer(name);
        violator.setTVBNo(v0);
        violator.setType(value9);
        violator.setDate(currentDate);
        violator.setTime(time);
        violator.setTo(value1);
        violator.setLicenseNo(value2);
        violator.setAddress1(value3);
        violator.setPlateNo(value4);
        violator.setMake(value5);
        violator.setColor(value6);
        violator.setAddress2(value7);
        violator.setOwner(value8);
        violator.setViolation(value10);
        violator.setViolation(value11);
        violator.setPrice(value12);*/
        violator.setEnforcer("Sanny Marinduque");
        violator.setTVBNo(v0);
        violator.setType("Data Only");
        violator.setDate(currentDate);
        violator.setTime(time);
        violator.setTo("Sanny Marinduque");
        violator.setLicenseNo("2002032200030696");
        violator.setAddress1("Parian");
        violator.setPlateNo("GTI100");
        violator.setMake("Toyota");
        violator.setColor("Blue");
        violator.setAddress2("Parian, Cebu City");
        violator.setOwner("Sanny Marinduque");
        violator.setViolation("Art VI Sec.1  -DTS NO ENTRY"+"\nArt VI Sec.4(1) -DTS Go Signal");
        violator.setLocation(value11);
        violator.setPrice("1010");
    }

    public void btnSend(View view){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
               /* AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(Save.this);
                alertdialogbuilder.setMessage("Send Data ?");
                alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {*/
                        if(dataSnapshot.exists()){
                            maxId=(dataSnapshot.getChildrenCount());
                            getValues();
                            ref.child(String.valueOf(maxId+1)).setValue(violator);
                        }


                        /*SharedPreferences sharedPreferences1 = getSharedPreferences("DataTo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor1.putString("ValueTo", textViewT.getText().toString());
                        editor1.apply();*/

                        Toast.makeText(Save.this, "Data Sent...",Toast.LENGTH_SHORT).show();
                       // Intent l = new Intent(Save.this, Save.class);
                       // startActivity(l);
                        finish();

                   /* }
                });
                alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertdialogbuilder.create();
                alertDialog.show();*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*public void onClickImageButtonListener(){
        button_back = (ImageView)findViewById(R.id.imageViewBack);
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
    @SuppressLint("SetTextI18n")
    void FindBluetoothDevice(){
        try{
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if(bluetoothAdapter==null){
                lblPrintername.setText("No Bluetooth Adapter Found!");
                Toast.makeText(Save.this, "No Bluetooth Adapter Found!", Toast.LENGTH_SHORT).show();
            }
            if(bluetoothAdapter.isEnabled()){
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBT,0);

            }
            Set<BluetoothDevice> pairedDevice =bluetoothAdapter.getBondedDevices();
            if(pairedDevice.size()>0){
                for(BluetoothDevice pairedDev:pairedDevice){
                    if(pairedDev.getName().equals("PT200")){
                        bluetoothDevice = pairedDev;
                        buttonConnect.setTextColor(Color.BLUE);
                        lblPrintername.setText("Bluetooth Printer Attached:"+pairedDev.getName());
                        Toast.makeText(Save.this, "Bluetooth Printer Attached:"+pairedDev.getName(), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

           // lblPrintername.setText("Bluetooth Printer Attached");
        }catch (Exception   ex){
            ex.printStackTrace();
        }
    }
    void openBluetoothPrinter() throws IOException {
        try{
            UUID uuidString = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            bluetoothSocket=bluetoothDevice.createRfcommSocketToServiceRecord(uuidString);
            bluetoothSocket.connect();
            outputStream=bluetoothSocket.getOutputStream();
            inputStream=bluetoothSocket.getInputStream();
            beginListenData();
        }catch (Exception ex){

        }
    }
    void beginListenData(){
        try {
           final Handler handler = new Handler();
           final byte delimater=10;
           stopWorking=false;
           readBufferPosition=0;
           readBuffer=new byte[1024];
           thread=new Thread(new Runnable() {
               @Override
               public void run() {
                   while(!Thread.currentThread().isInterrupted()&&!stopWorking){
                       try {
                           int byteAvialable =inputStream.available();
                           if(byteAvialable>0){
                               byte[] packetByte = new byte[byteAvialable];
                               inputStream.read(packetByte);

                               for(int i=0; i<byteAvialable; i++){
                                   byte b = packetByte[i];
                                   if(b==delimater){
                                       byte[] encodedByte = new byte[readBufferPosition];
                                       System.arraycopy(
                                               readBuffer,0,
                                               encodedByte,0,
                                               encodedByte.length
                                       );
                                       final String data = new String(encodedByte,"US-ASCII");
                                       readBufferPosition=0;
                                       handler.post(new Runnable() {
                                           @Override
                                           public void run() {
                                              lblPrintername.setText(data);
                                           }
                                       });
                                   }else{
                                       readBuffer[readBufferPosition++]=b;
                                   }
                               }
                           }
                       }catch (Exception ex){
                           stopWorking=true;
                       }
                   }
               }
           });
            thread.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
        //Printing Text to Bluetooth Printer
    void printData() throws IOException{
        /*AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("Print the Ticket ?");
        alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {*/
                try{

                    //String msg = textBox.getText().toString();
                    //msg+="\n";
                    //outputStream.write(msg.getBytes());
                    //lblPrintername.setText("Printing Ticket...");
                    String O9 = textViewRP.getText().toString();
                    O9+="\n";;
                    String O4 = textViewCC.getText().toString();
                    O4+="\n";
                    String O12 = textViewTVB.getText().toString();
                    O12+="\n";
                    String O11 = textViewTCT.getText().toString();
                    O11+="\n";
                    String O0 = textViewAuto.getText().toString();
                    O0+="\n";
                    String O16 = textViewCheck.getText().toString();
                    O16+="\n";
                    String O14 = textViewDate.getText().toString();
                    O14+="\n";
                    String O15 = textViewTime.getText().toString();
                    O15+="\n";
                    String O10 = textViewT.getText().toString();
                    O10+="\n";
                    String O5 = textViewLN.getText().toString();
                    O5+="\n";

                    String O1= textViewAdd1.getText().toString();
                    O1+="\n";
                    String O8 = textViewPN.getText().toString();
                    O8+="\n";
                    String O6 = textViewM.getText().toString();
                    O6+="\n";
                    String O7 = textViewO.getText().toString();
                    O7+="\n";
                    String O3 = textViewC.getText().toString();
                    O3+="\n";
                    String O2 = textViewAdd2.getText().toString();
                    O2+="\n";
                    String O13 = textViewV.getText().toString();
                    O13+="\n";
                    String O19 = textViewPrice.getText().toString();
                    O19+="\n";
                    String O18 = textViewL.getText().toString();
                    O18+="\n";
                    String O17 = textViewEnforcer.getText().toString();
                    O17+="\n";
                    O17+="\n";
                    O17+="\n";
                    outputStream.write(O9.getBytes());
                    outputStream.write(O4.getBytes());
                    outputStream.write(O12.getBytes());
                    outputStream.write(O11.getBytes());
                    outputStream.write(O0.getBytes());
                    outputStream.write(O16.getBytes());
                    outputStream.write(O14.getBytes());
                    outputStream.write(O15.getBytes());
                    outputStream.write(O10.getBytes());
                    outputStream.write(O5.getBytes());

                    outputStream.write(O1.getBytes());
                    outputStream.write(O8.getBytes());
                    outputStream.write(O6.getBytes());
                    outputStream.write(O7.getBytes());
                    outputStream.write(O3.getBytes());
                    outputStream.write(O2.getBytes());
                    outputStream.write(O13.getBytes());
                    outputStream.write(O19.getBytes());
                    outputStream.write(O18.getBytes());
                    outputStream.write(O17.getBytes());
                    buttonPrint.setTextColor(Color.BLACK);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            /*}
        });
        alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                buttonPrint.setTextColor(Color.BLACK);
            }
        });
        AlertDialog alertDialog = alertdialogbuilder.create();
        alertDialog.show();*/

    }

    //Disconnect Printer
    void disconnectBT() throws IOException{
        /*AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("Disconnect the Printer?");
        alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {*/
        try{
            buttonConnect.setTextColor(Color.BLACK);
            stopWorking = true;
            outputStream.close();
            inputStream.close();
            bluetoothSocket.close();
            lblPrintername.setText("Printer Disconnected.");
        }catch (Exception ex){}
            /*}
        });
        alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertdialogbuilder.create();
        alertDialog.show();*/
    }

}
