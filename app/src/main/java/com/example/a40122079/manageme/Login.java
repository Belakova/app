package com.example.a40122079.manageme;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Login extends ActionBarActivity {

    EditText usr,psw;
    Button loginBtn;
    TextView failed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usr=(EditText)findViewById(R.id.usernameLogin);
        psw=(EditText)findViewById(R.id.passwordLogin);
        loginBtn=(Button)findViewById(R.id.Login);
        failed=(TextView)findViewById(R.id.failedLogin);

    }

    public void Login(View view){
        if (usr.getText().toString().equals("Kate") && psw.getText().toString().equals("demo")){
            Intent LogedIn = new Intent(Login.this,Challenges.class);
            LogedIn.putExtra("Username",usr.getText().toString());
            startActivity(LogedIn);
        }
        else{
            failed.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
