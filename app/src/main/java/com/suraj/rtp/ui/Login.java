package com.suraj.rtp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.suraj.rtp.R;
import java.util.regex.Pattern;


public class Login extends AppCompatActivity {
    public static String IP;
    private EditText etip;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etip = (EditText) findViewById(R.id.etip);
        Button btnlogin = (Button) findViewById(R.id.btnlogin);

        SharedPreferences sharedPreferences = getSharedPreferences("preferences", 0);
        editor = sharedPreferences.edit();

        etip.setText(sharedPreferences.getString("IP", ""));

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        assert menu != null;
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.actionbar_icon);
        menu.setDisplayUseLogoEnabled(true);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredIP = etip.getText().toString();

                if (Pattern.compile("[0-9]+[.][0-9]+[.][0-9]+[.][0-9]+").matcher(enteredIP).matches()) {
                    IP = etip.getText().toString();
                    editor.putString("IP", Login.IP);
                    editor.apply();
                    startActivity(new Intent(Login.this, MainActivity.class));

                } else {
                    new AlertDialog.Builder(Login.this)
                            .setTitle("Invalid IP")
                            .setMessage("Please enter valid IP")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login, menu);
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
}
