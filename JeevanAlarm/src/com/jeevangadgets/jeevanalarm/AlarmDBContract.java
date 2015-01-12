package com.jeevangadgets.jeevanalarm;

import android.provider.BaseColumns;

public class AlarmDBContract {
	
	public AlarmDBContract() {}
	
	public static abstract class AlarmList implements BaseColumns {
		public static final String TABLE_NAME = "AlarmList";
		public static final String COLUMN_NAME_ALARM_NAME = "AlarmName";
		public static final String COLUMN_NAME_ALARM_ON = "AlarmOn";
		public static final String COLUMN_NAME_REPEAT_COUNT = "RepeatCount";
		public static final String COLUMN_NAME_REPEAT_FREQUENCY = "RepeatFrequency";

		public static final int COLUMN_INDEX_ID = 0;
		public static final int COLUMN_INDEX_ALARM_NAME = 1;
		public static final int COLUMN_INDEX_ALARM_ON = 2;
		public static final int COLUMN_INDEX_REPEAT_COUNT = 3;
		public static final int COLUMN_INDEX_REPEAT_FREQUENCY = 4;
	}

}
