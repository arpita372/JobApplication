package com.practice.jobms.job.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.practice.jobms.job.external.Company;

@FeignClient(name = "COMPANY-SERVICE", 
				url="${company-service.url}")
public interface CompanyClient {

	@GetMapping("/companies/{id}")
	Company getCompany(@PathVariable Long id);
	
}
