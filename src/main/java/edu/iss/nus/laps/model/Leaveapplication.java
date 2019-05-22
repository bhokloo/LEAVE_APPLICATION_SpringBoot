package edu.iss.nus.laps.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Future;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Leaveapplication {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int leaveid;
	
	@Column(name="username")
	private String username;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Future
	private LocalDate startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Future
	private LocalDate endDate;
	private String leaveType;
	private String status;
	private String reason;
	private String managerComment;
	private int annualLeave;
	private int medicalLeave;
	private int noOfLeave;
	private int weekends;
	private int publicholidays;
	
	@Transient
	private String newdate;
	
	@Transient
	private String radio;
	
	
	public int getWeekends() {
		return weekends;
	}
	public void setWeekends(int weekends) {
		this.weekends = weekends;
	}
	public int getPublicholidays() {
		return publicholidays;
	}
	public void setPublicholidays(int publicholidays) {
		this.publicholidays = publicholidays;
	}
	public int getLeaveid() {
		return leaveid;
	}
	public void setLeaveid(int leaveid) {
		this.leaveid = leaveid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getManagerComment() {
		return managerComment;
	}
	public void setManagerComment(String managerComment) {
		this.managerComment = managerComment;
	}
	public int getAnnualLeave() {
		return annualLeave;
	}
	public void setAnnualLeave(int annualLeave) {
		this.annualLeave = annualLeave;
	}
	public int getMedicalLeave() {
		return medicalLeave;
	}
	public void setMedicalLeave(int medicalLeave) {
		this.medicalLeave = medicalLeave;
	}
	public int getNoOfLeave() {
		return noOfLeave;
	}
	public void setNoOfLeave(int noOfLeave) {
		this.noOfLeave = noOfLeave;
	}
	
	public String getNewdate() {
		return newdate;
	}
	public void setNewdate(String newdate) {
		this.newdate = newdate;
	}
	
	
	
	public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	public Leaveapplication() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Leaveapplication(int leaveid, String username, @Future LocalDate startDate, @Future LocalDate endDate,
			String leaveType, String status, String reason, String managerComment, int annualLeave, int medicalLeave,
			int noOfLeave, int weekends, int publicholidays, String newdate, String radio) {
		super();
		this.leaveid = leaveid;
		this.username = username;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaveType = leaveType;
		this.status = status;
		this.reason = reason;
		this.managerComment = managerComment;
		this.annualLeave = annualLeave;
		this.medicalLeave = medicalLeave;
		this.noOfLeave = noOfLeave;
		this.weekends = weekends;
		this.publicholidays = publicholidays;
		this.newdate = newdate;
		this.radio = radio;
	}
	@Override
	public String toString() {
		return "Leaveapplication [leaveid=" + leaveid + ", username=" + username + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", leaveType=" + leaveType + ", status=" + status + ", reason=" + reason
				+ ", managerComment=" + managerComment + ", annualLeave=" + annualLeave + ", medicalLeave="
				+ medicalLeave + ", noOfLeave=" + noOfLeave + ", weekends=" + weekends + ", publicholidays="
				+ publicholidays + ", newdate=" + newdate + ", radio=" + radio + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
