package com.sp.controller;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sp.model.EmployeeShiftDetails;
import com.sp.model.RequestObject;
import com.sp.model.ShiftDetails;
import com.sp.service.FoodDetailsService;
import com.sp.utils.CommonUtils;

/**
 * EmployeeShiftDetailsController.
 */
@Controller
@RequestMapping(value = "/food")
public class EmployeeShiftDetailsController {

	@Autowired
	private FoodDetailsService foodDetailsService;

	private final Logger logger = Logger.getLogger(EmployeeShiftDetailsController.class.getName());

	/**
	 * saveDetails.
	 *
	 * @param data String
	 * @return Map<String, Object> result
	 */
	@RequestMapping(value = "/saveDetails", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveDetails(@RequestBody String data) {
		Map<String, Object> result = new HashMap<String, Object>();
		String errorMsgs = "";
		
		EmployeeShiftDetails employeeShiftDetails = new EmployeeShiftDetails();
		//TODO : Replace with users data
		employeeShiftDetails.setEmpId(220);
		employeeShiftDetails.setEmpName("Sanket Jain");
		
		List<ShiftDetails> newList = new ArrayList<ShiftDetails>();
		
		try {
			Type listType = new TypeToken<ArrayList<RequestObject>>() {}.getType();
			List<RequestObject> requestObjectList = new Gson().fromJson(data, listType);
			
			for (RequestObject object : requestObjectList) {
				
				ShiftDetails shiftDetails = new ShiftDetails();
				
				if (object.getRecordId() > 0) {
					shiftDetails.setRecordId(object.getRecordId());
				}
				
				if (object.getStatus().equalsIgnoreCase(CommonUtils.DELETED)) {
					if (object.getName().equalsIgnoreCase(CommonUtils.FIRST)) {
						shiftDetails.setFirstShift(false);
					} else if (object.getName().equalsIgnoreCase(CommonUtils.SECOND)) {
						shiftDetails.setSecondShift(false);
					} else if (object.getName().equalsIgnoreCase(CommonUtils.ETO)) {
						shiftDetails.setEto(false);
					} else {
						logger.info("Please enter a valid value for object name. I can be " + CommonUtils.FIRST + " / "
								+ CommonUtils.SECOND + " / " + CommonUtils.ETO);
					}
				} else {
					if (object.getName().equalsIgnoreCase(CommonUtils.FIRST)) {
						shiftDetails.setFirstShift(true);
						shiftDetails.setEto(false);
					} else if (object.getName().equalsIgnoreCase(CommonUtils.SECOND)) {
						shiftDetails.setSecondShift(true);
						shiftDetails.setEto(false);
					} else if (object.getName().equalsIgnoreCase(CommonUtils.ETO)) {
						shiftDetails.setEto(true);
						shiftDetails.setFirstShift(false);
						shiftDetails.setSecondShift(false);
					} else {
						logger.info("Please enter a valid value for object name. I can be " + CommonUtils.FIRST + " / "
								+ CommonUtils.SECOND + " / " + CommonUtils.ETO);
					}
				}
				
				shiftDetails.setShiftDate(CommonUtils.getDate(object.getStartDate()));
				shiftDetails.setStatus(object.getStatus());
				shiftDetails.setEmployeeShiftDetails(employeeShiftDetails);
				
				if (object.getStatus().equalsIgnoreCase(CommonUtils.NEW)) {
					ShiftDetails fetchedShiftDetails = foodDetailsService.getShiftDetailsforEmpForDate(shiftDetails);
					if (fetchedShiftDetails == null) {
						newList.add(shiftDetails);
					} else {
						shiftDetails.setRecordId(fetchedShiftDetails.getRecordId());
						if (object.getName().equalsIgnoreCase(CommonUtils.FIRST)) {
							shiftDetails.setSecondShift(fetchedShiftDetails.isSecondShift());
						} else if (object.getName().equalsIgnoreCase(CommonUtils.SECOND)) {
							shiftDetails.setFirstShift(fetchedShiftDetails.isFirstShift());
						}
						foodDetailsService.updateEmpShiftDetails(shiftDetails);
					}
				}  else { // It is either update or deleted
					ShiftDetails fetchedShiftDetails = foodDetailsService.getShiftDetailsforEmpForDate(shiftDetails);
					
					if (fetchedShiftDetails == null) {
						newList.add(shiftDetails);
					} else {
						shiftDetails.setRecordId(fetchedShiftDetails.getRecordId());
						if (object.getName().equalsIgnoreCase(CommonUtils.FIRST)) {
							shiftDetails.setSecondShift(fetchedShiftDetails.isSecondShift());
						} else if (object.getName().equalsIgnoreCase(CommonUtils.SECOND)) {
							shiftDetails.setFirstShift(fetchedShiftDetails.isFirstShift());
						}
						foodDetailsService.updateEmpShiftDetails(shiftDetails);
					}
				}
			}
			
			employeeShiftDetails.setShiftDetailsList(newList);
			
			if (employeeShiftDetails.getShiftDetailsList().size() > 0) {
				for (ShiftDetails newShiftDetails : employeeShiftDetails.getShiftDetailsList()) {
					ShiftDetails fetchedShiftDetails = foodDetailsService.getShiftDetailsforEmpForDate(newShiftDetails);
					if (fetchedShiftDetails != null) {
						newShiftDetails.setRecordId(fetchedShiftDetails.getRecordId());
						if (fetchedShiftDetails.isFirstShift()) {
							newShiftDetails.setFirstShift(true);
						} 
						if (fetchedShiftDetails.isSecondShift()) {
							newShiftDetails.setSecondShift(true);
						}
						foodDetailsService.updateEmpShiftDetails(newShiftDetails);
					} else {
						List<ShiftDetails> nList = new ArrayList<ShiftDetails>();
						nList.add(newShiftDetails);
						employeeShiftDetails.setShiftDetailsList(nList);
						foodDetailsService.saveEmpShiftDetails(employeeShiftDetails);
					}
				}
					
			} else {
				logger.info("No Data available");
			}
			
			logger.info("SUCCESS");
			result.put("operation", "saveDetails");
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			logger.log(Level.SEVERE, "Exception while saving new note", e);
			errorMsgs = e.getMessage();
		}
		result.put("error", errorMsgs);
		return result;
	}
	
	/**
	 * getShiftCount.
	 *
	 * @param data String
	 * @return Map<String, Object> result
	 */
	@RequestMapping(value = "/getShiftCount", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getShiftCount(@RequestBody String data) {
		String errorMsgs = "";
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			ObjectMapper om = new ObjectMapper();
			JsonNode node = om.readTree(data);
			String fromDate = om.convertValue(node.get("fromDate"), String.class);
			String toDate = om.convertValue(node.get("toDate"), String.class);
			
			System.out.println("fromDate : " + fromDate);
			System.out.println("toDate : " + toDate);
			
			if(fromDate == null || toDate == null){
				errorMsgs = "From date ot To date is missing";
				response.put("success", false);
			} else {
				response =  foodDetailsService.getShiftCount(CommonUtils.getDate(fromDate), CommonUtils.getDate(toDate));
				logger.info("SUCCESS");
				response.put("success", true);
			}
		} catch (Exception e) {
			response.put("success", false);
			logger.log(Level.SEVERE, "Exception while saving new note", e);
			errorMsgs = e.getMessage();
		}
		response.put("error", errorMsgs);
		return response;
	}
	
	/**
	 * getShiftCount.
	 *
	 * @param data String
	 * @return Map<String, Object> result
	 */
	@RequestMapping(value = "/getWeeklyShiftCount", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getWeeklyShiftCount(@RequestBody String data) {
		logger.info(" -------------------- getWeeklyShiftCount ----------- ");
		String errorMsgs = "";
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			ObjectMapper om = new ObjectMapper();
			JsonNode node = om.readTree(data);
			String fromDate = om.convertValue(node.get("fromDate"), String.class);
			//String toDate = om.convertValue(node.get("toDate"), String.class);
			
			System.out.println("fromDate : " + fromDate);
			
			if(fromDate == null){
				errorMsgs = "From date is missing";
				response.put("success", false);
			} else {
				Date fDate = CommonUtils.getDate(fromDate);
				
				if (fDate.getDay() != 1) { // Check if not Monday
					response.put("success", true);
					errorMsgs = "One Monday can be selected";
				} else {
					Date[] workWeek = CommonUtils.getWorkWeek(fDate);
					
					Integer[] firstShiftCount = new Integer[5];
					Integer[] secondShiftCount = new Integer[5];
					Integer[] etoCount = new Integer[5];
					Integer[] notFilledCount = new Integer[5];
					for (int i = 0; i < workWeek.length; i++) {
						firstShiftCount[i] =  foodDetailsService.getSpecificShiftCount(workWeek[i], CommonUtils.FIRST_SHIFT); //{15, 16, 18, 74, 25};
						secondShiftCount[i] = foodDetailsService.getSpecificShiftCount(workWeek[i], CommonUtils.SECOND_SHIFT); //{45, 76, 68, 14, 75};
						etoCount[i] = foodDetailsService.getSpecificShiftCount(workWeek[i], CommonUtils.ETO_SHIFT); // {10, 6, 1, 7, 5};
						notFilledCount[i] = foodDetailsService.getSpecificShiftCount(workWeek[i], null);
						//response =  foodDetailsService.getShiftCount(CommonUtils.getDate(fromDate), CommonUtils.getDate(toDate));
						response.put("firstShiftCount", firstShiftCount);
						response.put("secondShiftCount", secondShiftCount);
						response.put("etoCount", etoCount);
						response.put("notFilledCount", notFilledCount);
						logger.info("SUCCESS");
						response.put("success", true);

					}
				}
			}
		} catch (Exception e) {
			response.put("success", false);
			logger.log(Level.SEVERE, "Exception while saving new note", e);
			errorMsgs = e.getMessage();
		}
		response.put("error", errorMsgs);
		return response;
	}
	
	//TODO
	/*
	 * 
	 * POOLS: Think on it
	 * 
	 * Send Email:
	 * <a href="mailto:sjain@behr.com;smody@behr.com?subject=Meals survey
	 * &body=Please complete your meals survey for the next week">example</a>
	 * 
	 * Upload / download excel
	 * Display Menu
	 * 
	 */
	
	/**
	 * getShiftDetailsforEmp.
	 *
	 * @param data String
	 * @return Map<String, Object> result
	 */
	@RequestMapping(value = "/getShiftDetailsforEmp/{empId}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShiftDetailsforEmp(@PathVariable("empId") Integer empId) {
		Map<String, Object> result = new HashMap<String, Object>();
		String errorMsgs = "";
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		try {
			System.out.println("EmpId : " + empId);
						
			EmployeeShiftDetails employeeShiftDetails = foodDetailsService.getShiftDetailsforEmp(empId);
			List<RequestObject> objectList = new ArrayList<RequestObject>();
			int count = 0;
			if (employeeShiftDetails != null) {
				for (ShiftDetails shiftDetails : employeeShiftDetails.getShiftDetailsList()) {
					if (shiftDetails.isFirstShift()) {
						RequestObject object = new RequestObject();
						object.setId(count);
						object.setRecordId(shiftDetails.getRecordId());
						
						object.setName("First");
						object.setColor("#2096BA");
						
						object.setStartDate(format.format(shiftDetails.getShiftDate()));
						object.setEndDate(format.format(shiftDetails.getShiftDate()));
						object.setStatus("updated");
						objectList.add(object);
						count ++;
						
					}
					
					if (shiftDetails.isSecondShift()) {
						RequestObject object = new RequestObject();
						object.setId(count);
						object.setRecordId(shiftDetails.getRecordId());
						
						object.setName("Second");
						object.setColor("#DF6E21");
						
						object.setStartDate(format.format(shiftDetails.getShiftDate()));
						object.setEndDate(format.format(shiftDetails.getShiftDate()));
						object.setStatus("updated");
						objectList.add(object);
						count ++;
					}
					
					if (shiftDetails.isEto()) {
						RequestObject object = new RequestObject();
						object.setId(count);
						object.setRecordId(shiftDetails.getRecordId());
						
						object.setName("ETO");
						object.setColor("#C5919D");
						
						object.setStartDate(format.format(shiftDetails.getShiftDate()));
						object.setEndDate(format.format(shiftDetails.getShiftDate()));
						object.setStatus("updated");
						objectList.add(object);
						count ++;
					}
				}
			}
		    
			logger.info("SUCCESS");
			result.put("operation", "getShiftCount");
			result.put("success", true);
			result.put("success", true);
			result.put("result", objectList);
		} catch (Exception e) {
			result.put("success", false);
			logger.log(Level.SEVERE, "Exception while saving new note", e);
			errorMsgs = e.getMessage();
		}
		result.put("error", errorMsgs);
		return result;
	}

	/**
	 * deleteFoodDetails.
	 *
	 * @param empId
	 *            Integer
	 * @return Map<String, Object> message
	 */
	@RequestMapping(value = "/deleteFoodDetails/{empId}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteFoodDetails(@PathVariable("empId") Integer empId) {
		Map<String, Object> result = new HashMap();
		String errorMsgs = "";
		try {
			foodDetailsService.deleteEmpFooddetails(empId);
			result.put("operation", "deleteFoodDetails");
			result.put("success", "Success");
		} catch (Exception e) {
			errorMsgs = e.getMessage();
			logger.log(Level.SEVERE, "Unable to delete note", e);
		}
		result.put("error", errorMsgs);
		return result;
	}
}
