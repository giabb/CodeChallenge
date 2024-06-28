package org.challenge.batch.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

	public static String getCurrentDate() {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return currentDate.format(formatter);
	}

}
