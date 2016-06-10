package com.suraj.rtp.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.suraj.rtp.R;
import com.suraj.rtp.ui.widgets.PagerAdapter;
import com.suraj.rtp.worker.PowerQueryAdapter;
import com.suraj.rtp.worker.SendMessage;
import com.viewpagerindicator.TitlePageIndicator;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {
    public static final String FIRST = "first";
    public static final String LEFTPRESS = "lclickp";
    public static final String LEFTRELEASE = "lclickr";
    public static final String LCLICK = "lclick";
    public static final String SCROLLUP = "scrollup";
    public static final String SCROLLDOWN = "scrolldown";
    public static final String RCLICK = "rclick";
    public static final float SENSITIVITY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.pager);

        getSupportActionBar().hide();

        PagerAdapter pagerAdapter =
                new com.suraj.rtp.ui.widgets.PagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

        setOtherStuff(viewPager);

        (new Thread() {
            @Override
            public void run() {
                try {
                    Socket client = new Socket(Login.IP, 4444); // connect to the server
                    System.out.println("Connected");
                    SendMessage.client = client;
                    SendMessage.printwriter=new PrintWriter(client.getOutputStream(), true);

                    //client = new Socket(Login.IP, 4445);
                    //PowerQueryAdapter.setSocket(client);
                    //PowerQueryAdapter powerQueryAdapter = new PowerQueryAdapter(MainActivity.this);
                    //powerQueryAdapter.execute();

                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Unable to connect.",Toast.LENGTH_SHORT).show();
                            //MainActivity.this.finish();
                        }
                    });

                }
            }
        }
        ).start();


    }

    @Override
    protected void onPause() {
        try {
            if(SendMessage.client!=null)
                SendMessage.client.close();
                //PowerQueryAdapter.closePowerSocket();

        } catch (IOException e) {
            System.out.println("Couldnt close socket in onPause");
        }
        super.onPause();
    }

    public void closeIME(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setOtherStuff(final ViewPager viewPager){
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    closeIME(viewPager);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        TitlePageIndicator tabIndicator = (TitlePageIndicator) findViewById(R.id.titles);
        tabIndicator.setViewPager(viewPager);
    }
}


