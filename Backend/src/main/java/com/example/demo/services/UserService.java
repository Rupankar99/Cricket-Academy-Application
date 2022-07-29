package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.models.StudentModel;
import com.example.demo.payload.request.StudentRequest;


@Service
public interface UserService {
	
	
	ResponseEntity<?> enroll(StudentRequest studentRequest);
	ResponseEntity<List<StudentModel>> viewenroll( long userid);
	
	//Forget Password
	String forgotPassword(String email);
	String resetPassword(String token,String password);
	String generateToken();
	Boolean isTokenExpired(LocalDateTime tokenCreationDate);
}
