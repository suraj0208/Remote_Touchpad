package com.suraj.rtp.worker;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by suraj on 1/5/16.
 */
public class SendMessage extends AsyncTask<Void,Void,Void> {
    public static Socket client;
    public static PrintWriter printwriter;
    private String messsage;


    public SendMessage(String messsage) {
        this.messsage=messsage;
    }

    @Override
    protected Void doInBackground(Void... params) {
        messsage=messsage+"\n";

        printwriter.write(messsage); // write the message to output stream

        printwriter.flush();

        return null;
    }

}
