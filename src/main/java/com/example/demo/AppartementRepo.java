package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
  
public interface AppartementRepo extends JpaRepository <Appartement,Long> {

	List<Appartement> findAll();

	
	
	
	
}
