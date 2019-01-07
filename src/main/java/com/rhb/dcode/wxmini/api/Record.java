package com.rhb.dcode.wxmini.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Record {
	String openId;
	Double second;
	String datetime;

	public Record(String openId, String second) {
		this.openId = openId;
		this.second = Double.parseDouble(second);
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime time = LocalDateTime.now();
		this.datetime = df.format(time);
	}
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Double getSecond() {
		return second;
	}
	public void setSecond(Double second) {
		this.second = second;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	@Override
	public String toString() {
		return "Record [openId=" + openId + ", second=" + second + ", datetime=" + datetime + "]";
	}
}
