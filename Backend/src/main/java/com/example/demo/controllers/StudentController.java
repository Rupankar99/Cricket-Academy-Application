package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.StudentModel;
import com.example.demo.services.StudentService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired private StudentService studentservice;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/")
	public ResponseEntity<List<StudentModel>> viewAllStudents(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber
			,@RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize
			,@RequestParam(value="sortBy",defaultValue = "Studentid",required = false) String sortBy
			,@RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir
			){
		return studentservice.viewAllStudents(pageNumber,pageSize,sortBy,sortDir);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{studentid}/")
	public ResponseEntity<StudentModel> viewAdmissionById(@PathVariable Long studentid){
		return studentservice.viewAdmissionById(studentid);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/count")
	public ResponseEntity<Long> getCount(){
		Long count = this.studentservice.getCount();
		return new ResponseEntity<>(count,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{studentid}/")
	public ResponseEntity<HttpStatus> deleteStudentModel(@PathVariable Long studentid){
		return studentservice.deleteStudentModel(studentid);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{studentid}/")
	public ResponseEntity<List<StudentModel>> editStudentById(@PathVariable Long studentid , @RequestBody StudentModel student){
		return studentservice.editStudentById(studentid, student);
	}
}
