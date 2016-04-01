package com.example.a40122079.manageme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Health extends Activity implements OnClickListener {

    final String LOG_TAG = "myLogs";
    FileOutputStream outputStream;
    ListView lvMain;
    String[] habits;
    FileWriter writer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        lvMain = (ListView) findViewById(R.id.lvMain);
        // ????????????? ????? ?????? ??????? ??????
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        // ??????? ???????, ????????? ?????? ?? ????? ????????
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.habits,android.R.layout.simple_list_item_multiple_choice);
        lvMain.setAdapter(adapter);

        Button btnChecked = (Button) findViewById(R.id.btnChecked);
        btnChecked.setOnClickListener(this);

        // ???????? ?????? ?? ????? ????????
        habits = getResources().getStringArray(R.array.habits);
    }


    public void onClick(View arg0) {
        // ????? ? ??? ?????????? ????????


        Log.d(LOG_TAG, "checked: ");
        SparseBooleanArray sbArray = lvMain.getCheckedItemPositions();
        for (int i = 0; i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key))

                try {
                    outputStream = openFileOutput("habits.txt", Context.MODE_PRIVATE);
                    outputStream.write(habits[key].getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            Intent next =new Intent(Health.this,SelectedHabits.class);
            startActivity(next);
               // Log.d(LOG_TAG, habits[key]);




        }
    }
}