package com.practice.companyms.company.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.practice.companyms.company.Company;
import com.practice.companyms.company.CompanyRepository;
import com.practice.companyms.company.CompanyService;
import com.practice.companyms.company.clients.ReviewClient;
import com.practice.companyms.company.dto.ReviewMessage;

import jakarta.ws.rs.NotFoundException;

@Service
public class CompanyServiceImpl implements CompanyService{

	private CompanyRepository companyRepository;
	private ReviewClient reviewClient;
	
	public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
		this.companyRepository = companyRepository;
		this.reviewClient = reviewClient;
	}

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

	@Override
	public void updateCompanyRating(ReviewMessage reviewMessage) {
		//System.out.println("Review message:"+reviewMessage.getDescription());
		Company company=companyRepository.findById(reviewMessage.getCompanyId())
				.orElseThrow(()->new NotFoundException("Company not found "+reviewMessage.getCompanyId()));
		double averageRating=reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
		company.setRating(averageRating);
		companyRepository.save(company);
				
	}
	
	
}
