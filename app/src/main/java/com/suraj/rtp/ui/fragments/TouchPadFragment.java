package com.suraj.rtp.ui.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.suraj.rtp.R;
import com.suraj.rtp.ui.Login;
import com.suraj.rtp.ui.MainActivity;
import com.suraj.rtp.ui.TouchPanel;
import com.suraj.rtp.worker.SendMessage;


/**
 * Created by suraj on 1/5/16.
 */
public class TouchPadFragment extends Fragment {
    private Button lclick, rclick, scroll;
    private TouchPanel panel;
    private String messsage;
    private SendMessage sendMessageTask;
    private float y1dif = 0;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View touchpadfragment = inflater.inflate(R.layout.fragment_touchpad, container, false);

        panel = (TouchPanel) touchpadfragment.findViewById(R.id.button2);

        lclick = (Button) touchpadfragment.findViewById(R.id.btnlclick);
        rclick = (Button) touchpadfragment.findViewById(R.id.btnrclick);
        scroll = (Button) touchpadfragment.findViewById(R.id.btnscroll);


        // Button press event listener
        Log.i("IP:", Login.IP);


        lclick.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    messsage = MainActivity.LEFTPRESS;
                    messsage = messsage + "\n";
                    sendMessageTask = new SendMessage(messsage);

                    sendMessageTask.execute();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    messsage = MainActivity.LEFTRELEASE;
                    messsage = messsage + "\n";

                    sendMessageTask = new SendMessage(messsage);

                    sendMessageTask.execute();
                }

                return false;
            }
        });


        rclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messsage = MainActivity.RCLICK;
                messsage = messsage + "\n";

                sendMessageTask = new SendMessage(messsage);
                sendMessageTask.execute();
            }
        });

        scroll.setOnTouchListener(new View.OnTouchListener() {
            float iy;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iy = event.getY();
                    return false;
                }
                y1dif = event.getY() - iy;

                if (y1dif < -MainActivity.SENSITIVITY) {
                    messsage = MainActivity.SCROLLUP;
                } else if (y1dif >= MainActivity.SENSITIVITY) {
                    messsage = MainActivity.SCROLLDOWN;
                }

                if(Math.abs(y1dif)<3){
                    return false;
                }

                messsage = messsage + "\n";

                sendMessageTask = new SendMessage(messsage);
                sendMessageTask.execute();
                iy = event.getY();


                return false;
            }
        });


        return touchpadfragment;
    }
}