package com.example.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.InstituteModel;
import com.example.demo.repository.InstituteRepository;
import com.example.demo.services.InstituteModelService;

@Service
public class InstituteModelServiceImpl implements InstituteModelService{
	
	@Autowired
	private InstituteRepository instituteRepository;
	
	//Add Institute
	@Override
	public InstituteModel addInstitute(InstituteModel instituteModel) {
		return instituteRepository.save(instituteModel);
	}
	
	//View Institutes
	@Override
	public List<InstituteModel> viewInstituteModelList(int pageNumber,int pageSize)
	{
		
		Pageable p = PageRequest.of(pageNumber, pageSize);
		
		Page<InstituteModel> pageInstitute = this.instituteRepository.findAll(p);
		
		
		return pageInstitute.getContent(); 
	}
	
	//View Institute by Id
	public InstituteModel viewInstituteModelById(Long instituteId)
	{
		 return instituteRepository.findById(instituteId)
				.orElseThrow(()-> new ResourceNotFoundException("No Institute found with ID "+instituteId));
	}
	
	//Edit Institutes
	@Override
	public InstituteModel editInstituteModel(Long instituteId , InstituteModel instituteModel)
	{
		InstituteModel existingInstituteModel = this.instituteRepository.findById(instituteId)
		.orElseThrow(()-> new ResourceNotFoundException("No Academy found with Id "+instituteId));
		existingInstituteModel.setInstituteName(instituteModel.getInstituteName());
		existingInstituteModel.setInstituteDescription(instituteModel.getInstituteDescription());
		existingInstituteModel.setInstituteAddress(instituteModel.getInstituteAddress());
		existingInstituteModel.setEmail(instituteModel.getEmail());
		existingInstituteModel.setMobile(instituteModel.getMobile());
		existingInstituteModel.setImgUrl(instituteModel.getImgUrl());
		return this.instituteRepository.save(existingInstituteModel);
	}
	
	//Delete Institutes
	@Override
	public void deleteInstituteModel(Long instituteId) {
		InstituteModel instituteModel = instituteRepository.findById(instituteId)
				.orElseThrow(()-> new ResourceNotFoundException("No Academy found with Id "+instituteId));
		instituteRepository.delete(instituteModel);
	}

	@Override
	public Long getCount() {
		return this.instituteRepository.count();
	}
}
