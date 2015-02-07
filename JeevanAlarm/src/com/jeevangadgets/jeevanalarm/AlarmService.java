package com.jeevangadgets.jeevanalarm;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmService extends IntentService {
	
	public AlarmService() {
		super("AlarmService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent incomingIntent) {
		// TODO Auto-generated method stub
		AlarmManager alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
		Bundle bundle = incomingIntent.getExtras();
		Intent intent = new Intent(AlarmService.this, AlarmReceiver.class);
		intent.putExtra("AlarmId", bundle.getString("AlarmId"));
		intent.putExtra("AlarmName", bundle.getString("AlarmName"));
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		if (incomingIntent.getAction() == "Cancel")
			alarmMgr.cancel(alarmIntent);
		else
			alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(1000), 10000, alarmIntent);
	}

}
