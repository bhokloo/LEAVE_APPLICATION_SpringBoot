package edu.iss.nus.laps.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import edu.iss.nus.laps.model.Employee;


public interface EmployeeRepo extends JpaRepository<Employee, String>{

	@Transactional
	@Modifying
	@Query("Delete from Employee s where s.empid =?1")
	void deleteByEmpid(String empid);

	@Transactional
	@Query("Select s from Employee s where s.empid = ?1")
	Employee findMe(String empid);

	@Transactional
	@Query("Select s from Employee s where s.managerfk.managerid = ?1")
	List<Object> findAllByManagerfk(String managerfk);

	@Transactional
	@Modifying
	@Query("update Employee u set u.managerfk.managerid = ?1 WHERE u.managerfk.managerid =?2 ")
	void doUpdate(String no,String managerid);
	

}
