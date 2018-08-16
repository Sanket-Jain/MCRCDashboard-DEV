package com.sp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.dao.FoodDetailsDao;
import com.sp.model.EmployeeShiftDetails;
import com.sp.model.ShiftDetails;
import com.sp.utils.CommonUtils;

/**
 * FoodDetailsService.
 */
@Service
public class FoodDetailsService {

	@Autowired
	private FoodDetailsDao foodDetailsDao;

	private final Logger logger = Logger.getLogger(FoodDetailsService.class.getName());

	/**
	 * saveEmpShiftDetails.
	 *
	 * @param employeeShiftDetails EmployeeShiftDetails
	 */
	public void saveEmpShiftDetails(EmployeeShiftDetails employeeShiftDetails) {
		/*for (ShiftDetails sd : employeeShiftDetails.getShiftDetailsList()) {
			sd.setEmployeeShiftDetails(employeeShiftDetails);
		}*/
		foodDetailsDao.saveUpdateEmpShiftDetails(employeeShiftDetails);
	}

	/**
	 * updateEmpShiftDetails.
	 *
	 * @param shiftDetails ShiftDetails
	 */
	public void updateEmpShiftDetails(ShiftDetails shiftDetails) {
		foodDetailsDao.updateEmpShiftDetails(shiftDetails);
	}
	
	/**
	 * getShiftCount.
	 *
	 * @param fromDate Date
	 * @param toDate Date
	 */
	public Map<String, Object> getShiftCount(Date fromDate, Date toDate) {
		List<ShiftDetails> shiftDetailsList =  foodDetailsDao.getShiftCount(fromDate, toDate);
		System.out.println("returning result : " + shiftDetailsList.size());
		return CommonUtils.getShiftDetailsCount(shiftDetailsList);
	}
	
	/**
	 * getShiftDetailsforEmp.
	 *
	 * @param empId String
	 */
	public EmployeeShiftDetails getShiftDetailsforEmp(Integer empId) {
		return foodDetailsDao.getShiftDetailsforEmp(empId);
	}
	
	/**
	 * getShiftDetailsforEmpForDate.
	 *
	 * @param empId String
	 */
	public ShiftDetails getShiftDetailsforEmpForDate(ShiftDetails shiftDetails) {
		return foodDetailsDao.getShiftDetailsforEmpForDate(shiftDetails);
	}

	public void deleteEmpFooddetails(Integer empId) {
		//foodDetailsDao.deleteEmpShiftDetails(empId);
	}

	public Integer getSpecificShiftCount(Date fDate, String shift) {
		return foodDetailsDao.getSpecificShiftCount(fDate, shift);
	}

}
