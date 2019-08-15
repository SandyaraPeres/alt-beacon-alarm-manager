package com.aplicativo.fcamara.poc_beacon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;

public class POCAlarm extends BroadcastReceiver {

    private static BeaconScanner bs;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {

        if (POCAlarm.bs == null)
        {
            POCAlarm.bs = new BeaconScanner(context);
        }

        POCAlarm.bs.Scan();

        AlarmRegister.registrar(context);
    }
}
