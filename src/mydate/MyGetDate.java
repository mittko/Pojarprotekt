package mydate;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyGetDate {

	// return mydate from type String
	public static String getSystemDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");// ("yyyy.MM.dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getReversedSystemDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");// ("yyyy.MM.dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getTimeStamp() {
		Date date = new Date();
		return new Timestamp(date.getTime()).toString().replaceAll(":", "-");
	}

	/*
	 * public static String getPointDate() { DateFormat dateFormat = new
	 * SimpleDateFormat("dd.MM.yyyy");//("yyyy.MM.dd"); mydate date = new mydate();
	 * return dateFormat.format(date); }
	 */
	public static String getDate_Days_Hours() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// ("yyyy.MM.dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	// return mydate from type mydate with some type SimpleDateFormat
	public static Date getSystemDate2() {
		DateFormat sdf = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static String getDateAfterAnotherDate(int days, Date helpDate) {
		Calendar gregorian = new GregorianCalendar();
		gregorian.setTime(helpDate);
		gregorian.add(Calendar.DAY_OF_YEAR, days);
		Date date2 = gregorian.getTime();

		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return dateFormat.format(date2);
	}

	public static String getDateBeforeAnotherDate(int days, Date helpDate) {
		Calendar gregorian = new GregorianCalendar();
		gregorian.setTime(helpDate);
		gregorian.add(Calendar.DAY_OF_YEAR, -1 * days);
		Date date2 = gregorian.getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return dateFormat.format(date2);
	}

	public static String getDateAfterToday(int days) {

		Calendar c = Calendar.getInstance(); // starts with today's date and
												// time
		c.add(Calendar.DAY_OF_YEAR, days); // advances day by 2
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = c.getTime();
		return dateFormat.format(date);
	}

	public static String getDifference(Date startDate, Date endDate) {

		// milliseconds
		long different = endDate.getTime() - startDate.getTime();

		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;

		/*
		 * long elapsedHours = different / hoursInMilli; different = different %
		 * hoursInMilli;
		 * 
		 * long elapsedMinutes = different / minutesInMilli; different =
		 * different % minutesInMilli;
		 * 
		 * long elapsedSeconds = different / secondsInMilli;
		 */

		String days = String.format("%d", elapsedDays);
		return days;

	}

	public static Date getDateFromString(String dateString) {
		/*
		 * SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		 * SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		 * SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		 * SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy");
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date date = null;
		try {

			date = sdf.parse(dateString);

			/*
			 * System.out.println(date); System.out.println(sdf.format(date));
			 */
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getUrgentDays(String startDate, String endDate) {
		return getDifference(getDateFromString(startDate),
				getDateFromString(endDate));
	}

	public static String getYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");// ("yyyy.MM.dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static int getYearFromString(String currDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = dateFormat.parse(currDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static String addYear(int years) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, years);
		return c.get(Calendar.DATE) + "." + (c.get(Calendar.MONTH) + 1) + "."
				+ c.get(Calendar.YEAR);
	}

	public static String getYearAfterYears(int years) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, years);
		return c.get(Calendar.YEAR) + "";
	}

	public static String getCurrentYear() {
		Date datetime = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(datetime);
		return cal.get(Calendar.YEAR) + "";
	}

	public static String getCurrentMonth() {
		Date datetime = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(datetime);
		return (cal.get(Calendar.MONTH) + 1) + "";
	}

	public static String getCurrentDay() {
		Date datetime = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(datetime);
		return cal.get(Calendar.DAY_OF_MONTH) + "";
	}

	public static int getDaysFromCurrentMonth() {
		Calendar c = Calendar.getInstance();
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);

	}

	/*
	 * public static String substractYears(int years,String date) { Calendar c =
	 * Calendar.getInstance(); SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd"); try { c.setTime(sdf.parse(date)); } catch
	 * (ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }// all done c.add(Calendar.YEAR,(years * -1));
	 * return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" +
	 * c.get(Calendar.DATE); }
	 */


	public static String generateFiskalBonNumber() {
		DateFormat dateFormat = new SimpleDateFormat("yy.MM.dd.HH.mm.ss");// ("yyyy.MM.dd");
		Date date = new Date();
		return dateFormat.format(date).replace(".", "");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(MyGetDate.getCurrentYear());
		System.out.println(MyGetDate.getYearAfterYears(1));
	}

}
