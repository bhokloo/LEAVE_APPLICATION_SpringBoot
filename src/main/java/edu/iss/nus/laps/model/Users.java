package edu.iss.nus.laps.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users {

	
	@Id
	private String username;
	private String empname;
	private String role_name;
	private String password;
	public String getUsername() {
		return username;
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
	public Users(String username, String empname, String role_name, String password) {
		super();
		this.username = username;
		this.empname = empname;
		this.role_name = role_name;
		this.password = password;
	}
	
	
	public Users() {
		super();
		
	}
	
	@Override
	public String toString() {
		return "Users [username=" + username + ", empname=" + empname + ", role_name=" + role_name + ", password="
				+ password + "]";
	}





}
