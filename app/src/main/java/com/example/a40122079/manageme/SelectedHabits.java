package com.example.a40122079.manageme;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.io.OutputStream;
import java.util.Calendar;


public class SelectedHabits extends Activity {
    TextView time;
    Button notifyMe,share,saveToDB ;
    ListView listView1;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_habits);
         listView1 = (ListView)findViewById(R.id.listView1);
         time=(TextView)findViewById(R.id.timeDisplay);

         final HealthDB db =HealthDB.getInstance(this);//calling singleton
         ArrayAdapter<String>adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,db.getHabits());
         listView1.setAdapter(adapter1);


         final Calendar alarmStartTime = Calendar.getInstance();
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
                share.putExtra(Intent.EXTRA_STREAM, uri);//uri was here
                startActivity(Intent.createChooser(share, "Share screenshot using:"));
            }
        });




        //Notify Button show notifications
        notifyMe=(Button)findViewById(R.id.notifyMe);
        notifyMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  alarmStartTime.set(Calendar.HOUR_OF_DAY, 8);
                  alarmStartTime.set(Calendar.MINUTE, 00);
                  alarmStartTime.set(Calendar.SECOND, 00);
                 Intent intent = new Intent(SelectedHabits.this, Receiver.class);
                 PendingIntent pendingIntent = PendingIntent.getBroadcast(SelectedHabits.this,0,intent, 0);
                 AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                 am.setRepeating(am.RTC_WAKEUP, System.currentTimeMillis(),am.INTERVAL_DAY, pendingIntent);//every 4 hours //am.INTERVAL_DAY
                Toast.makeText(getBaseContext(), "You will be notififed daily at 8 am",  Toast.LENGTH_LONG).show();
            }
        });
      this.listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //  String item=(String)(listView1.getItemAtPosition(position));
            //  int ItemId = listView1.indexOfChild(item);
            //  db.delete(ItemId);

          }
      });


         //Not working
        //Delete Button from sqlite DB -> HealthDB
        saveToDB=(Button)findViewById(R.id.SavetoDB);
        saveToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        for (int i=0; i<db.getHabits().size();i++){
               db.delete(db.getHabits().toString());
          }



                Toast.makeText(getBaseContext(),"Your item has been deleted", Toast.LENGTH_SHORT).show();
            }
        });}




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
