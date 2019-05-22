package edu.iss.nus.laps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import edu.iss.nus.laps.model.Users;
import edu.iss.nus.laps.repository.LapsRepository;

@SpringBootApplication
public class LapsApplication implements CommandLineRunner{

	@Autowired
	private LapsRepository repo;
	
	public static void main(String[] args) {
		SpringApplication.run(LapsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Users user= new Users();
		user.setUsername("indra");
		user.setRole_name("admin");
		user.setPassword("123abc");
		user.setEmpname("Indrajit Maurya");
		
		Users user1= new Users();
		user1.setUsername("soe");
		user1.setRole_name("employee");
		user1.setPassword("123a");
		user1.setEmpname("Soehan");
		
		Users user2= new Users();
		user2.setUsername("yang");
		user2.setRole_name("manager");
		user2.setPassword("123a");
		user2.setEmpname("Yangue");
		
		repo.save(user);
		repo.save(user1);
		repo.save(user2);
		
	}
}
