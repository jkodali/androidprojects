package com.jeevangadgets.jeevanalarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AddNewAlarmActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_alarm);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_alarm, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_new_alarm,
					container, false);

			Calendar today = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("E, MMM d, yyyy");
			TextView view = (TextView)rootView.findViewById(R.id.date);
			view.setText(format.format(today.getTime()));
			
			view = (TextView)rootView.findViewById(R.id.time);
			format = new SimpleDateFormat("hh:mm a");
			view.setText(format.format(today.getTime()));
			
			return rootView;
		}
	}

	 public void showDatePickerDialog(View v) {
	        DialogFragment newFragment = new DatePickerDialogFragment(v);
	        newFragment.show(getFragmentManager(), "datePicker");
	 }

	 public void showTimePickerDialog(View v) {
	        DialogFragment newFragment = new TimePickerDialogFragment(v);
	        newFragment.show(getFragmentManager(), "datePicker");
	 }

}
