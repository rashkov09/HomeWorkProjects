package com.scalefocus.midterm.trippyapp.controller;

import com.scalefocus.midterm.trippyapp.controller.request.ReviewRequest;
import com.scalefocus.midterm.trippyapp.model.dto.ReviewDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/reviews")
public interface ReviewController {

    @GetMapping()
    ResponseEntity<List<ReviewDto>> getAllReviews();

    @GetMapping("/{id}")
    ResponseEntity<ReviewDto> getReviewById(@PathVariable
                                            Long id);

    @PostMapping(params = {"username"})
    ResponseEntity<Void> addReview(
            @RequestBody @Valid ReviewRequest reviewRequest,
            @RequestParam(value = "username") String username);

    @PutMapping("/{id}")
    ResponseEntity<ReviewDto> updateReview(
            @RequestBody @Valid ReviewRequest reviewRequest, @PathVariable int id,
            @RequestParam(required = false) boolean returnOld,
            @RequestParam(value = "username") String username);

    @DeleteMapping("/{id}")
    ResponseEntity<ReviewDto> deleteReview(
            @RequestBody @Valid ReviewRequest reviewRequest, @PathVariable int id,
            @RequestParam(required = false) boolean returnOld,
            @RequestParam(value = "username") String username);


}
