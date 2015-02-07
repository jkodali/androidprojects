package com.jeevangadgets.jeevanalarm;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

public class ShowAlarm extends Activity {
	Vibrator vib = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_alarm);
        
        Bundle extras = this.getIntent().getExtras();
        String alarmName = extras.getString("AlarmName");

        TextView view = (TextView) findViewById(R.id.alarm_name);
        view.setText(alarmName);
        
        vib = (Vibrator)this.getSystemService(VIBRATOR_SERVICE);
    	vib.vibrate(10000);
    }

    public void OnCancel(View view) {
    	vib.cancel();
    	super.finish();
    }
}
