package com.jeevangadgets.jeevanalarm;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;

import com.jeevangadgets.jeevanalarm.AlarmDBContract.AlarmList;

public class MainActivity extends ListActivity implements OnItemClickListener, OnItemLongClickListener {

	private ActionMode mActionMode = null;
	private AlarmData longClickedAlarmData = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadListView();
        getListView().setOnItemLongClickListener(this);
    }
    
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.main_activity_context_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    deleteAlarm();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                case R.id.action_edit:
                	openNewAlarm(longClickedAlarmData);
                	mode.finish();
                	return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };
    
    public void LoadListView() {
		AlarmDBHelper dbHelper = new AlarmDBHelper(this);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("Select * from " + AlarmList.TABLE_NAME, null);
		ArrayList<AlarmData> results = new ArrayList<AlarmData>();
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					AlarmData data = new AlarmData();
					data.Id = cursor.getString(AlarmList.COLUMN_INDEX_ID);
					data.Name = cursor.getString(AlarmList.COLUMN_INDEX_ALARM_NAME);
					data.On = cursor.getString(AlarmList.COLUMN_INDEX_ALARM_ON);
					data.RepeatCount = cursor.getString(AlarmList.COLUMN_INDEX_REPEAT_COUNT);
					data.RepeatFrequency = cursor.getString(AlarmList.COLUMN_INDEX_REPEAT_FREQUENCY);
					results.add(data);
				} while(cursor.moveToNext());
			}
		}
		
		setListAdapter(new ArrayAdapter<AlarmData>(this, android.R.layout.simple_list_item_1, results));
		getListView().setTextFilterEnabled(true);
		getListView().setOnItemClickListener(this);
    }
    
    protected void deleteAlarm() {
		// TODO Auto-generated method stub
		Intent msgIntent = new Intent(this, AlarmService.class);
		msgIntent.putExtra("AlarmId", longClickedAlarmData.Id);
		msgIntent.putExtra("AlarmName", longClickedAlarmData.Name);
		msgIntent.setAction("Cancel");
		startService(msgIntent);
		
		AlarmDBHelper dbHelper = new AlarmDBHelper(this);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String selection = AlarmList._ID + " LIKE ?";
		String[] selectionArgs = { String.valueOf(longClickedAlarmData.Id) };
		db.delete(AlarmList.TABLE_NAME, selection, selectionArgs);

	    LoadListView();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_new:
                openNewAlarm(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
	public void openNewAlarm(AlarmData data) {
    	Intent intent = new Intent(this, AddNewAlarmActivity.class);
    	if (data != null) intent.putExtra("AlarmId", data.Id);
    	startActivityForResult(intent, 1);
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		LoadListView();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (mActionMode != null)
			mActionMode.finish();
		AlarmData data = (AlarmData)parent.getAdapter().getItem(position);
		openNewAlarm(data);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
        if (mActionMode != null) {
            return false;
        }

        // Start the CAB using the ActionMode.Callback defined above
        mActionMode = this.startActionMode(mActionModeCallback);
        view.setSelected(true);
		longClickedAlarmData = (AlarmData)parent.getAdapter().getItem(position);
        return true;
	}

}