package com.example.a40122079.manageme;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.AvoidXfermode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class Health extends ListActivity {


    int count = 0; // counter for the selection


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        final ArrayAdapter<Model> adapter = new InteractiveArrayAdapter(this,
                getModel());
        setListAdapter(adapter);

        Button save = (Button)findViewById(R.id.saveSelection);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent next = new Intent(Health.this, SelectedHabits.class);
                startActivity(next);
            }
        });
}
    private List<Model> getModel(){
        List<Model>list=new ArrayList<Model>();
        list.add(get("go for a walk"));
        list.add(get("drink water"));
        list.add(get("go for a walk"));
        list.add(get("drink water"));
        list.add(get("go for a walk"));
        list.add(get("drink water"));
        list.get(1).setSelected(true);
        return list;
    }

    private Model get(String s){
        return new Model(s);
    }






}
