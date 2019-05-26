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
	@Query("Select s from Manager s where s.managerid = ?1")
	Manager findMee(String managerid);

	@Transactional
	@Modifying
	@Query("Delete from Manager s where s.managerid =?1")
	void deleteByManagerid(String string);


}
