package com.sp.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainFoodDetails {

	/*
	 * public static void main(String[] args) { Date date =
	 * getDate("09-12-2018"); System.out.println(date.getDay()); }
	 * 
	 * public static Date getDate (String dateString) { //String dateString =
	 * "2018-07-29T18:30:00.000Z"; try { DateFormat inputFormat = new
	 * SimpleDateFormat("dd-MM-yyyy"); Date date =
	 * inputFormat.parse(dateString);
	 * 
	 * //SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
	 * //System.out.println(format1.format(date)); return date; } catch
	 * (Exception e) { System.out.println("Invalid date format"); } return null;
	 * }
	 */

	public static Date newDate(Date preDate) {
		Date nDate = new Date();
		// nDate = preDate;
		// int newDate = nDate.getDate();
		// System.out.println("newDate : " + newDate);
		nDate.setDate(preDate.getDate() + 1);
		nDate.setMonth(preDate.getMonth());
		nDate.setYear(preDate.getYear());
		return nDate;
	}

	/*
	 * public static Date newDate(Date preDate){ Date nDate = new Date(); nDate
	 * = preDate; nDate.setDate(preDate.getDate()+1); return nDate; Date nDate =
	 * new Date(); //nDate = preDate; //int newDate = nDate.getDate();
	 * //System.out.println("newDate : " + newDate);
	 * nDate.setDate(preDate.getDate() + 1); nDate.setMonth(preDate.getMonth());
	 * nDate.setYear(preDate.getYear()); return nDate; }
	 */

	public static Date nextDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		// Add one to month {0 - 11}
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String dateString = day + "-" + month + "-" + year;
		System.out.println("dateString : " + dateString);
		return CommonUtils.getDate(dateString);
	}

	public static void main(String[] args) throws ParseException {
		String dateString = "29-01-2018";
		DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date today = inputFormat.parse(dateString);
		Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
		System.out.println(today);
		System.out.println(tomorrow);
		// Initialize your Date however you like it.
		// Date date = new Date();
		/*String dateString = "29-01-2018";
		DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = inputFormat.parse(dateString);

		Date[] workWeek = new Date[5];

		workWeek[0] = date;
		for (int i = 1; i < 5; i++) {
			workWeek[i] = nextDate(workWeek[i - 1]);
		}*/

		/*
		 * String dateString = "29-01-2018"; DateFormat inputFormat = new
		 * SimpleDateFormat("dd-MM-yyyy"); Date date =
		 * inputFormat.parse(dateString); System.out.println("Date ::: " +
		 * date); String dateString1 = date.getDate() + "-" + date.getMonth()+1
		 * + "-" + date.getYear(); System.out.println("dateString1 : ----- " +
		 * dateString1); Date[] workWeek = new Date[5];
		 * 
		 * workWeek[0] = date; for (int i = 1; i < 5; i++) { workWeek[i] =
		 * nextDate(workWeek[i-1]); }
		 */
		// return workWeek;

		/*
		 * List<Date> workweek = new ArrayList<Date>(); Date[] ww = new Date[5];
		 * 
		 * String dateString = "29-01-2018"; DateFormat inputFormat = new
		 * SimpleDateFormat("dd-MM-yyyy"); Date date =
		 * inputFormat.parse(dateString); Date ndate = newDate(date); //ndate =
		 * newDate(date);
		 * 
		 * System.out.println(date); System.out.println(ndate);
		 * workweek.add(date); ww[0] = date; for (int i = 1; i < 5; i++) {
		 * workweek.add(newDate(workweek.get(i-1))); ww[i] = newDate(ww[i-1]); }
		 * for (int i = 0; i < 5; i++) {
		 * System.out.println("-----------------");
		 * System.out.println(workweek.get(i)); System.out.println(ww[i]); }
		 * System.out.println(workweek.size());
		 */
		/*
		 * System.out.println("date 1 : " + newDate(date)); System.out.println(
		 * "date 2 : " + newDate(date)); System.out.println("date 3 : " +
		 * newDate(date)); System.out.println("date 4 : " + newDate(date));
		 * System.out.println("date 5 : " + newDate(date));
		 */
	}
	/*
	 * String dateString = "2018-07-29T18:30:00.000Z"; DateFormat dateFormat =
	 * new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); Date date =
	 * dateFormat.parse(dateString.split("\\.")[0]);//You will get date object
	 * relative to server/client timezone wherever it is parsed
	 * 
	 * System.out.println(date);
	 * 
	 * Date date = null; try { date = inputFormat.parse(dateString);
	 * System.out.println("date: " + date); } catch (ParseException e) {
	 * System.out.println("Invalid date format"); }
	 * 
	 * String dateString =
	 * "Tue Jun 26 2018 00:00:00 GMT+0530 (India Standard Time)"; DateFormat
	 * inputFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z",
	 * Locale.ENGLISH); Date date = inputFormat.parse(dateString);
	 * 
	 * System.out.println("Date111: " + date);
	 * 
	 * SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
	 * System.out.println(format1.format(date));
	 * 
	 * 
	 * 
	 * 
	 * Date date = new Date("2018-06-22T13:28:06.419Z");
	 * System.out.println(date.getDate());
	 * 
	 * String string = "2018-07-03T18:30:00.000Z"; DateFormat format = new
	 * SimpleDateFormat("yyyyy-MM-dd'T'HH:mm:ss.sssZ"); Date date =
	 * format.parse(string.replaceAll("Z$", "+0000"));
	 * 
	 * 
	 * SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
	 * System.out.println(format1.format(date));
	 * 
	 * ShiftDetails shiftDetails1 = new ShiftDetails();
	 * shiftDetails1.setShiftDate("25-06-2018");
	 * shiftDetails1.setFirstShift(true); shiftDetails1.setSecondShift(false);
	 * 
	 * ShiftDetails shiftDetails2 = new ShiftDetails();
	 * shiftDetails2.setShiftDate("26-06-2018");
	 * shiftDetails2.setFirstShift(true); shiftDetails2.setSecondShift(false);
	 * 
	 * List<ShiftDetails> shiftDetails = new ArrayList<ShiftDetails>();
	 * shiftDetails.add(shiftDetails1); shiftDetails.add(shiftDetails2);
	 * 
	 * EmployeeShiftDetails employeeShiftDetails = new EmployeeShiftDetails();
	 * employeeShiftDetails.setEmpId("220"); employeeShiftDetails.setEmpName(
	 * "Sanket Jain"); employeeShiftDetails.setShiftDetailsList(shiftDetails);
	 * 
	 * Gson gson = new Gson(); String json = gson.toJson(employeeShiftDetails);
	 * System.out.println("Json : " + json);
	 * 
	 * // {"empId":"220","empName":"Sanket //
	 * Jain","shiftDetailsList":[{"shiftDate":"25-06-
	 * 2018","firstShift":true,"secondShift":false},{"shiftDate":"26-06-
	 * 2018","firstShift":true,"secondShift":false}]} }
	 * 
	 */}
