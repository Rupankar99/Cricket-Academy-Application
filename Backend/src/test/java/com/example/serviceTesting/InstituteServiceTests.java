package com.example.serviceTesting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.models.InstituteModel;
import com.example.demo.repository.InstituteRepository;
import com.example.demo.services.impl.InstituteModelServiceImpl;

@ExtendWith(MockitoExtension.class)
class InstituteServiceTests {
	
	@Mock
	private InstituteRepository instituteRepo;
	
	@Autowired
	@InjectMocks
	private InstituteModelServiceImpl instituteService;
	
	private InstituteModel institute1;
	private InstituteModel institute2;
	List<InstituteModel> instituteList;
	
	@BeforeEach
	void doConstruct() {
		instituteList = new ArrayList<>();
		institute1 = new InstituteModel(1L, "ABC Institute", "Best institute", "New Delhi", "9876543210", "abcins@gmail.com", "#");
		institute2 = new InstituteModel(2L, "XYZ Institute", "Best institute", "Kolkata", "9845611210", "xyzins@gmail.com", "#");
		instituteList.add(institute1);
		instituteList.add(institute2);
	}
	
	@AfterEach
	void doDestruct() {
		institute1 = institute2 = null;
		instituteList = null;
	}
	
	//Test case for saving an institute
	@Test
	void givenInstituteToAddShouldAddInstitute() {
	     when(instituteRepo.save(any())).thenReturn(institute1);
	     instituteService.addInstitute(institute1);
	     verify(instituteRepo,times(1)).save(any());
	}
	
	//Test case for getting an institute by id
	@Test
	 void givenInstituteIdShouldReturnInstitute() {
		when(instituteRepo.findById(1L)).thenReturn(Optional.ofNullable(institute1));
		assertThat(instituteService.viewInstituteModelById(institute1.getInstituteId())).isEqualTo(institute1);
	}
	
}
 