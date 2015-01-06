package com.jeevangadgets.jeevanalarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.DialogFragment;
 
public class DatePickerDialogFragment extends DialogFragment implements OnDateSetListener {
 
	private OnDateSetListener mDateSetListener;
	private TextView mDateView;
 
	public DatePickerDialogFragment() {
		// nothing to see here, move along
	}
 
	public DatePickerDialogFragment(View callingView) {
		mDateSetListener = (OnDateSetListener) this;
		mDateView = (TextView)callingView;
	}
 
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("E, MMM d, yyyy");
		try {
			cal.setTime(format.parse(mDateView.getText().toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		return new DatePickerDialog(getActivity(),
				mDateSetListener, cal.get(Calendar.YEAR), 
				cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
	}


	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
		SimpleDateFormat format = new SimpleDateFormat("E, MMM d, yyyy");
		mDateView.setText(format.format(cal.getTime()));		
	}	
	
	
}