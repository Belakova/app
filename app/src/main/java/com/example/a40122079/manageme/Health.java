package com.example.a40122079.manageme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;


public class Health extends Activity {
    ArrayAdapter<String> adapter;
    ListView lvMain;
    String[] habits;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);//



       lvMain = (ListView) findViewById(R.id.lvMain);
       habits = getResources().getStringArray(R.array.habits); // the array of all habits
       adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, habits);
       lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
       lvMain.setAdapter(adapter);
        Button btnChecked = (Button) findViewById(R.id.btnChecked);
        final HealthDB db =HealthDB.getInstance(this);// calling singleton of HealthDB


            btnChecked.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    SparseBooleanArray checked = lvMain.getCheckedItemPositions();
                    ArrayList<String> arr_selected = new ArrayList<String>(); // to store selected items
                    for (int i = 0; i < checked.size(); i++) {
                        int position = checked.keyAt(i);
                        if (checked.valueAt(i))
                            arr_selected.add(adapter.getItem(position));
                    }

                    for (int i = 0; i < arr_selected.size(); i++) {
                         db.add(arr_selected.get(i)); //adding all data to the column

                    }

                        Intent next = new Intent(getApplicationContext(), SelectedHabits.class);

                        startActivity(next);

                } });

    }
}