package com.sp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.sp.model.ShiftDetails;

public class CommonUtils {
	
	private static final Logger logger = Logger.getLogger(CommonUtils.class.getName());
	
	public static final String FIRST = "First";
	public static final String SECOND = "Second";
	public static final String ETO = "ETO";
	
	public static final String FIRST_SHIFT = "first_shift";
	public static final String SECOND_SHIFT = "second_shift";
	public static final String ETO_SHIFT = "eto";
	public static final String NOT_FILLED = "not_filled";

	public static final String NEW = "New";
	public static final String UPDATED = "Updated";
	public static final String DELETED = "Deleted";

	public static Date getDate (String dateString) {
		try {
			DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date date = inputFormat.parse(dateString);
			
			return date;
		} catch (Exception e) {
			logger.info("Invalid date format");
		}
		return null;
	}
	
	public static Map<String, Object> getShiftDetailsCount (List<ShiftDetails> shiftDetailsList ) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Integer firstShiftCount = 0;
		Integer secondShiftCount = 0;
		Integer etoCount = 0;
		
		for (ShiftDetails shiftDetails : shiftDetailsList) {
			System.out.println("shiftDetails : " + shiftDetails.toString());
			if (shiftDetails.isFirstShift()) {
				firstShiftCount++;
			}
			if (shiftDetails.isSecondShift()) {
				secondShiftCount++;
			}
			if (shiftDetails.isEto()) {
				etoCount++;
			}
		}
		result.put(FIRST, firstShiftCount);
		result.put(SECOND, secondShiftCount);
		result.put(ETO, etoCount);
		return result; 
	}
	
	public static Date nextDate(Date preDate){
		return new Date(preDate.getTime() + (1000 * 60 * 60 * 24));
	}
	
	public static Date[] getWorkWeek(Date date) {
		//TODO add  a check that only monday is passed 
		Date[] workWeek = new Date[5];
		
		workWeek[0] = date;
		for (int i = 1; i < 5; i++) {
			workWeek[i] = nextDate(workWeek[i-1]);
		}
		return workWeek;
	}
	
}
