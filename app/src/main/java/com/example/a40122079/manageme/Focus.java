package com.example.a40122079.manageme;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Focus extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        final Button OnWifi = (Button)findViewById(R.id.onwifi);
        Button OffWifi = (Button)findViewById(R.id.offwifi);
        final TextView timer = (TextView)findViewById(R.id.timer);


        //disables wifi
        OffWifi.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                OnWifi.setVisibility(View.INVISIBLE);
                WifiManager wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(false);

                new CountDownTimer(360000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        timer.setText("Time remaining: " + millisUntilFinished / 1000);
                    }
                    public void onFinish() {
                        timer.setText("You survived without wifi!");
                        OnWifi.setVisibility(View.VISIBLE);
                    }
                }.start();
            }}
        );

        OnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiManager wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(true);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_focus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
