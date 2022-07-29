package com.example.repositoryTesting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.example.demo.CricketAcademy1Application;
import com.example.demo.models.InstituteModel;
import com.example.demo.repository.InstituteRepository;

@SpringBootTest(classes = CricketAcademy1Application.class)

class InstituteRepoTests {
	
	@Autowired InstituteRepository instituteRepo;
	
	@Test
	void saveInstituteTest() {
		InstituteModel institute = new InstituteModel(169L,"ABC Institute","Best institute","Delhi","1234598687","abcins@gmail.com","#");
		assertNotNull(instituteRepo.save(institute));
	}
	
	@Test
	void testgetAllInstitutes() {
		List<InstituteModel> list = instituteRepo.findAll();
		assertThat(list.size()).isPositive();
	}
	
	@Test
	void testInstituteById() {
		InstituteModel institute = instituteRepo.findById(123L).get();
		assertThat(institute.getInstituteId()).isEqualTo(123L);
	}
	
	@Test
	void testUpdateInstitute() {
		InstituteModel institute = instituteRepo.findById(123L).get();
		institute.setInstituteName("New ABC institute");
		InstituteModel updatedInstitute = instituteRepo.save(institute);
		assertThat(updatedInstitute.getInstituteName()).isEqualTo("New ABC institute");
	}
	
	@Test
	void testDeleteInstitute() {
		instituteRepo.deleteById(123L);
		
		InstituteModel institute = null;
		
		Optional<InstituteModel> optionalInstitute = instituteRepo.findById(123L);
		if(optionalInstitute.isPresent()) {
			institute = optionalInstitute.get();
		}
		assertThat(institute).isNull();
	}
}
