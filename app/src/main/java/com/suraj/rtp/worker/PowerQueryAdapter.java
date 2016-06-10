package com.suraj.rtp.worker;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import com.suraj.rtp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by om on 05/06/2016.
 */
public class PowerQueryAdapter extends AsyncTask<Void, Void, Void> {
    private String percentage = null;
    private static Socket socket;
    private Context context;

    public PowerQueryAdapter(Context context) {
        this.context = context;
    }


    public static void setSocket(Socket socket) {
        PowerQueryAdapter.socket = socket;
    }

    @Override
    protected Void doInBackground(Void[] params) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            percentage = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(percentage!=null){
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.icon)
                            .setContentTitle("Laptop Battery Low")
                            .setContentText("Your Laptop battery is " + percentage +"%");

            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(96577, mBuilder.build());

            new PowerQueryAdapter(context).execute();

        }
    }

    public static void closePowerSocket() throws IOException {
        PowerQueryAdapter.socket.close();
    }
}
