package com.jeevangadgets.jeevanalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent incomingIntent) {   
		Integer id = Integer.parseInt(incomingIntent.getStringExtra("AlarmId"));
        String name = incomingIntent.getStringExtra("AlarmName");
    
        Intent intent = new Intent(context, ShowAlarm.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("AlarmName", name);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);

        Notification n = new Notification.Builder(context)
        //.setContentTitle("Alarm")
        //.setContentText(name)
        //.setSmallIcon(R.drawable.ic_launcher)
        .setFullScreenIntent(pi, true)
        .setWhen(System.currentTimeMillis())
        .setContentIntent(pi)
        .build();
        
        
        // TODO check user preferences
        n.defaults |= Notification.DEFAULT_VIBRATE;
        //n.sound = Uri.parse(RemindMe.getRingtone());
//      n.defaults |= Notification.DEFAULT_SOUND;       
        n.flags |= Notification.FLAG_AUTO_CANCEL;       
         
        NotificationManager nm = (NotificationManager) 
                                    context.getSystemService(Context.NOTIFICATION_SERVICE);
        //nm.notify((int)id, n);
        context.startActivity(intent);
	}

}
