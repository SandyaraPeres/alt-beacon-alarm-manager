package com.aplicativo.fcamara.poc_beacon;

import android.app.NotificationManager;
import android.content.Context;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class BeaconSamel {
    private int contador;
    private Context context;

    public boolean Visivel;
    public String UUID;

    public BeaconSamel(String uuid, Context context)
    {
        this.UUID = uuid;
        this.context = context;
    }

    public void DefinirVisibilidade(boolean beaconVisivel)
    {
        if (beaconVisivel)
        {
            if (!this.Visivel) {
                Visivel = true;
                contador = 0;

                this.GerarNotificacao();
            }
        }
        else
        {
            if (this.Visivel) {
                contador++;

                if (contador >= 3) {
                    Visivel = false;

                    this.GerarNotificacao();
                }
            }
        }
    }

    private void GerarNotificacao()
    {
        String mensagem = "";

        if (this.Visivel)
        {
            mensagem = "BEACON " + this.UUID + " esta visível!";
        }
        else
        {
            mensagem = "BEACON " + this.UUID + " não está mais visível!";
        }

        Toast.makeText(this.context, mensagem, Toast.LENGTH_SHORT).show();

    }
}
