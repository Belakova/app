package com.example.a40122079.manageme;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

        }
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challenges, menu);
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
