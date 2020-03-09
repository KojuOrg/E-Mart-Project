package services;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
	public static void main(String[] args) throws Exception{
		String date1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String date2 = "2020-03-07";

		Date date = new SimpleDateFormat("MM/dd/yyyy").parse(date2);
		System.out.println(new Date().before(date));
	}
}
