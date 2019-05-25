package edu.iss.nus.laps.Services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.iss.nus.laps.model.Users;
import edu.iss.nus.laps.repository.LapsRepository;


@Service
public class Services implements ServicesInterface {
		
	@Autowired
	private LapsRepository repo;
		
	
//	@Transactional
//	public Users user(Users users) {	
//	Users userdetails = repo.findByUsername(users.getUsername());
//	return userdetails;
//	}
}
