package edu.iss.nus.laps.model;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.persistence.PreRemove;

@Entity
public class Manager {

	@Id
	@NotBlank(message = "Name may not be empty")
	@Column(name = "managerid")
	private String managerid;
	private String managername;
	private String managerpassword;

	@OneToMany(mappedBy = "managerfk", fetch=FetchType.EAGER)
	private List<Employee> emplist;
    
	@PreRemove
	public void  setNullToForeingKeys() {
		 emplist.forEach(x -> x.setManagerfk(null));
	}
	
	public List<Employee> getEmplist() {
		return emplist;
	}

	public void setEmplist(List<Employee> emplist) {
		this.emplist = emplist;
	}

	public String getManagerid() {
		return managerid;
	}

	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}

	public String getManagername() {
		return managername;
	}

	public void setManagername(String managername) {
		this.managername = managername;
	}

	public String getManagerpassword() {
		return managerpassword;
	}

	public void setManagerpassword(String managerpassword) {
		this.managerpassword = managerpassword;
	}

	public Manager() {
		super();
	}

	public Manager(@NotBlank(message = "Name may not be empty") String managerid, String managername,
			String managerpassword, List<Employee> emplist) {
		super();
		this.managerid = managerid;
		this.managername = managername;
		this.managerpassword = managerpassword;
		this.emplist = emplist;
	}

	@Override
	public String toString() {
		return "Manager [managerid=" + managerid + ", managername=" + managername + ", managerpassword="
				+ managerpassword + ", emplist=" + emplist + "]";
	}

	

	
	
	

	
}
