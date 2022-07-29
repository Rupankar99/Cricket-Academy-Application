package com.example.demo.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.models.StudentModel;

public interface StudentService {
	
	//View all students
	public ResponseEntity<List<StudentModel>> viewAllStudents(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//View admission by id
	public ResponseEntity<StudentModel> viewAdmissionById(Long studentid);
	
	//View student by studentId
	public ResponseEntity<List<StudentModel>> viewStudentById(Long studentid);
	
	//Count of all students
	public Long getCount();
	
	//Edit student
	public ResponseEntity<List<StudentModel>> editStudentById(Long studentid , StudentModel student);
	
	
	//Delete student
	public ResponseEntity<HttpStatus> deleteStudentModel(Long studentid);
}
