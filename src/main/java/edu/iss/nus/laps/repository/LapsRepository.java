package edu.iss.nus.laps.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import edu.iss.nus.laps.model.Users;


public interface LapsRepository extends JpaRepository<Users, String>{
	
	Users findByUsername(String username);
	
	@Transactional
	@Modifying
	@Query("update Users u set u.empname = ?1, u.role_name =?2, u.password = ?3 WHERE u.username =?4")
	void modify(String empname,String role_name, String password,String username);

	@Transactional
	@Modifying
	@Query("Delete from Users c where c.username =?1")
	void delete(String username);
}

