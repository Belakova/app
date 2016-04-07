package com.example.a40122079.manageme;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


public class Challenges extends ActionBarActivity {

    TextView UsrName;
    Button health,focus,todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        UsrName=(TextView)findViewById(R.id.Name);
        // getting the username

        String name = getIntent().getExtras().getString("Username");
        UsrName.setText(name);

    }

    public void Change( View v){
        switch (v.getId()) {
            case R.id.Healthier:
                Intent one= new Intent(Challenges.this,Health.class);
                startActivity(one);
                break;
            case R.id.Focus:
                Intent two = new Intent(Challenges.this,Focus.class);
                startActivity(two);
                break;
            case R.id.Todo:
                Intent three= new Intent(Challenges.this,Todo.class);
                startActivity(three);
                break;
            case R.id.MyList:
                Intent myList=new Intent(Challenges.this,SelectedHabits.class);
                startActivity(myList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_challenges,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
