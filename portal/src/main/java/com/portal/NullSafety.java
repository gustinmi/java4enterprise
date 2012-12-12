package com.portal;

public class NullSafety {
	public static boolean isset(String value) {
		return !emptyStr(value);
	}

	public static boolean emptyStr(String value) {
		return value == null || value.length() == 0;
	}

	public static boolean notEmptyStr(String value) {
		return !emptyStr(value);
	}

}
