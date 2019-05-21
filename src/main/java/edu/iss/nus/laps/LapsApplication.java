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
		repo.save(user);
		
	}
}
