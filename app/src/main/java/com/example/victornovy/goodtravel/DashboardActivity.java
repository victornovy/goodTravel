package com.example.victornovy.goodtravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;
import android.widget.TextView;

import android.view.Menu;
import android.view.MenuInflater;

/**
 * Created by Victor Novy on 24/09/2015.
 */
public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
    }

    public void selectOptionDashboard(View view)
    {
        switch (view.getId()) {
            case R.id.new_cost:
                startActivity(new Intent(this, SpentActivity.class));
                break;
            case R.id.new_travel:
                startActivity(new Intent(this, NewtravelActivity.class));
                break;
            case R.id.my_travels:
                startActivity(new Intent(this, TravelListActivity.class));
                break;
            case R.id.configuration:
                startActivity(new Intent(this, ConfigurationActivity.class));
                break;
        }
        TextView textView = (TextView) view;
        Toast.makeText(this, "Opção: " + textView.getText().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item)
    {
        finish();
        return true;
    }
}
