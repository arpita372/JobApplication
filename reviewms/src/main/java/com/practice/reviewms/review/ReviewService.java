package com.practice.reviewms.review;

import java.util.List;

public interface ReviewService {

	List<Review> getAllReview(Long companyId);
	boolean addReview(Long companyId,Review review);
	Review getReviewByID(Long reviewId);
	boolean updateReview(Long reviewId,Review review);
	boolean deleteReview(Long reviewId);
}
