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
import com.example.demo.models.CourseModel;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.InstituteRepository;
import com.example.demo.services.CourseModelService;

@Service
public class CourseModelServiceImpl implements CourseModelService{
	
	@Autowired
	private CourseRepository courserepo;
	@Autowired
	private InstituteRepository instituterepo;
	
	//View all courses in an academy
	public ResponseEntity<List<CourseModel>> viewAllCoursesInstitute(Long institutesId){
		List<CourseModel> courses = courserepo.findByInstitutesId(institutesId);
		return new ResponseEntity<>(courses , HttpStatus.OK);
	}
	
	//View course by id
	public ResponseEntity<CourseModel> viewCourseById(Long courseId){
		CourseModel course = courserepo.findById(courseId)
		.orElseThrow(()-> new ResourceNotFoundException("No course found with id = " +courseId));
		return new ResponseEntity<>(course,HttpStatus.OK);
	}
	
	//Add Courses
	public ResponseEntity<CourseModel> addCourseModel(Long institutesId, CourseModel course)
	{
		CourseModel newcourse = instituterepo.findById(institutesId).map(institute->{
			course.setInstitute(institute);
			return courserepo.save(course);
		}).orElseThrow(()-> new ResourceNotFoundException("No academy found with id = " +institutesId));
		return new ResponseEntity<>(newcourse,HttpStatus.CREATED);
	}
	
	//View all courses
	public ResponseEntity<List<CourseModel>> viewAllCourses(int pageNumber,int pageSize,String sortBy,String sortDir){
		
		Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
		
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		Page<CourseModel> pageCourse = this.courserepo.findAll(p);
		
		List<CourseModel> list = pageCourse.getContent();
		return new ResponseEntity<>(list , HttpStatus.OK);
	}
	
	//Edit Course
	public ResponseEntity<CourseModel> editCourseModel(Long courseId, CourseModel course){
		CourseModel existingcourse = courserepo.findById(courseId)
		.orElseThrow(()-> new ResourceNotFoundException("No course found by id = " + courseId));
		existingcourse.setCourseName(course.getCourseName());
		existingcourse.setCourseDuration(course.getCourseDuration());
		existingcourse.setCourseDescription(course.getCourseDescription());
		return new ResponseEntity<>(courserepo.save(existingcourse), HttpStatus.OK);
	}
	
	//Delete Course
	public ResponseEntity<HttpStatus> deleteCourseModel(Long courseId){
		courserepo.deleteById(courseId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public Long count() {
		return courserepo.count();
	}
}
