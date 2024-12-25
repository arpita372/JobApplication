package com.practice.reviewms.review;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.reviewms.review.messaging.ReviewMessageProducer;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
	
	private ReviewService reviewService;
	private ReviewMessageProducer reviewMessageProducer;
	
	public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
		this.reviewService = reviewService;
		this.reviewMessageProducer = reviewMessageProducer;
	}

	@GetMapping
	public ResponseEntity<List<Review>> getReviews(@RequestParam Long companyId){
		return ResponseEntity.ok(reviewService.getAllReview(companyId));
	}
	
	@PostMapping
	public ResponseEntity<String> createReview(@RequestParam Long companyId,
												@RequestBody Review review){
		boolean isCreated=reviewService.addReview(companyId, review);
		if(isCreated) {
			reviewMessageProducer.sendMessage(review);
			return new ResponseEntity<String>("review created successfully",HttpStatus.CREATED);
		}else
			return new ResponseEntity<String>("Review not saved",HttpStatus.NOT_FOUND);
			
	}
	
	@GetMapping("/{reviewId}")
	public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
		Review review=reviewService.getReviewByID(reviewId);
		if(review!=null) {
			return new ResponseEntity<>(review,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{reviewId}")
	public ResponseEntity<String> updateReview(@PathVariable Long reviewId,
												@RequestBody Review review){
		boolean isUpdated=reviewService.updateReview( reviewId, review);
		if(isUpdated) {
			return new ResponseEntity<>("Review is updated",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Review not updated",HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<String> updateReview(@PathVariable Long reviewId){
		boolean isDeleted=reviewService.deleteReview(reviewId);
		if(isDeleted) {
			return new ResponseEntity<>("Review is deleted",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Review not deleted",HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/averageRating")
	public Double getAverageReview(@RequestParam Long companyId) {
		List<Review> reviewList=reviewService.getAllReview(companyId);
		return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
	}
	
	
}
