package com.practice.reviewms.review.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.reviewms.review.Review;
import com.practice.reviewms.review.ReviewRepository;
import com.practice.reviewms.review.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	ReviewRepository reviewRepository;
	
	@Override
	public List<Review> getAllReview(Long companyId) {
		return reviewRepository.findByCompanyId(companyId);
	}

	@Override
	public boolean addReview(Long companyId, Review review) {
		if(companyId!=null && review!=null) {
			review.setCompanyId(companyId);
			reviewRepository.save(review);
			return true;
		}
		return false;
		
	}

	@Override
	public Review getReviewByID(Long reviewId) {
		return reviewRepository.findById(reviewId).orElse(null);
	}

	@Override
	public boolean updateReview(Long reviewId, Review updatedReview) {
		Review review=reviewRepository.findById(reviewId).orElse(null);
		if(review!=null) {
			review.setTitle(updatedReview.getTitle());
			review.setDescription(updatedReview.getDescription());
			review.setRating(updatedReview.getRating());
			review.setCompanyId(updatedReview.getCompanyId());
			reviewRepository.save(review);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteReview(Long reviewId) {
		if(reviewRepository.existsById(reviewId) ) {
			reviewRepository.deleteById(reviewId);
			return true;
		}
		return false;
	}
	
}
