package com.aplicativo.fcamara.poc_beacon;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import org.altbeacon.beacon.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class POCActivity extends AppCompatActivity {

    private static ArrayList<BeaconSamel> beaconsSamel = null;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;


    private void VerificarBeaconsEncontrados()
    {
        ArrayList<String> beaconsEncontrados = null;
        Intent i = getIntent();
        Bundle z = i.getExtras();

        if (z != null) {
            Object o = z.get("Beacons");

            beaconsEncontrados = z.getStringArrayList("BeaconsEncontrados");


            if (beaconsEncontrados != null) {
                for (BeaconSamel beaconSamel : POCActivity.beaconsSamel) {
                    beaconSamel.DefinirVisibilidade(beaconsEncontrados.contains(beaconSamel.UUID));
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (POCActivity.beaconsSamel == null) {
            POCActivity.beaconsSamel = new ArrayList<BeaconSamel>();

            beaconsSamel.add(new BeaconSamel("b29dea6f-f9b4-40ab-ace6-d2794d30759b", this));
            beaconsSamel.add(new BeaconSamel("e38008a0-1123-41cb-a6a9-a970e17e03ba", this));
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poc);
        AlarmRegister.registrar(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("This app needs location access");
            builder.setMessage("Please grant location access so this app can detect beacons in the background.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @TargetApi(23)
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            PERMISSION_REQUEST_COARSE_LOCATION);
                }

            });
            builder.show();
        }


        this.VerificarBeaconsEncontrados();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }
}


