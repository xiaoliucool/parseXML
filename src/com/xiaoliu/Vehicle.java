package com.xiaoliu;
/** 
 * @author  ���� E-mail: xiaoliucool@126.com
 * @date    ����ʱ�䣺2015��12��6�� ����12:38:44 
 * @version 1.0  
 */
public class Vehicle {
	String sender ;
	String longitude ;
	String latitude ;
	String dateTime;
	String seconds;
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getSeconds() {
		return seconds;
	}
	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
}
