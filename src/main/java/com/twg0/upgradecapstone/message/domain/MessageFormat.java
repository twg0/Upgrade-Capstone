package com.twg0.upgradecapstone.message.domain;

public enum MessageFormat {

	MESSAGE_TYPE(0),
	DEVICE_ID(1),
	TITLE(2),
	PHONE_NUMBER(2),
	REPLY_KIND(3),
	TEMPERATURE(2),
	HUMIDITY(3),
	FILE_ID(4),
	VIBRATION(4),
	GASLEAK(5),
	ABNORMALNESS(6);

	private final int index;

	MessageFormat(int index) {
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}
}
