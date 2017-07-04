package com.jprod.jerryletehen.setyourvolumesimple;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.CheckBox;

public class Setup extends Activity implements View.OnClickListener {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    final String NOTIFICATION = "notification";
    final String VOICE_CALL = "voice_call";
    final String SYSTEM = "system";

    private static boolean notification_b = false;
    private static boolean voice_call_b = false;
    private static boolean system_b = false;

    CheckBox notification;
    CheckBox voice_call;
    CheckBox system;

    FloatingActionButton fab_back;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);

        notification = (CheckBox) findViewById(R.id.notification);
        voice_call = (CheckBox) findViewById(R.id.voice_call);
        system = (CheckBox) findViewById(R.id.system);

        String DEFAULT_SP_KEY = "MainActivity";
        sharedPreferences = getSharedPreferences(DEFAULT_SP_KEY, Context.MODE_PRIVATE);
        Load_it();


        notification.setChecked(notification_b);
        voice_call.setChecked(voice_call_b);
        system.setChecked(system_b);



        fab_back = (FloatingActionButton) findViewById(R.id.fab_back);
        fab_back.setOnClickListener(this);

    }

    private void Load_it() {
        notification_b = sharedPreferences.getBoolean(NOTIFICATION, notification_b);
        voice_call_b = sharedPreferences.getBoolean(VOICE_CALL, voice_call_b);
        system_b = sharedPreferences.getBoolean(SYSTEM, system_b);

        notification.setChecked(notification_b);
        voice_call.setChecked(voice_call_b);
        system.setChecked(system_b);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_back:
                fabBack();
        }
    }

    private void fabBack() {
            Save_it();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
    }

    private void Save_it() {
        editor = sharedPreferences.edit();

        editor.putBoolean(NOTIFICATION, notification.isChecked());
        editor.putBoolean(VOICE_CALL, voice_call.isChecked());
        editor.putBoolean(SYSTEM, system.isChecked());

        editor.apply();
    }


    protected void onStart() {
        super.onStart();
        Load_it();
    }

    protected void onStop() {
        super.onStop();
        Save_it();
    }

    protected void onPause() {
        super.onPause();
        Save_it();
    }

    protected void onResume() {
        super.onResume();
        Load_it();
    }

}