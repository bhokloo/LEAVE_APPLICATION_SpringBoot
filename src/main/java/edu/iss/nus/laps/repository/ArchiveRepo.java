package edu.iss.nus.laps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.iss.nus.laps.model.UserArchive;

public interface ArchiveRepo  extends JpaRepository<UserArchive, String>{
	
}


