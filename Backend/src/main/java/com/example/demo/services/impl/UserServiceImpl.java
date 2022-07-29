package com.example.demo.services.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.models.CourseModel;
import com.example.demo.models.StudentModel;
import com.example.demo.models.User;
import com.example.demo.payload.request.StudentRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;

@Component
public class UserServiceImpl implements UserService{
	
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	CourseRepository  courseRepository;
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private static final long EXPIRY_TIME_MINUTES = 30;
	@Override
	public ResponseEntity<?> enroll(StudentRequest studentRequest) {
			
			List<Long> userId = studentRepository.getUserId(studentRequest.getUserid());
			
				if(userId.get(0) > 0)
				{
					
				List<Long> studentid = studentRepository.getStudentId(studentRequest.getUserid());
				Long studentId=studentid.get(0);
				StudentModel student = new StudentModel( studentId,
						studentRequest.getFirstName(), 
						studentRequest.getFatherName(),
						studentRequest.getMotherName(), 
						studentRequest.getAge(),
						studentRequest.getEmail(), 
						studentRequest.getGender(), 
						studentRequest.getState(), 
						studentRequest.getNationality(), 
						studentRequest.getPhonenumber(), 
						studentRequest.getAddress(),
						studentRequest.getPincode());
				
	
				CourseModel courses=courseRepository.getById(studentRequest.getCourseId());
				student.getCourses().add(courses);
				User u=userRepository.getById(studentRequest.getUserid());
				
				student.setUsers(u);
				logger.info(String.format("Student details = %s", student.toString()));
				studentRepository.save(student);
			
				return new ResponseEntity<>( student,HttpStatus.OK);
				}
				else if(userId.get(0) == 0) {
				      Long randomInt = (long)Math.floor(Math.random()*(2999-1000+1)+1000);
	
				       StudentModel student=new StudentModel( 
				    		    randomInt,
				    		   	studentRequest.getFirstName(), 
				    		   	studentRequest.getFatherName(),
								studentRequest.getMotherName(), 
								studentRequest.getAge(),
								studentRequest.getEmail(), 
								studentRequest.getGender(), 
								studentRequest.getState(), 
								studentRequest.getNationality(), 
								studentRequest.getPhonenumber(), 
								studentRequest.getAddress(),
								studentRequest.getPincode());
						CourseModel courses=courseRepository.getById(studentRequest.getCourseId());
					student.getCourses().add(courses);
				      
				      User u=userRepository.getById(studentRequest.getUserid());
				student.setUsers(u);
						
						studentRepository.save(student);
						 return new ResponseEntity<>( student,HttpStatus.OK);
	
				}
				return ResponseEntity
	                    .badRequest()
	                    .body(new MessageResponse("Error! Not Registerd "));	
					
			
		}
	
		@Override
		public ResponseEntity<List<StudentModel>> viewenroll(long userid) {
			List<StudentModel> student= studentRepository.getEnrollCourse(userid);
			return new ResponseEntity<>( student,HttpStatus.OK);
		}

		@Override
		public String forgotPassword(String email) {
			Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
			
			if(!userOptional.isPresent()) {
				logger.error(String.format("No id found with email = %s", email));
				return "Invalid Email Id";
			}
			
			User user = userOptional.get();
			user.setToken(generateToken());
			user.setTokenCreationDate(LocalDateTime.now());
			
			user = userRepository.save(user);
			return user.getToken();
		}

		@Override
		public String resetPassword(String token, String password) {
			Optional<User> userOptional = Optional.ofNullable(userRepository.findByToken(token));
			
			logger.info(token);
					
			if(!userOptional.isPresent()) {
				return "Invalid Token";
			}
			
			LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();
			
			if(Boolean.TRUE.equals(isTokenExpired(tokenCreationDate))){
				return "Token has expired";
			}
			
			User user = userOptional.get();
			user.setPassword(encoder.encode(password));
			user.setToken(null);
			user.setTokenCreationDate(null);
			
			userRepository.save(user);
			return "Your password was updated successfully !!";
		}

		@Override
		public String generateToken() {
			StringBuilder token = new StringBuilder();
			return token.append(UUID.randomUUID().toString()).append(UUID.randomUUID().toString()).toString();
		}

		@Override
		public Boolean isTokenExpired(LocalDateTime tokenCreationDate) {
			LocalDateTime now = LocalDateTime.now();
			Duration diff = Duration.between(tokenCreationDate, now);
			return diff.toMinutes() >= EXPIRY_TIME_MINUTES; 
		}
}
