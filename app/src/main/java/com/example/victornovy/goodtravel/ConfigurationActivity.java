package com.example.victornovy.goodtravel;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by Victor Novy on 02/11/2015.
 */
public class ConfigurationActivity extends PreferenceActivity {

    //TODO arrumar chamada deste metodo - page 124
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, fragment()).commit();
    }

    private PreferenceFragment fragment()
    {
        return new PreferenceFragment() {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                addPreferencesFromResource(R.xml.preference);
            }
        };
    }
}
