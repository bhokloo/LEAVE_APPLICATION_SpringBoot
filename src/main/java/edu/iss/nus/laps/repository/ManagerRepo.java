package edu.iss.nus.laps.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import edu.iss.nus.laps.model.Employee;
import edu.iss.nus.laps.model.Manager;


public interface ManagerRepo extends JpaRepository<Manager, String>{

	@Transactional
	@Query("Select s.managername from Manager s where s.managerid = ?1")
	String findMee(String managerid);

	@Transactional
	@Query("Delete from Manager s where s.managerid =?1")
	List<Manager> deleteByManagerid(String string);


}
