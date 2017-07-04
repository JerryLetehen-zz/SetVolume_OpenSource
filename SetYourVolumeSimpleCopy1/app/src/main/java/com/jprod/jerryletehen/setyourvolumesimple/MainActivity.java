package com.jprod.jerryletehen.setyourvolumesimple;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener  {

    Toast toast;

    //save
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    final String SAVED_TIME_TEXT_1 = "Saved_Time_1";
    final String SAVED_VOLUME_TEXT_1 = "Saved_Volume_1";
    final String SAVED_HOUR_1 = "Selected_Hour_1";
    final String SAVED_MINUTE_1 = "Selected_Minute_1";
    final String SAVED_VOLUME_1 = "Selected_Volume_1";
    final String CHECK_IF_SET_TIME_1 = "if_time_set_1";
    final String CHECK_IF_SET_VOLUME_1 = "if_time_set_1";
    final String CHECK_IF_SET_1 = "if_can_set_1";

    //int, float
    public static int selected_hour_1 = 0;
    public static int selected_minute_1 = 0;
    public static int selected_volume_1 = 0;

    //String
    private String Name_Time_1;
    private String Name_Volume_1;

    //boolean

    private static boolean checktime_1 = false;
    private static boolean checkvolume_1 = false;
    private static boolean checkset_1 = false;

    //View components
    TextView set_time_1;
    TextView set_volume_1;
    Button set_button_1;
    Button cancel_button_1;
    FloatingActionButton fab;
    FloatingActionButton fab1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        set_time_1 = (TextView) findViewById(R.id.text_time_1);
        set_volume_1 = (TextView) findViewById(R.id.text_volume_1);
        set_button_1 = (Button) findViewById(R.id.set_button_1);
        cancel_button_1 = (Button) findViewById(R.id.cancel_button_1);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);

        //Load
        String DEFAULT_SP_KEY = "MainActivity";
        sharedPreferences = getSharedPreferences(DEFAULT_SP_KEY, Context.MODE_PRIVATE);
        Load_it();

        // default
        // text 1
        if (Name_Time_1 == null && Name_Volume_1 == null) {
            Name_Time_1 = getResources().getString(R.string.text_time);
            Name_Volume_1 = getResources().getString(R.string.text_volume);
            set_time_1.setText(Name_Time_1);
            set_volume_1.setText(Name_Volume_1);
        } else {
            set_time_1.setText(Name_Time_1);
            set_volume_1.setText(Name_Volume_1);
        }


        set_time_1.setOnClickListener(this);
        set_volume_1.setOnClickListener(this);
        set_button_1.setOnClickListener(this);
        cancel_button_1.setOnClickListener(this);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
    }

    private void Load_it() {
        String Saved_Time_Text_1 = sharedPreferences.getString(SAVED_TIME_TEXT_1, Name_Time_1);
        set_time_1 = (TextView) findViewById(R.id.text_time_1);
        set_time_1.setText(Saved_Time_Text_1);
        String Saved_Volume_Text_1 = sharedPreferences.getString(SAVED_VOLUME_TEXT_1, Name_Volume_1);
        set_volume_1 = (TextView) findViewById(R.id.text_volume_1);
        set_volume_1.setText(Saved_Volume_Text_1);
        selected_hour_1 = sharedPreferences.getInt(SAVED_HOUR_1, 0);
        selected_minute_1 = sharedPreferences.getInt(SAVED_MINUTE_1, 0);
        selected_volume_1 = sharedPreferences.getInt(SAVED_VOLUME_1, 0);
        checktime_1 = sharedPreferences.getBoolean(CHECK_IF_SET_TIME_1, false);
        checkvolume_1 = sharedPreferences.getBoolean(CHECK_IF_SET_VOLUME_1, false);
        checkset_1 = sharedPreferences.getBoolean(CHECK_IF_SET_1, false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_time_1:
                getTimeFor_1();
                break;
            case R.id.text_volume_1:
                getVolumeFor_1();
                break;
            case R.id.set_button_1:
                setBtn_1();
                break;
            case R.id.cancel_button_1:
                cancelBtn_1();
                break;
            case R.id.fab:
                fabBtn();
                break;
            case R.id.fab1:
                fabBtn1();
                break;
        }
    }

    private void fabBtn1() {
        Intent intent = new Intent(getApplicationContext(), Setup.class);
        startActivity(intent);
    }

    private void fabBtn() {
            final AlertDialog.Builder main_alert = new AlertDialog.Builder(MainActivity.this);
            main_alert.setMessage(R.string.available);
            main_alert.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            main_alert.setPositiveButton(R.string.buy, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.jprod.jerryletehen.setyourvolumepro"));
                    startActivity(intent);
                }
            });
            main_alert.show();
    }

    private void cancelBtn_1() {
        if (checkset_1) {
            Intent cancel_intent = new Intent(getApplicationContext(), SetVolume_1.class);
            PendingIntent cancel_pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 23, cancel_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.cancel(cancel_pendingIntent);
            Name_Time_1 = null;
            Name_Volume_1 = null;
            set_time_1.setText(R.string.text_time);
            set_volume_1.setText(R.string.text_volume);
            selected_hour_1 = 0;
            selected_minute_1 = 0;
            selected_volume_1 = 0;
            checktime_1 = false;
            checkvolume_1 = false;
            checkset_1 = false;
            Save_it();
            toast = Toast.makeText(MainActivity.this, R.string.Canceled, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast = Toast.makeText(MainActivity.this, R.string.nothing_to_cancel, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void setBtn_1() {
        if (!checktime_1) {
            toast = Toast.makeText(MainActivity.this, R.string.didnt_set_time, Toast.LENGTH_SHORT);
            toast.show();
        } else if (!checkvolume_1) {
            toast = Toast.makeText(MainActivity.this, R.string.didnt_set_volume, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            final AlertDialog.Builder main_alert = new AlertDialog.Builder(MainActivity.this);
            main_alert.setTitle(set_time_1.getText().toString() + "  " + set_volume_1.getText().toString());
            main_alert.setMessage(R.string.confirmation);
            main_alert.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            main_alert.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Name_Time_1 = set_time_1.getText().toString();
                    Name_Volume_1 = set_volume_1.getText().toString();
                    checkset_1 = true;
                    Save_it();
                    Alarm_it();
                }
            });
            main_alert.show();
        }
    }

    private void Alarm_it() {
        Intent intent = new Intent(getApplicationContext(), SetVolume_1.class);
        PendingIntent pendingIntent_1 = PendingIntent.getBroadcast(getApplicationContext(), 23, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, selected_hour_1);
        calendar.set(Calendar.MINUTE, selected_minute_1);
        calendar.set(Calendar.SECOND, 0);
        long time = calendar.getTimeInMillis();
        if (System.currentTimeMillis() > time) {
            time = time + 24 * 60 * 60 * 1000; // Next day
        }
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pendingIntent_1);
        toast = Toast.makeText(MainActivity.this, Name_Time_1 + "  " + Name_Volume_1, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void Save_it() {
        editor = sharedPreferences.edit();

        editor.putInt(SAVED_HOUR_1, selected_hour_1);
        editor.putInt(SAVED_MINUTE_1, selected_minute_1);
        editor.putInt(SAVED_VOLUME_1, selected_volume_1);
        editor.putString(SAVED_TIME_TEXT_1, set_time_1.getText().toString());
        editor.putString(SAVED_VOLUME_TEXT_1, set_volume_1.getText().toString());
        editor.putBoolean(CHECK_IF_SET_TIME_1, checktime_1);
        editor.putBoolean(CHECK_IF_SET_VOLUME_1, checkvolume_1);
        editor.putBoolean(CHECK_IF_SET_1, checkset_1);
        editor.apply();
    }

    private void getVolumeFor_1() {
        final AlertDialog.Builder volume_alert = new AlertDialog.Builder(MainActivity.this);

        final SeekBar bar = new SeekBar(MainActivity.this);

        final AudioManager au = (AudioManager) getSystemService(AUDIO_SERVICE);
        bar.setMax(au.getStreamMaxVolume(AudioManager.STREAM_RING));
        bar.setProgress(au.getStreamVolume(AudioManager.STREAM_RING));
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selected_volume_1 = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        volume_alert.setTitle(R.string.choice_volume);
        volume_alert.setView(bar);
        volume_alert.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        volume_alert.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                set_volume_1.setText(Integer.toString(selected_volume_1));
                checkvolume_1 = true;
            }
        });
        volume_alert.show();
    }

    private void getTimeFor_1() {
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String textOfTime = check_numbers(hourOfDay, minute);
                set_time_1.setText(textOfTime);
                selected_hour_1 = hourOfDay;
                selected_minute_1 = minute;
                checktime_1 = true;
            }
        }, hour, minute, true);
        tpd.show();
    }

    private String check_numbers(int hour, int minute) {
        String textOfTime;
        switch (minute) {
            case 0:
                minute = 0;
                textOfTime = Integer.toString(hour) + ":0" + Integer.toString(minute);
                break;
            case 1:
                minute = 1;
                textOfTime = Integer.toString(hour) + ":0" + Integer.toString(minute);
                break;
            case 2:
                minute = 2;
                textOfTime = Integer.toString(hour) + ":0" + Integer.toString(minute);
                break;
            case 3:
                minute = 3;
                textOfTime = Integer.toString(hour) + ":0" + Integer.toString(minute);
                break;
            case 4:
                minute = 4;
                textOfTime = Integer.toString(hour) + ":0" + Integer.toString(minute);
                break;
            case 5:
                minute = 5;
                textOfTime = Integer.toString(hour) + ":0" + Integer.toString(minute);
                break;
            case 6:
                minute = 6;
                textOfTime = Integer.toString(hour) + ":0" + Integer.toString(minute);
                break;
            case 7:
                minute = 7;
                textOfTime = Integer.toString(hour) + ":0" + Integer.toString(minute);
                break;
            case 8:
                minute = 8;
                textOfTime = Integer.toString(hour) + ":0" + Integer.toString(minute);
                break;
            case 9:
                minute = 9;
                textOfTime = Integer.toString(hour) + ":0" + Integer.toString(minute);
                break;
            default:
                textOfTime = Integer.toString(hour) + ":" + Integer.toString(minute);
                break;
        }
        return textOfTime;
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