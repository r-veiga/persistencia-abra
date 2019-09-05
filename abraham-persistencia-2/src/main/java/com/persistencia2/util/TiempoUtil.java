package com.persistencia2.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TiempoUtil {
	
	private static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public static Timestamp ahora() {
		Timestamp ahora = new Timestamp((new Date()).getTime());
		return ahora;
	}
}
