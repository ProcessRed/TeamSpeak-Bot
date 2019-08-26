package de.processred.ts3;

public class TimeValueItem {
	
	public Object Item;
	private long _time;
	
	
	public void setTimeToCurrent() { 
		_time = System.currentTimeMillis();
	}
	
	public void setTime(long value) { 
		_time = value;
	}
	
	public long getTime() { 
		return _time;
	}
	
}
