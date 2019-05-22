package edu.iss.nus.laps.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
public class Users {
	
	@Id 
	@NotBlank(message = "Name may not be empty")
	@Column(name="username")
	private String username;
	private String empname;
	private String role_name;
	private String password;
	
	@Transient
	private String userdetails;
	
	
	public String getUserdetails() {
		return userdetails;
	}
	public void setUserdetails(String userdetails) {
		this.userdetails = userdetails;
	}
	public String getUsername() {
		return username;
	}
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private List<Leaveapplication> leaveapp;
	
	
	
	public List<Leaveapplication> getLeaveapp() {
		return leaveapp;
	}
	public void setLeaveapp(List<Leaveapplication> leaveapp) {
		this.leaveapp = leaveapp;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	public Users(@NotBlank(message = "Name may not be empty") String username, String empname, String role_name,
			String password, String userdetails, List<Leaveapplication> leaveapp) {
		super();
		this.username = username;
		this.empname = empname;
		this.role_name = role_name;
		this.password = password;
		this.userdetails = userdetails;
		this.leaveapp = leaveapp;
	}
	
	public Users() {
		super();
		
	}
	@Override
	public String toString() {
		return "Users [username=" + username + ", empname=" + empname + ", role_name=" + role_name + ", password="
				+ password + ", userdetails=" + userdetails + ", leaveapp=" + leaveapp + "]";
	}
	


}
