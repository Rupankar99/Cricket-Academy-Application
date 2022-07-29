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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.CourseModel;
import com.example.demo.services.CourseModelService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired private CourseModelService courseModelService;
	
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/{institutesId}/")
	public ResponseEntity<List<CourseModel>> viewAllCoursesInstitute(@PathVariable (value = "institutesId") Long institutesId){
		return courseModelService.viewAllCoursesInstitute(institutesId);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/")
	public ResponseEntity<List<CourseModel>> viewAllCourses(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber
			,@RequestParam(value = "pageSize",defaultValue = "3",required = false) Integer pageSize
			,@RequestParam(value="sortBy",defaultValue = "courseId",required = false) String sortBy
			,@RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir){
		return courseModelService.viewAllCourses(pageNumber,pageSize,sortBy,sortDir);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{courseId}")
	public ResponseEntity<CourseModel> viewCourseById(@PathVariable Long courseId){
		return courseModelService.viewCourseById(courseId);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/course-count")
	public ResponseEntity<Long> count(){
		Long count = courseModelService.count();
		return new ResponseEntity<>(count,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/{institutesId}/create-course")
	public ResponseEntity<CourseModel> addCourseModel(@PathVariable (value = "institutesId") Long institutesId, @RequestBody CourseModel course){
		return courseModelService.addCourseModel(institutesId, course);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{courseId}")
	public ResponseEntity<CourseModel> editCourseModel(@PathVariable (value = "courseId") Long courseId , @RequestBody CourseModel course){
		return courseModelService.editCourseModel(courseId, course);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{courseId}")
	public ResponseEntity<HttpStatus> deleteCourseModel(@PathVariable(value = "courseId") Long courseId){
		return courseModelService.deleteCourseModel(courseId);
	}
}
