package com.jprod.jerryletehen.setyourvolumesimple;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.support.v4.app.NotificationCompat;

public class SetVolume_1 extends BroadcastReceiver {
    SharedPreferences sharedPreferences;

    private static boolean notification_b = false;
    private static boolean voice_call_b = false;
    private static boolean system_b = false;

    final String NOTIFICATION = "notification";
    final String VOICE_CALL = "voice_call";
    final String SYSTEM = "system";

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences("MainActivity", Context.MODE_PRIVATE);
        int volume = sharedPreferences.getInt("Selected_Volume_1", 0);

        notification_b = sharedPreferences.getBoolean(NOTIFICATION, notification_b);
        voice_call_b = sharedPreferences.getBoolean(VOICE_CALL, voice_call_b);
        system_b = sharedPreferences.getBoolean(SYSTEM, system_b);


        // Set volume
        final AudioManager au = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        au.getStreamVolume(AudioManager.STREAM_RING);
        au.getStreamMaxVolume(AudioManager.STREAM_RING);
        au.setStreamVolume(AudioManager.STREAM_RING, volume, volume);

        if (notification_b){
            au.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
            au.setStreamVolume(AudioManager.STREAM_NOTIFICATION, volume, volume);
        }
        if (voice_call_b){
            au.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
            au.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
            au.setStreamVolume(AudioManager.STREAM_VOICE_CALL, volume, volume);
        }
        if (system_b){
            au.getStreamVolume(AudioManager.STREAM_SYSTEM);
            au.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
            au.setStreamVolume(AudioManager.STREAM_SYSTEM, volume, volume);
        }
        // Create notification
        String app_name = context.getResources().getString(R.string.app_name);
        String Volume_changed = context.getResources().getString(R.string.Volume_changed);

        Intent back_to_app = new Intent(context, MainActivity.class);
        back_to_app.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pending_to_app = PendingIntent.getActivity(context, 23, back_to_app, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pending_to_app);
        builder.setContentTitle(app_name);
        builder.setContentText(Volume_changed + volume);
        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setAutoCancel(true);
        notificationManager.notify(23, builder.build());
    }
}