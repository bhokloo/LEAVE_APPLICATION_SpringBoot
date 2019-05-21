package edu.iss.nus.laps.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import edu.iss.nus.laps.model.Users;


public interface LapsRepository extends JpaRepository<Users, String>{
	
	List<Users> findByUsername(String username);
	
	@Transactional
	@Modifying
	@Query("update Users u set u.username = ?1,u.empname = ?2, u.role_name =?3, u.password = ?4 WHERE u.username =?5")
	void modify(String username, String empname,String role_name, String password,String previousname);
}

