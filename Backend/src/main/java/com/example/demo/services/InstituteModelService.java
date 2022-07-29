package com.example.demo.services;

import java.util.List;
import com.example.demo.models.InstituteModel;

public interface InstituteModelService {
		//Add Institute
		InstituteModel addInstitute(InstituteModel instituteModel);
		
		//Fetch Institute
		List<InstituteModel> viewInstituteModelList(int pageNumber,int pageSize);
		
		//Fetch Institute by Id
		InstituteModel viewInstituteModelById(Long institutesId);
		
		//Get count of all academies
		Long getCount();
		 
		//Update Institute
		InstituteModel editInstituteModel(Long instituteId , InstituteModel instituteModel);
		
		//Delete Institute
		void deleteInstituteModel(Long instituteId);
}
