package com.example.abdalrheem.maindisplay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {
    EditText serverIP;
    Button save,back;
    String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        serverIP= findViewById(R.id.serverip);
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        String servIP=pref.getString("serverIP","");
        String couID=pref.getString("CounterID","");

        serverIP.setText(servIP);
        save= findViewById(R.id.Save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip=serverIP.getText().toString();
                SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(Settings.this);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("serverIP",ip);
                editor.apply();
            }
        });

    }

    public void sendMessage(View view)
    {
        Intent intent = new Intent(Settings.this, MainActivity.class);
        startActivity(intent);
    }

}
