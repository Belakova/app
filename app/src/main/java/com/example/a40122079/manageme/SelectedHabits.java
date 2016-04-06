package com.example.a40122079.manageme;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Instrumentation;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;


public class SelectedHabits extends Activity {
    TextView time,txt_time;
    Button pickTime,notifyMe,share,saveToDB;
    ListView listView1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_habits);
        listView1 = (ListView)findViewById(R.id.listView1);
        txt_time=(TextView)findViewById(R.id.txt_time);
        time=(TextView)findViewById(R.id.timeDisplay);
        pickTime=(Button)findViewById(R.id.pickTime);
        Bundle b = getIntent().getExtras();
        final String[] result = b.getStringArray("val");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,result);
        listView1.setAdapter(adapter);
        Toast.makeText(getBaseContext(), "Try to stick to your new habits!",  Toast.LENGTH_LONG).show();


        //Share button -> you can Share your Habit list screenShot here
        share = (Button)findViewById(R.id.Share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting a screenshot of a current activity
                View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                rootView.setDrawingCacheEnabled(true);
                Bitmap bm = rootView.getDrawingCache();
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bm);

                //sharing
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "title");
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values);
                OutputStream outstream;
                try {
                    outstream = getContentResolver().openOutputStream(uri);
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                    outstream.close();
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
                share.putExtra(Intent.EXTRA_STREAM,uri);//uri was here
                startActivity(Intent.createChooser(share, "Share screenshot using:"));
             }
        });

        //set the time for notification every day
        final Calendar alarmStartTime = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 8);
        alarmStartTime.set(Calendar.MINUTE, 0);
        alarmStartTime.set(Calendar.SECOND, 0);

        //Notify Button show notifications
        notifyMe=(Button)findViewById(R.id.notifyMe);
        notifyMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedHabits.this, Receiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(SelectedHabits.this,0,intent, 0);
                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.setRepeating(am.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), am.INTERVAL_DAY,pendingIntent);
            }
        });

         final HealthDB db = new HealthDB(this);
        //Save Button to sqlite DB -> HealthDB
        saveToDB=(Button)findViewById(R.id.SavetoDB);
        saveToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<result.length;i++){
                    db.add(result.toString());
                    String [] s= databaseList(); // Put extra as Intent after
                    Toast.makeText(getBaseContext(),"Your list has been saved", Toast.LENGTH_SHORT).show();
                    //Intent saved = new Intent(SelectedHabits.this,MenuSelected.class);
                    // Bundle b = new Bundle();
                    //  b.putStringArray("my",s);
                    //  saved.putExtras(b);
                }
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
