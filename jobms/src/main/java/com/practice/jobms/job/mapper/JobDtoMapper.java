package com.practice.jobms.job.mapper;

import java.util.List;

import com.practice.jobms.job.Job;
import com.practice.jobms.job.JobDTO;
import com.practice.jobms.job.external.Company;
import com.practice.jobms.job.external.Review;

public class JobDtoMapper {
	
	public static JobDTO mapTojobWithCompanyDTO(Job job,Company company, List<Review> reviews) {
		
		JobDTO jobDTO = new JobDTO();
		
		jobDTO.setId(job.getId());
		jobDTO.setTitle(job.getTitle());
		jobDTO.setDescription(job.getDescription());
		jobDTO.setLocation(job.getLocation());
		jobDTO.setMinSalary(job.getMinSalary());
		jobDTO.setMaxSalary(job.getMaxSalary());
		jobDTO.setCompany(company);
		jobDTO.setReviews(reviews);
		return jobDTO;
		
	}
}
