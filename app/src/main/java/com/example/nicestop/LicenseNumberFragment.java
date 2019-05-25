package com.example.nicestop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import org.jetbrains.annotations.NotNull;


/**
 * A simple {@link Fragment} subclass.
 */
public class LicenseNumberFragment extends Fragment implements View.OnClickListener{
    public LicenseNumberFragment() {
        // Required empty public constructor
    }
  CheckBox checkBoxDO,checkBoxSK,checkBoUV;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Toast.makeText(getActivity(),"Ticket",Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_license_number, container, false);
        Button btnscan = (Button)view.findViewById(R.id.buttonScan);
         checkBoxDO = (CheckBox) view.findViewById(R.id.checkBoxDO);
         checkBoxSK = (CheckBox) view.findViewById(R.id.checkBoxSK);
         checkBoUV = (CheckBox) view.findViewById(R.id.checkBoUV);
        btnscan.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_scan, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.ActionScan){
            Toast.makeText(getActivity(),"Click on "+ item.getTitle(),Toast.LENGTH_SHORT).show();
        }
        return true;

    }

   /* @SuppressLint("ShowToast")
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(getActivity(),"Result not Found",Toast.LENGTH_SHORT).show();
            }
            else{
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(getContext());
                alertdialogbuilder.setMessage(result.getContents()+"Scan Again ?");
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
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
            Toast.makeText(getActivity(),"Scann Complete!",Toast.LENGTH_SHORT).show();
        }
    }*/
    public void scannow(){
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setCaptureActivity(Scan.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("SCAN");
        integrator.initiateScan();
    }

    @Override
    public void onClick(View v) {
        scannow();
    }

}
