package com.example.demo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="institutes")

public class InstituteModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long instituteId;
	
	@Column(name = "institute_name")
	private String instituteName;
	
	@Column(name = "institute_desc")
	private String instituteDescription;
	
	@Column(name = "institute_address")
	private String instituteAddress;
	private String mobile;
	private String email;
	private String imgUrl;
	
	@OneToMany(mappedBy = "institute" , cascade = {
		CascadeType.ALL
	})
	List<CourseModel> courses;
	
	public InstituteModel() {
		super();
	}
	
	public InstituteModel(Long instituteId, String instituteName, String instituteDescription, String instituteAddress,
			String mobile, String email, String imgUrl) {
		super();
		this.instituteId = instituteId;
		this.instituteName = instituteName;
		this.instituteDescription = instituteDescription;
		this.instituteAddress = instituteAddress;
		this.mobile = mobile;
		this.email = email;
		this.imgUrl = imgUrl;
	}

	public Long getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(Long instituteId) {
		this.instituteId = instituteId;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getInstituteDescription() {
		return instituteDescription;
	}
	public void setInstituteDescription(String instituteDescription) {
		this.instituteDescription = instituteDescription;
	}
	public String getInstituteAddress() {
		return instituteAddress;
	}
	public void setInstituteAddress(String instituteAddress) {
		this.instituteAddress = instituteAddress;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public List<CourseModel> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseModel> courses) {
		this.courses = courses;
	}
}
