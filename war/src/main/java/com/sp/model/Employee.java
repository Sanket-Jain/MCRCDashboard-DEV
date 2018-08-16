package com.sp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_details")
public class Employee {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "emp_id")
	private Integer empId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "designation")
	private String designation;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "extn")
	private String extn;
	
	@Column(name = "birthday")
	private Date birthday;
	
	@Column(name = "anniversaryDate")
	private Date anniversaryDate;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "profileImage")
	private String profileImage;
	
	@Column(name = "isValid")
	private boolean isValid;
	
	@Column(name = "isProfileComplete")
	private boolean isProfileComplete;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getExtn() {
		return extn;
	}

	public void setExtn(String extn) {
		this.extn = extn;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getAnniversaryDate() {
		return anniversaryDate;
	}

	public void setAnniversaryDate(Date anniversaryDate) {
		this.anniversaryDate = anniversaryDate;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public boolean isProfileComplete() {
		return isProfileComplete;
	}

	public void setProfileComplete(boolean isProfileComplete) {
		this.isProfileComplete = isProfileComplete;
	}
	
}
