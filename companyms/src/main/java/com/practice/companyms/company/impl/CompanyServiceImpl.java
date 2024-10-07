package com.practice.companyms.company.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.companyms.company.Company;
import com.practice.companyms.company.CompanyRepository;
import com.practice.companyms.company.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	private CompanyRepository companyRepository;
	
	@Override
	public List<Company> getAllCompanies() {
		
		return companyRepository.findAll();
	}

	@Override
	public boolean updateCompany(Company company, Long id) {
		Optional<Company> company1=companyRepository.findById(id);
		if(company1.isPresent()) {
			Company companyToUpdate=company1.get();
			companyToUpdate.setName(company.getName());
			companyToUpdate.setDescription(company.getDescription());
			companyRepository.save(companyToUpdate);
			return true;
		}
		return false;
	}

	@Override
	public void createCompany(Company company) {
		companyRepository.save(company);
	}

	@Override
	public boolean deleteCompany(Long id) {
		if(companyRepository.existsById(id)){
			companyRepository.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Company getCompanyById(Long id) {
		return companyRepository.findById(id).orElse(null);
	}
	
	
}
