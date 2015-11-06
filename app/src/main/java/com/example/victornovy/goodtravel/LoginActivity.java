package com.example.victornovy.goodtravel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Toast;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LoginActivity extends AppCompatActivity {

    private static final String KEEP_CONNECTED = "keep_connected";
    private EditText txtUser;
    private EditText txtPassword;
    private CheckBox chcKeepConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        this.txtUser = (EditText) findViewById(R.id.txtUser);
        this.txtPassword = (EditText) findViewById(R.id.txtPassword);
        this.chcKeepConnected = (CheckBox) findViewById(R.id.chc_keep_connected);

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean connected = preferences.getBoolean(KEEP_CONNECTED, false);

        if (connected)
            startActivity(new Intent(this, DashboardActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void loginOnClick(View v)
    {
        String user = txtUser.getText().toString();
        String password = txtPassword.getText().toString();

        if ("teste".equals(user) && "123".equals(password)) {

            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            Editor editor = preferences.edit();
            editor.putBoolean(KEEP_CONNECTED, chcKeepConnected.isChecked());
            editor.apply();

            startActivity(new Intent(this, DashboardActivity.class));
        } else {
            String message = getString(R.string.fail_authentication);
            Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
