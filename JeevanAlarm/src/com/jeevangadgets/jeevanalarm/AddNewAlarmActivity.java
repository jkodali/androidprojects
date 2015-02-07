package com.jeevangadgets.jeevanalarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.jeevangadgets.jeevanalarm.AlarmDBContract.AlarmList;

public class AddNewAlarmActivity extends Activity {
	
	protected String alarmId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_alarm);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_alarm, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_cancel) {
			setResult(RESULT_OK, null);
			super.finish();
		}
		else if (id == R.id.action_accept) {
			AlarmDBHelper dbHelper = new AlarmDBHelper(this);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			
			TextView view = (TextView)findViewById(R.id.edit_name);
			String alarmName = view.getText().toString();
			values.put(AlarmList.COLUMN_NAME_ALARM_NAME, alarmName);
			
			view = (TextView)findViewById(R.id.date);
			String date = view.getText().toString();
			view = (TextView)findViewById(R.id.time);
			String time = view.getText().toString();
			values.put(AlarmList.COLUMN_NAME_ALARM_ON, date + " " + time);
			
			view = (TextView)findViewById(R.id.repeat_value);
			values.put(AlarmList.COLUMN_NAME_REPEAT_COUNT, view.getText().toString());
			
			Spinner spinner = (Spinner)findViewById(R.id.repeat_frequency);
			String frequency = spinner.getSelectedItem().toString();
			values.put(AlarmList.COLUMN_NAME_REPEAT_FREQUENCY, frequency);
			
			if (alarmId == "")
			{
				long addedAlarmId = db.insert(AlarmList.TABLE_NAME, null, values);
				alarmId = String.valueOf(addedAlarmId);
			}
			else{
				String selection = AlarmList._ID + " LIKE ?";
				String[] selectionArgs = { String.valueOf(alarmId) };
				
				db.update(AlarmList.TABLE_NAME, values, selection, selectionArgs);
			}

			Intent msgIntent = new Intent(this, AlarmService.class);
			msgIntent.putExtra("AlarmId", alarmId);
			msgIntent.putExtra("AlarmName", alarmName);
			msgIntent.setAction("Create");
			startService(msgIntent);
			super.finish();
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

			Bundle extras = getActivity().getIntent().getExtras();
			if (extras != null)
				((AddNewAlarmActivity)getActivity()).alarmId = extras.getString("AlarmId");
			
			TextView view = null;
			if (((AddNewAlarmActivity)getActivity()).alarmId == "") {
				Calendar today = Calendar.getInstance();
				SimpleDateFormat format = new SimpleDateFormat("E, MMM d, yyyy", Locale.US);
				view = (TextView)rootView.findViewById(R.id.date);
				view.setText(format.format(today.getTime()));
				
				view = (TextView)rootView.findViewById(R.id.time);
				format = new SimpleDateFormat("hh:mm a", Locale.US);
				view.setText(format.format(today.getTime()));
			}
			else {
				AlarmDBHelper dbHelper = new AlarmDBHelper(getActivity());
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				Cursor cursor = db.rawQuery("Select * from AlarmList where _id = " + ((AddNewAlarmActivity)getActivity()).alarmId, null);
				if (cursor != null) {
					if (cursor.moveToFirst()) {
						view = (TextView)rootView.findViewById(R.id.edit_name);
						view.setText(cursor.getString(AlarmList.COLUMN_INDEX_ALARM_NAME));
						
						try {
							SimpleDateFormat fullDateFormat = new SimpleDateFormat("E, MMM d, yyyy hh:mm a", Locale.US);
							SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM d, yyyy", Locale.US);
							SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
							Date on = fullDateFormat.parse(cursor.getString(AlarmList.COLUMN_INDEX_ALARM_ON));
							view = (TextView)rootView.findViewById(R.id.date);
							view.setText(dateFormat.format(on));
							
							view = (TextView)rootView.findViewById(R.id.time);
							view.setText(timeFormat.format(on));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						view = (TextView)rootView.findViewById(R.id.repeat_value);
						view.setText(cursor.getString(AlarmList.COLUMN_INDEX_REPEAT_COUNT));
						
						Spinner spinner = (Spinner)rootView.findViewById(R.id.repeat_frequency);
						String frequency = cursor.getString(AlarmList.COLUMN_INDEX_REPEAT_FREQUENCY);
						SpinnerAdapter adapter = spinner.getAdapter();
						int index = 0;
						for(int i = 0; i < adapter.getCount(); i++)
							if (adapter.getItem(i).toString().equals(frequency)) {
								index = i;
								break;
							}
						spinner.setSelection(index); 
					}
				}
			}
			
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
