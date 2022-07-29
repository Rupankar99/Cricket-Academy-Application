package com.example.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.StudentModel;
import com.example.demo.repository.StudentRepository;
import com.example.demo.services.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired StudentRepository studentrepo;
	
	public ResponseEntity<List<StudentModel>> viewAllStudents(Integer pageNumber,Integer pageSize,String sortBy,String sortDir){
		Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
		
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		
		Page<StudentModel> pageStudent = this.studentrepo.findAll(p);
		
		List<StudentModel> list = pageStudent.getContent();
		
		return new ResponseEntity<>(list , HttpStatus.OK);
	}
	
	public ResponseEntity<StudentModel> viewAdmissionById(Long studentid){
		StudentModel student = studentrepo.findById(studentid)
		.orElseThrow(()-> new ResourceNotFoundException("No student found with ID "+studentid));
		return new ResponseEntity<>(student,HttpStatus.OK);
	}
	
	public ResponseEntity<List<StudentModel>> viewStudentById(Long studentid){
		List<StudentModel> list  = studentrepo.findStudentByStudentid(studentid);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	public ResponseEntity<List<StudentModel>> editStudentById(Long studentid , StudentModel student){
		
		List<StudentModel> list = studentrepo.findStudentByStudentid(studentid);
		for(StudentModel existingStudent : list) {
			existingStudent.setAddress(student.getAddress());
			existingStudent.setAge(student.getAge());
			existingStudent.setEmail(student.getEmail());
			existingStudent.setFathersname(student.getFathersname());
			existingStudent.setGender(student.getGender());
			existingStudent.setMothersname(student.getMothersname());
			existingStudent.setNationality(student.getNationality());
			existingStudent.setPhonenumber(student.getPhonenumber());
			existingStudent.setPincode(student.getPincode());
			existingStudent.setState(student.getState());
			existingStudent.setStudentname(student.getStudentname());
			studentrepo.save(existingStudent);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	public ResponseEntity<HttpStatus> deleteStudentModel(Long studentid){
		studentrepo.deleteByStudentid(studentid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public Long getCount() {
		return studentrepo.count();
	}
}
