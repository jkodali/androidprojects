package com.jeevangadgets.jeevanalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {   
		long id = intent.getLongExtra("id", 0);
        String msg = intent.getStringExtra("msg");
    
        PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(), 0);

        Notification n = new Notification.Builder(context)
        .setContentTitle("Alarm")
        .setContentText(msg)
        .setSmallIcon(R.drawable.ic_launcher)
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
        nm.notify((int)id, n);	
	}

}
