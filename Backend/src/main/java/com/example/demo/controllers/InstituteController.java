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

import com.example.demo.models.InstituteModel;
import com.example.demo.services.InstituteModelService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/academies")
public class InstituteController {
	@Autowired private InstituteModelService instituteModelService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/")
	public ResponseEntity<InstituteModel> addInstitute(@RequestBody InstituteModel instituteModel) {
		InstituteModel institute = instituteModelService.addInstitute(instituteModel);
		return new ResponseEntity<>(institute,HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/")
	public List<InstituteModel> viewInstituteModelList(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,@RequestParam(value = "pageSize",defaultValue = "2",required = false) Integer pageSize)
	{
		return instituteModelService.viewInstituteModelList(pageNumber,pageSize);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/count")
	public Long getCount() {
		return instituteModelService.getCount();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{instituteId}")
	public ResponseEntity<InstituteModel> viewInstituteModelById(@PathVariable long instituteId)
	{
		InstituteModel institute = instituteModelService.viewInstituteModelById(instituteId);
		return new ResponseEntity<>(institute,HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{instituteId}")
	public InstituteModel editInstituteModel(@PathVariable Long instituteId,@RequestBody InstituteModel instituteModel) {
		return instituteModelService.editInstituteModel(instituteId,instituteModel);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{instituteId}")
	public ResponseEntity<HttpStatus> deleteInstituteModel(@PathVariable Long instituteId) {
		instituteModelService.deleteInstituteModel(instituteId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
