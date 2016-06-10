package com.suraj.rtp.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import android.os.Handler;

import com.suraj.rtp.R;

public class Bounce extends ImageView {
    Context mContext;
    Handler handler;
    int x=-1;
    int y=-1;
    int xvelocity=10;
    int yvelocity=5;
    public static int FRAME_RATE=20;

    public Bounce(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        handler=new Handler();

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        BitmapDrawable ball = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.ball1);
        if (x<0 && y <0) {

            x = this.getWidth()/2;

            y = this.getHeight()/2;

        } else {
            //Log.i("before adding x and y", Integer.toString(x) + Integer.toString(y));

            x += xvelocity;

            y += yvelocity;

                if ((x > this.getWidth() - ball.getBitmap().getWidth()) || (x < 0)) {

                //Log.i("x greater and y", Integer.toString(x) + Integer.toString(y));

                xvelocity = xvelocity*-1;

            }

            if ((y > this.getHeight() - ball.getBitmap().getHeight()) || (y < 0)) {
                //Log.i("ygreater x and y", Integer.toString(x) + Integer.toString(y));

                yvelocity = yvelocity*-1;

            }

        }
        canvas.drawBitmap(ball.getBitmap(), x, y, null);

        handler.postDelayed(runnable, FRAME_RATE);
    }
}
