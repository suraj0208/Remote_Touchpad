package com.suraj.rtp.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.suraj.rtp.R;
import com.suraj.rtp.worker.SendMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by om on 05/06/2016.
 */
public class PowerFragment extends Fragment implements View.OnClickListener {
    Button powerButton;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View powerFragment = inflater.inflate(R.layout.fragment_power, container, false);

        powerButton = (Button) powerFragment.findViewById(R.id.btnPower);

        powerButton.setText("100%");
        powerButton.setOnClickListener(this);

        return powerFragment;
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnPower:
                final Socket socket = SendMessage.client;
                new SendMessage("getpower").execute();

                (new AsyncTask<Void, Void, Void>() {
                    String percentage = null;

                    @Override
                    protected Void doInBackground(Void... params) {
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
                        ((Button) v).setText(percentage+"%");
                    }
                }).execute();
                break;
        }
    }
}