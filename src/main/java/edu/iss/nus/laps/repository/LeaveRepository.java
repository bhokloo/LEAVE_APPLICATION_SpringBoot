package edu.iss.nus.laps.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import edu.iss.nus.laps.model.Leaveapplication;


public interface LeaveRepository extends JpaRepository<Leaveapplication, String>{

	List<Leaveapplication>  findByUsername(String username);
	List<Leaveapplication>  findByLeaveid(int leaveid);
	
//	@Transactional
//	@Modifying
//	@Query("select count(e.medicalLeave) from Leaveapplication e")
//	int findCount();

	
	@Transactional
	@Modifying
	@Query("update Leaveapplication u set u.managerComment = ?1,u.status = ?2 WHERE u.leaveid =?3")
	void leaveapplication(String managerComment, String status,int leaveid);
	
	
//	@Transactional
//	@Modifying
//	@Query("update leaveapplication u set u.username = ?1 WHERE u.username =?2")
//	void modify(String username, String username2);

}
