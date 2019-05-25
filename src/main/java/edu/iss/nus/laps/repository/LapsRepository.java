package edu.iss.nus.laps.repository;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.iss.nus.laps.model.Leaveapplication;
import edu.iss.nus.laps.model.Users;

@Repository
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


	@Transactional
	@Modifying
	@Query("Select c.username from Users c where c.role_name =?1")
	List<String> findByRole_name(String rolename);
	
}

