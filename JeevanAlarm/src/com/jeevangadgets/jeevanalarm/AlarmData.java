package com.jeevangadgets.jeevanalarm;

public class AlarmData {
	public String Id;
	public String Name;
	public String On;
	public String RepeatCount;
	public String RepeatFrequency;
	
	public AlarmData() {
		super();
	}
	
	public AlarmData(String id, String name, String on, String repeatCount, String repeatFrequency) {
		super();
		this.Id = id;
		this.Name = name;
		this.On = on;
		this.RepeatCount = repeatCount;
		this.RepeatFrequency = repeatFrequency;
	}
	
	@Override
	public String toString() {
		String display = this.Name + "\nOn " + On;
		if (!RepeatCount.equals("0") && !RepeatCount.equals("") && RepeatCount != null) 
			display += "\nand Repeat every '" + RepeatCount + "' " + RepeatFrequency + " after that";
		return display;
	}
}
