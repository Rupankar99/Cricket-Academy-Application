package com.example.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.example.demo.models.StudentModel;
import com.example.demo.payload.request.StudentRequest;
import com.example.demo.services.impl.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AdminController {
	
	@Autowired private UserServiceImpl userservice;
	
	
	
	//User Side
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/addAdmission")
	public ResponseEntity<?> enroll(  @Valid @RequestBody  StudentRequest studentRequest){
		return  userservice.enroll(studentRequest);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/viewenrolledcourses/{userid}")
	public ResponseEntity<List<StudentModel>> displayenrollcourse(@PathVariable("userid") Integer userid){
		return  userservice.viewenroll(userid);
	}
}
