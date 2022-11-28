package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface imageRepo extends JpaRepository <image,Long> {


	List<image> findAll();
	
	
	
}
