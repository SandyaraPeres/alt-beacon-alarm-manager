package com.aplicativo.fcamara.poc_beacon;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import android.util.Log;
import org.altbeacon.beacon.*;
import java.util.ArrayList;
import java.util.Collection;

public class BeaconScanner implements BeaconConsumer{

    protected static final String TAG = "RangingActivity";
    private static boolean emExecucao;
    private BeaconManager beaconManager;
    private Context context;

    public BeaconScanner(Context context)
    {
        this.context = context;

        BeaconScanner.emExecucao = false;

        beaconManager = BeaconManager.getInstanceForApplication(context);

        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        beaconManager.bind(this);
    }

    public void Scan(){
        try {
            BeaconScanner.emExecucao = true;
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBeaconServiceConnect() {

        RangeNotifierEx rangeNotifier = new RangeNotifierEx(this) {

            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                ArrayList<String> beaconsEncontrados = new ArrayList<String>();

                if (beacons.size() > 0) {
                    for (Beacon beacon : beacons) {

                        if (beacon.getDistance() <= 15) {
                            Log.i(TAG, "Beacon: " + beacon.getId1().toString() + " DISTANCIA: " + Double.toString(beacon.getDistance()));
                            beaconsEncontrados.add(beacon.getId1().toString().toLowerCase());
                        }
                    }
                }

                try {
                    beaconManager.stopRangingBeaconsInRegion(region);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }


                if (BeaconScanner.emExecucao) {
                    BeaconScanner.emExecucao = false;

                    Intent i = new Intent(context, POCActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("Beacons", beaconsEncontrados.toArray());
                    i.putStringArrayListExtra("BeaconsEncontrados", beaconsEncontrados);

                    context.startActivity(i);
                }
            }
        };

        beaconManager.addRangeNotifier(rangeNotifier);
    }

    @Override
    public Context getApplicationContext() {
        return this.context;
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {

    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return false;
    }
}
