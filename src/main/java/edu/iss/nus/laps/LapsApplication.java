package edu.iss.nus.laps;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import edu.iss.nus.laps.model.AllStaff;
import edu.iss.nus.laps.model.Employee;
import edu.iss.nus.laps.model.Manager;
import edu.iss.nus.laps.repository.AllStaffRepo;
import edu.iss.nus.laps.repository.EmployeeRepo;
import edu.iss.nus.laps.repository.LapsRepository;
import edu.iss.nus.laps.repository.LeaveRepository;
import edu.iss.nus.laps.repository.ManagerRepo;


@SpringBootApplication
public class LapsApplication implements CommandLineRunner{

	@Autowired
	private LapsRepository repo;
	
	@Autowired
	private AllStaffRepo adminrepo;
	
	@Autowired
	private ManagerRepo managerrepo;
	
	@Autowired
	private EmployeeRepo employeerepo;
	

	@Autowired
	private LeaveRepository leaverepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(LapsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		AllStaff admin = new AllStaff();
//		admin.setAdminid("12345");
//		admin.setAdminname("Indrajit Maurya");
//		admin.setAdminpassword("password");
				
		Manager man2 = new Manager();
		man2.setManagerid("manid2");	
		man2.setManagername("manname2");		
		man2.setManagerpassword("manpas2");
		
		Manager man1 = new Manager();
		man1.setManagerid("manid1");	
		man1.setManagername("manname1");		
		man1.setManagerpassword("manpas1");
		
		Employee emp2 = new Employee();
		emp2.setEmpid("empid2");
		emp2.setEmpname("empname2");
		emp2.setEmppassword("emppass2");
		emp2.setManagerfk(man2);
		
		Employee emp1 = new Employee();
		emp1.setEmpid("empid1");
		emp1.setEmpname("empname1");
		emp1.setEmppassword("emppass1");
		emp1.setManagerfk(man1);

	    managerrepo.save(man1);
	    managerrepo.save(man2);
	    employeerepo.save(emp1);
	    employeerepo.save(emp2);
	    managerrepo.deleteById("manid2");
	    
	    emp2.setManagerfk(man1);
	    employeerepo.save(emp2);


		
	}
}
