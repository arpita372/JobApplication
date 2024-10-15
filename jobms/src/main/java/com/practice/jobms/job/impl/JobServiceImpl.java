package com.practice.jobms.job.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.jobms.job.Job;
import com.practice.jobms.job.JobRepository;
import com.practice.jobms.job.JobService;
import com.practice.jobms.job.client.CompanyClient;
import com.practice.jobms.job.client.ReviewClient;
import com.practice.jobms.job.JobDTO;
import com.practice.jobms.job.external.Company;
import com.practice.jobms.job.external.Review;
import com.practice.jobms.job.mapper.JobDtoMapper;


@Service
public class JobServiceImpl implements JobService{
	
	@Autowired
	JobRepository jobRepository;
	
	//@Autowired
	//RestTemplate restTemplate;
	
	@Autowired
	CompanyClient companyClient;
	
	@Autowired
	ReviewClient reviewClient;
	
	@Override
	public void createJob(Job job) {
		jobRepository.save(job);
	}
	
	@Override
	public List<JobDTO> findAll() {
		
		List<Job> jobs=jobRepository.findAll();
		return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
	}
	
	public JobDTO convertToDTO(Job job) {
		/*
		 * Company
		 * company=restTemplate.getForObject("http://companyms:8081/companies/"+job.
		 * getCompanyId(),Company.class); //ParameterizedTypeReference -enable class for
		 * passing generic type ResponseEntity<List<Review>>
		 * reviewResponse=restTemplate.exchange(
		 * "http://reviewms:8083/reviews?companyId="+job.getCompanyId(), HttpMethod.GET,
		 * null, new ParameterizedTypeReference<List<Review>>() { }); List<Review>
		 * reviews=reviewResponse.getBody();
		 */
		Company company=companyClient.getCompany(job.getCompanyId());
		List<Review> reviews=reviewClient.getReviews(job.getCompanyId());
		JobDTO jobDTO=JobDtoMapper.mapTojobWithCompanyDTO(job, company, reviews);
		return jobDTO;
	}

	@Override
	public JobDTO getJobById(Long id) {
		 Job job=jobRepository.findById(id).orElse(null);
		 return convertToDTO(job);
	}

	@Override
	public boolean deleteJobById(Long id) {
		/*Iterator<Job> iterator=jobs.iterator();
		while(iterator.hasNext()) {
			Job job=iterator.next();
			if(job.getId().equals(id)) {
				iterator.remove();
				return true;
			}
		}
		return false;*/
		try {
			jobRepository.deleteById(id);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateJob(Long id, Job updatedJob) {
		Optional<Job> jobOptional=jobRepository.findById(id);
		if(jobOptional.isPresent()) {
			Job job=jobOptional.get();
			job.setTitle(updatedJob.getTitle());
			job.setDescription(updatedJob.getDescription());
			job.setMinSalary(updatedJob.getMinSalary());
			job.setMaxSalary(updatedJob.getMaxSalary());
			job.setLocation(updatedJob.getLocation());
			jobRepository.save(job);
			return true;
		}
		return false;
	}
		
	
}
