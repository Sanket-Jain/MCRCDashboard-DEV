package com.sp.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sp.model.EmployeeShiftDetails;
import com.sp.model.ShiftDetails;

/**
 * FoodDetailsDao.
 */
@Repository
public class FoodDetailsDao {

    @Autowired
    private SessionFactory sessionFactory;
    private final Logger logger = Logger.getLogger(FoodDetailsDao.class.getName());

    /**
     * getSession.
     *
     * @return Session Hibernate session
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * getEmpShiftDetails.
     *
     * @param empId integer
     * @return EmployeeShiftDetails employeeShiftDetails
     */
    @Transactional
	public EmployeeShiftDetails getEmpShiftDetails(int empId) {
        Query q = getSession().createQuery("from EmployeeShiftDetails where id=:empId ");
        q.setParameter("empId", empId);
        EmployeeShiftDetails employeeShiftDetails = (EmployeeShiftDetails) q.uniqueResult();

        return employeeShiftDetails;
	}

    /**
     * saveUpdateEmpShiftDetails.
     *
     * @param employeeShiftDetails EmployeeShiftDetails
     */
    @Transactional
	public void saveUpdateEmpShiftDetails(EmployeeShiftDetails employeeShiftDetails) {
		getSession().saveOrUpdate(employeeShiftDetails);
	}

	 /**
     * deleteEmpShiftDetails.
     *
     * @param empId integer
     *//*
    @Transactional
	public void deleteEmpShiftDetails(Integer empId) {
		Query q = getSession().createQuery("from emp_shift_details where id=:empId ");
        q.setParameter("empId", empId);
        Note note = (Note) q.uniqueResult();
        getSession().delete(note);
	}*/

    @Transactional
	public void updateEmpShiftDetails(ShiftDetails shiftDetails) {
    	logger.info("Updating record : " + shiftDetails.toString());
    	Query q = getSession().createQuery("UPDATE ShiftDetails set firstShift = :firstShift, secondShift = :secondShift, eto = :eto  where recordId=:recordId ");
        q.setParameter("recordId", shiftDetails.getRecordId());
        q.setParameter("firstShift", shiftDetails.isFirstShift());
        q.setParameter("secondShift", shiftDetails.isSecondShift());
        q.setParameter("eto", shiftDetails.isEto());
        int updtaed = q.executeUpdate();
		logger.info("Updated: " + updtaed);
	}

    @Transactional
	public List<ShiftDetails> getShiftCount(Date fromDate, Date toDate) {
    	Query q = getSession().createQuery("from ShiftDetails where shiftDate BETWEEN :stDate AND :edDate");
    	q.setParameter("stDate", fromDate);
    	q.setParameter("edDate", toDate);
    	System.out.println("returning result");
    	return (List<ShiftDetails>) q.list();
	}
    @Transactional
	public EmployeeShiftDetails getShiftDetailsforEmp(Integer empId) {
        Query q = getSession().createQuery("from EmployeeShiftDetails e join fetch e.shiftDetailsList where e.empId=:empId ");
        q.setParameter("empId", empId);
        EmployeeShiftDetails employeeShiftDetails = (EmployeeShiftDetails) q.uniqueResult();

        return employeeShiftDetails;
    }

    @Transactional
	public ShiftDetails getShiftDetailsforEmpForDate(ShiftDetails shiftDetails) {
		 Query q = getSession().createQuery("from ShiftDetails where shiftDate=:shiftDate and employeeShiftDetails=:employeeShiftDetails");
	        q.setParameter("employeeShiftDetails", shiftDetails.getEmployeeShiftDetails());
	        q.setParameter("shiftDate", shiftDetails.getShiftDate());
	        q.setMaxResults(1);
	        
	        ShiftDetails result = (ShiftDetails) q.uniqueResult();

	        return result;
	}

    @Transactional
	public Integer getSpecificShiftCount(Date fDate, String field) {
    	Query q;
    	if (field != null) {
    		q = getSession().createSQLQuery("Select count(*) from shift_details where shift_date=:fDate and " + field + "=true");
	        q.setParameter("fDate", fDate);
    	} else {
    		q = getSession().createSQLQuery("Select count(*) from shift_details where shift_date=:fDate and first_shift=false and second_shift=false and eto=false");
	        q.setParameter("fDate", fDate);
    	}
	        
        BigInteger result = (BigInteger) q.uniqueResult();
        return Integer.parseInt(result.toString());
	}
}