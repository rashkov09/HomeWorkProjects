package com.scalefocus.midterm.trippyapp.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.midterm.trippyapp.constants.enums.BusinessType;
import com.scalefocus.midterm.trippyapp.constants.enums.ReviewRating;
import com.scalefocus.midterm.trippyapp.mapper.BusinessMapper;
import com.scalefocus.midterm.trippyapp.repository.ReviewRepository;
import com.scalefocus.midterm.trippyapp.service.ReviewService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.scalefocus.midterm.trippyapp.testutils.Review.ReviewConstants.*;
import static com.scalefocus.midterm.trippyapp.testutils.Review.ReviewFactory.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class ReviewControllerImplTest {
    private MockMvc mockMvc;
    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewControllerImpl reviewController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(reviewController)
                .build();
    }

    @Test
    public void getAllReviews_noException_success() throws Exception {
        when(reviewService.getAllReviews()).thenReturn(Collections.singletonList(getDefaultReviewDto()));
        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(REVIEW_ID))
                .andExpect(jsonPath("$[0].username").value(REVIEW_USERNAME))
                .andExpect(jsonPath("$[0].rating").value(ReviewRating.values()[1].name()))
                .andExpect(jsonPath("$[0].text").value(REVIEW_TEXT))
                .andExpect(jsonPath("$[0].business.id").value(REVIEW_BUSINESS.getId()));
    }

    @Test
    public void getReviewById_noException_success() throws Exception {
        when(reviewService.getReviewById(REVIEW_ID)).thenReturn(getDefaultReviewDto());
        mockMvc.perform(get("/reviews/" + REVIEW_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(REVIEW_ID))
                .andExpect(jsonPath("$.username").value(REVIEW_USERNAME))
                .andExpect(jsonPath("$.rating").value(ReviewRating.values()[1].name()))
                .andExpect(jsonPath("$.text").value(REVIEW_TEXT))
                .andExpect(jsonPath("$.business.id").value(REVIEW_BUSINESS.getId()));
    }

    @Test
    public void addReview_noException_success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultReviewRequest());

        when(reviewService.createReview(any(), any())).thenReturn(getDefaultReview().getId());
        mockMvc.perform(MockMvcRequestBuilders.post("/reviews?username=" + REVIEW_USERNAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/reviews/1"));
    }

    @Test
    public void updateReview_noException_success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultReviewRequest());
        when(reviewService.editReview(any(), eq(REVIEW_ID.intValue()), eq(REVIEW_USERNAME))).thenReturn(getDefaultReviewDto());

        mockMvc.perform(put("/reviews/" + REVIEW_ID + "?username=" + REVIEW_USERNAME)
                        .queryParam("returnOld", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(REVIEW_USERNAME))
                .andExpect(jsonPath("$.rating").value(ReviewRating.values()[1].name()))
                .andExpect(jsonPath("$.text").value(REVIEW_TEXT))
                .andExpect(jsonPath("$.business.id").value(REVIEW_BUSINESS.getId()));

    }

    @Test
    public void deleteReview_noException_success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultReviewRequest());
        when(reviewService.deleteReview(eq(REVIEW_ID.intValue()), eq(REVIEW_USERNAME))).thenReturn(getDefaultReviewDto());

        mockMvc.perform(delete("/reviews/" + REVIEW_ID + "?username=" + REVIEW_USERNAME)
                        .queryParam("returnOld", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(REVIEW_USERNAME))
                .andExpect(jsonPath("$.rating").value(ReviewRating.values()[1].name()))
                .andExpect(jsonPath("$.text").value(REVIEW_TEXT))
                .andExpect(jsonPath("$.business.id").value(REVIEW_BUSINESS.getId()));

    }

}