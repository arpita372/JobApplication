package com.practice.companyms.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	

	@PostMapping
	public ResponseEntity<String> addCompany(@RequestBody Company company){
		companyService.createCompany(company);
		return new ResponseEntity<>("Company added successfully",HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Company>> getAllCompanies(){
		return ResponseEntity.ok(companyService.getAllCompanies());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
		Company company=companyService.getCompanyById(id);
		if(company!=null) {
			return new ResponseEntity<>(company,HttpStatus.OK);
		}
		else {	
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateComapany(@PathVariable Long id,@RequestBody Company company){
		boolean isUpdated=companyService.updateCompany(company, id);
		if(isUpdated) {
			return new ResponseEntity<>("Company updated successfully",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Company not found",HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCompany(@PathVariable Long id){
		boolean isDeleted=companyService.deleteCompany(id);
		if(isDeleted) {
			return new ResponseEntity<>("Company deleted successfully",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Company not found",HttpStatus.NOT_FOUND);
		}
	}
	
}
