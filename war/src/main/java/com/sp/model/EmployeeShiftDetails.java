package com.sp.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "emp_shift_details")
public class EmployeeShiftDetails {

	@Id
	@Column(name = "id")
	private Integer empId;

	@Column(name = "name")
	private String empName;

	//@JsonIgnore
    @OneToMany(mappedBy = "employeeShiftDetails", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
	private List<ShiftDetails> shiftDetailsList;

	public EmployeeShiftDetails() {
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public List<ShiftDetails> getShiftDetailsList() {
		return shiftDetailsList;
	}

	public void setShiftDetailsList(List<ShiftDetails> shiftDetailsList) {
		this.shiftDetailsList = shiftDetailsList;
	}

}
