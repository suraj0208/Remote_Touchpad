package com.suraj.rtp.ui;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import com.suraj.rtp.worker.SendMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TouchPanel extends Button implements GestureDetector.OnGestureListener {
    private boolean doubletapped = false;
    private long prevtime = 20;
    private long currtime = 1000;
    private boolean twofinger = false;
    private boolean scroll = false;
    private Socket client;
    private SendMessage sendMessageTask;
    private GestureDetectorCompat gestureDetectorCompat;
    private String messsage;

    float ix, iy, iy1, xdif, ydif, y1dif;


    public TouchPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Log.i("2 arg", "construxt");

        this.gestureDetectorCompat = new GestureDetectorCompat(context, this);



        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.i("event", "ontouch");

                if (event.getPointerCount() == 2) {
                    //Log.i("event", "double finger");
                    twofinger = true;

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            prevtime = System.currentTimeMillis();
                            iy1 = event.getY(event.getPointerId(0));
                            return true;

                        case MotionEvent.ACTION_MOVE:
                            break;

                    }

                    currtime = System.currentTimeMillis();

                    ////Log.i("iy1:", Float.toString(iy1));
                    ////Log.i("current iy:", Float.toString(event.getY()));

                    y1dif = event.getY(event.getPointerId(0)) - iy1;
                    iy1 = event.getY(event.getPointerId(0));


                    if ((currtime - prevtime > 200) && y1dif < -MainActivity.SENSITIVITY) {
                        messsage = MainActivity.SCROLLUP;
                        scroll = true;
                        sendMessageTask = new SendMessage(messsage);
                        sendMessageTask.execute();
                        return true;

                    } else if ((currtime - prevtime > 200) && y1dif > MainActivity.SENSITIVITY) {
                        messsage = MainActivity.SCROLLDOWN;
                        scroll = true;
                        sendMessageTask = new SendMessage(messsage);
                        sendMessageTask.execute();
                        return true;
                    }
                    //return true;
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //Log.i("event", "actionup");

                    if (currtime - prevtime < 200 && twofinger) {
                        messsage = MainActivity.RCLICK;
                        sendMessageTask = new SendMessage(messsage);
                        sendMessageTask.execute();
                        return true;
                    }

                    if (doubletapped) {

                        messsage = MainActivity.LEFTRELEASE;
                        sendMessageTask = new SendMessage(messsage);
                        sendMessageTask.execute();
                        doubletapped = false;
                        return true;
                    }

                    //set scroll to false when twofingers are up
                    //otherwise mouse will not move ever
                    if (scroll) {
                        scroll = false;
                        return true;
                    }
                }

                //prevent mouse from moving when scrolled with two fingers
                if (scroll)
                    return true;


                xdif = event.getX() - ix;
                ydif = event.getY() - iy;

                if (gestureDetectorCompat.onTouchEvent(event)) {
                    return true;
                }

                messsage = Float.toString(xdif) + "&" + Float.toString(ydif);

                sendMessageTask = new SendMessage(messsage);

                sendMessageTask.execute();
                System.out.println("sent movement");

                return false;
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //scaleGestureDetector.onTouchEvent(event);

        return true;
    }

    public TouchPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //Log.i("3 arg", "construxt");
    }


    public TouchPanel(Context context) {
        super(context);
        //Log.i("1 arg", "construxt");

    }

    @Override
    public boolean onDown(MotionEvent event) {
        //Log.i("event", "ondown");

        ix = event.getX();
        iy = event.getY();
        messsage = MainActivity.FIRST;
        sendMessageTask = new SendMessage(messsage);
        sendMessageTask.execute();

        currtime = System.currentTimeMillis();

        ////Log.i("currtime", Long.toString(currtime));


        if (currtime - prevtime < 200) {

            messsage = MainActivity.LEFTPRESS;
            sendMessageTask = new SendMessage(messsage);
            sendMessageTask.execute();
            doubletapped = true;
        }
        prevtime = currtime;

        twofinger = false;

        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //Log.i("event:", "singletapup");


        if (Math.abs(xdif) <= 3 && Math.abs(ydif) <= 3) {
            messsage = MainActivity.LCLICK;
            sendMessageTask = new SendMessage(messsage);

            sendMessageTask.execute();

            return true;

        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        ix = e1.getX();
        iy = e1.getY();
        messsage = "first";
        sendMessageTask = new SendMessage(messsage);
        sendMessageTask.execute();

        return true;
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            //Log.i("event:", "on scale");

            doubletapped = true;

            //Log.i("factor:", Float.toString(detector.getScaleFactor()));

            if (detector.getScaleFactor() < 1)
                messsage = "scrolldown";
            else
                messsage = "scrollup";

            sendMessageTask = new SendMessage(messsage);

            sendMessageTask.execute();

            return true;
        }
    }

}