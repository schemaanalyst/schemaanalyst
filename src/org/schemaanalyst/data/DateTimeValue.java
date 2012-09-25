package org.schemaanalyst.data;

import java.util.List;

public class DateTimeValue extends DateValue {

	private static final long serialVersionUID = -4217523964688850270L;

	protected NumericValue hour, minute, second;
	
	public DateTimeValue() {
		this(0, 0, 0, 0, 0, 0);
	}
	
	public DateTimeValue(int year, int month, int day, int hour, int minute, int second) {
		super(year, month, day);
		this.hour = new NumericValue(hour, 0, 23);
		this.minute = new NumericValue(minute, 0, 59);
		this.second = new NumericValue(second, 0, 59);		
	}
	
	public NumericValue getHour() {
		return hour;
	}
	
	public void setHour(int hour) {
		this.hour.set(hour);
	}
	
	public NumericValue getMinute() {
		return minute;
	}
	
	public void setMinute(int minute) {
		this.minute.set(minute);
	}	
	
	public NumericValue getSecond() {
		return second;
	}	
	
	public void setSecond(int second) {
		this.second.set(second);
	}		
	
	public List<Value> getElements() {
		List<Value> elements = super.getElements();
		elements.add(hour);
		elements.add(minute);
		elements.add(second);
		return elements;
	}		
	
	public void accept(ValueVisitor valueVisitor) {
		valueVisitor.visit(this);
	}	
	
	public DateTimeValue duplicate() {
		DateTimeValue duplicate = new DateTimeValue();
		duplicate.year = year.duplicate();
		duplicate.month = month.duplicate();
		duplicate.day = day.duplicate();
		duplicate.hour = hour.duplicate();
		duplicate.minute = minute.duplicate();
		duplicate.second = second.duplicate();		
		return duplicate;
	}	

	public int compareTo(Value v) { 
		if (getClass() != v.getClass()) {
			throw new DataException(
				"Cannot compare DateTimeValues to a " + v.getClass());
		}
		
		DateTimeValue other = (DateTimeValue) v;
		int result = year.compareTo(other.year);
		if (result == 0) {
			result = month.compareTo(other.month);
			if (result == 0) {
				result = day.compareTo(other.day);
				if (result == 0) {
					result = hour.compareTo(other.hour);
					if (result == 0) {
						result = minute.compareTo(other.minute);
						if (result == 0) {
							result = second.compareTo(other.second);
						}
					}
				}
			}
		}
		return result;
	}		
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		DateTimeValue other = (DateTimeValue) obj;
		return hour.equals(other.hour)
				&& minute.equals(other.minute)
				&& second.equals(other.second);
	}	
	
	public String toString() {
		return String.format(
				"'%04d-%02d-%02d %02d:%02d:%02d'", 
				year.get().intValue(),
				month.get().intValue(),
				day.get().intValue(),	
				hour.get().intValue(),
				minute.get().intValue(),
				second.get().intValue());					
	}
}
