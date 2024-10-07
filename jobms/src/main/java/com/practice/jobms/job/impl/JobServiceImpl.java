package com.practice.jobms.job.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.jobms.job.Job;
import com.practice.jobms.job.JobRepository;
import com.practice.jobms.job.JobService;


@Service
public class JobServiceImpl implements JobService{
	
	@Autowired
	JobRepository jobRepository;
	
	@Override
	public void createJob(Job job) {
		jobRepository.save(job);
	}
	
	@Override
	public List<Job> findAll() {
		return jobRepository.findAll();
	}

	@Override
	public Job getJobById(Long id) {
		return jobRepository.findById(id).orElse(null);
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
