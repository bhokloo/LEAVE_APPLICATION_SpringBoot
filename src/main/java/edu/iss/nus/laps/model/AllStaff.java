package edu.iss.nus.laps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class AllStaff {
	
	@Id
	@NotBlank(message = "Name may not be empty")
	@Column(name = "staffId")
	private String staffId;
	private String staffName;
	private String staffPassword;
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getStaffPassword() {
		return staffPassword;
	}
	public void setStaffPassword(String staffPassword) {
		this.staffPassword = staffPassword;
	}
	public AllStaff(@NotBlank(message = "Name may not be empty") String staffId, String staffName,
			String staffPassword) {
		super();
		this.staffId = staffId;
		this.staffName = staffName;
		this.staffPassword = staffPassword;
	}
	public AllStaff() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "AllStaff [staffId=" + staffId + ", staffName=" + staffName + ", staffPassword=" + staffPassword + "]";
	}
	
	

	


	
	
}
