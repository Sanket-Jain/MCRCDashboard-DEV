package com.sp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "shift_details")
public class ShiftDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "record_id")
	private Integer recordId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "emp_id", referencedColumnName = "id")
	private EmployeeShiftDetails employeeShiftDetails;

	@Column(name = "shift_date")
	@Temporal(TemporalType.DATE)
	private Date shiftDate;

	@Column(name = "first_shift")
	private boolean firstShift;

	@Column(name = "second_shift")
	private boolean secondShift;

	@Column(name = "eto")
	private boolean eto;

	@Transient
	private String status;

	public ShiftDetails() {
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public EmployeeShiftDetails getEmployeeShiftDetails() {
		return employeeShiftDetails;
	}

	public void setEmployeeShiftDetails(EmployeeShiftDetails employeeShiftDetails) {
		this.employeeShiftDetails = employeeShiftDetails;
	}

	public Date getShiftDate() {
		return shiftDate;
	}

	public void setShiftDate(Date shiftDate) {
		this.shiftDate = shiftDate;
	}

	public boolean isFirstShift() {
		return firstShift;
	}

	public void setFirstShift(boolean firstShift) {
		this.firstShift = firstShift;
	}

	public boolean isSecondShift() {
		return secondShift;
	}

	public void setSecondShift(boolean secondShift) {
		this.secondShift = secondShift;
	}

	public boolean isEto() {
		return eto;
	}

	public void setEto(boolean eto) {
		this.eto = eto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ShiftDetails [recordId=" + recordId + ", employeeShiftDetails=" + employeeShiftDetails + ", shiftDate="
				+ shiftDate + ", firstShift=" + firstShift + ", secondShift=" + secondShift + ", eto=" + eto
				+ ", status=" + status + "]";
	}

}
