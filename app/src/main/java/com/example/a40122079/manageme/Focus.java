package com.example.a40122079.manageme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Focus extends ActionBarActivity {

    TextView WifiState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        WifiState = (TextView)findViewById(R.id.wifistate);
        Button OnWifi = (Button)findViewById(R.id.onwifi);
        Button OffWifi = (Button)findViewById(R.id.offwifi);

        this.registerReceiver(this.WifiStateChangedReceiver,
                new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));

        OnWifi.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                WifiManager wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(true);
            }});

        OffWifi.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                WifiManager wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(false);
            }});
    }

    private BroadcastReceiver WifiStateChangedReceiver
            = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE ,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch(extraWifiState){
                case WifiManager.WIFI_STATE_DISABLED:
                    WifiState.setText("WIFI STATE DISABLED");
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    WifiState.setText("WIFI STATE DISABLING");
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    WifiState.setText("WIFI STATE ENABLED");
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    WifiState.setText("WIFI STATE ENABLING");
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    WifiState.setText("WIFI STATE UNKNOWN");
                    break;
            }

        }};
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_focus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.Kate
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
