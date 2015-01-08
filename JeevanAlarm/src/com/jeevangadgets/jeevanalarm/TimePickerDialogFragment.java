package com.jeevangadgets.jeevanalarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.DialogFragment;
 
public class TimePickerDialogFragment extends DialogFragment implements OnTimeSetListener {
 
	private OnTimeSetListener mTimeSetListener;
	private TextView mDateView;
 
	public TimePickerDialogFragment() {
		// nothing to see here, move along
	}
 
	public TimePickerDialogFragment(View callingView) {
		mTimeSetListener = (OnTimeSetListener) this;
		mDateView = (TextView)callingView;
	}
 
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.US);
		try {
			cal.setTime(format.parse(mDateView.getText().toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		return new TimePickerDialog(getActivity(),
				mTimeSetListener, cal.get(Calendar.HOUR_OF_DAY), 
				cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		Calendar cal = new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, hourOfDay, minute);
		SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.US);
		mDateView.setText(format.format(cal.getTime()));		
		
	}	
	
	
}