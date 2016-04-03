package com.example.a40122079.manageme;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SelectedHabits extends Activity {
    TextView time,txt_time;
    Button pickTime,notifyMe,share;
    ListView listView1;
    public HabitsList provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_habits);

        listView1 = (ListView)findViewById(R.id.listView1);
        txt_time=(TextView)findViewById(R.id.txt_time);
        time=(TextView)findViewById(R.id.timeDisplay);
        pickTime=(Button)findViewById(R.id.pickTime);

        Bundle b = getIntent().getExtras();
        String[] result = b.getStringArray("val");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,result);
        listView1.setAdapter(adapter);
        provider = new HabitsList(this);
        provider.addTask(result.toString());
        provider.findAll();

        Toast.makeText(getBaseContext(), "Try to stick to your new habits!",  Toast.LENGTH_LONG).show();
        notifyMe=(Button)findViewById(R.id.notifyMe);
        share = (Button)findViewById(R.id.Share);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse("android.resource://com.example.a40122079/manageme/*");
                try {
                    InputStream stream = getContentResolver().openInputStream(screenshotUri);
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                sharingIntent.setType("image/jpeg");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));
            }
        });

        //set the time for notification every day
        final Calendar alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 8);
        alarmStartTime.set(Calendar.MINUTE, 0);
        alarmStartTime.set(Calendar.SECOND, 0);

        notifyMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectedHabits.this, Receiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(SelectedHabits.this, 0, intent, 0);
                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.setRepeating(am.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), am.INTERVAL_DAY, pendingIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_selected_habits, menu);
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
