package edu.iss.nus.laps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FUsers {

	@Id 
	@Column(name="usedId")
	private String userId;
	private String name;
	private String role;
	private String password;
	
	

}
