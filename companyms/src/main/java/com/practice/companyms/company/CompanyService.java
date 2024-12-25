package com.practice.companyms.company;

import java.util.List;

import com.practice.companyms.company.dto.ReviewMessage;

public interface CompanyService {
	
	List<Company> getAllCompanies();
	boolean updateCompany(Company company,Long id);
	void createCompany(Company company);
	boolean deleteCompany(Long id);
	Company getCompanyById(Long id);
	void updateCompanyRating(ReviewMessage reviewMessage);
}
