package com.aplicativo.fcamara.poc_beacon;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;

import static android.content.Context.ALARM_SERVICE;

public class AlarmRegister {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void registrar(Context context){
        Intent it = new Intent(context, POCAlarm.class);

        PendingIntent p = PendingIntent.getBroadcast( context, 101, it, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar c = Calendar.getInstance();

        c.setTimeInMillis(System.currentTimeMillis());

        c.add(Calendar.SECOND, 15);

        AlarmManager alarme = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        long time = c.getTimeInMillis();

        alarme.setExact(AlarmManager.RTC_WAKEUP, time, p);

        Log.i("Alarme", "Alarme agendado!");
    }
}
