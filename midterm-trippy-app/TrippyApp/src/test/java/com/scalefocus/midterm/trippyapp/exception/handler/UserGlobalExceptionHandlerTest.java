package com.scalefocus.midterm.trippyapp.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.midterm.trippyapp.controller.ReviewController;
import com.scalefocus.midterm.trippyapp.controller.UserController;
import com.scalefocus.midterm.trippyapp.controller.impl.BusinessControllerImpl;
import com.scalefocus.midterm.trippyapp.controller.impl.ReviewControllerImpl;
import com.scalefocus.midterm.trippyapp.controller.impl.UserControllerImpl;
import com.scalefocus.midterm.trippyapp.controller.request.BusinessRequest;
import com.scalefocus.midterm.trippyapp.controller.request.ReviewRequest;
import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessAlreadyExistsException;
import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessNotFoundException;
import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessTypeNotFoundException;
import com.scalefocus.midterm.trippyapp.exception.ReviewExceptions.ReviewAccessDeniedException;
import com.scalefocus.midterm.trippyapp.exception.ReviewExceptions.ReviewNotFoundException;
import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserAlreadyExistsException;
import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserNotFoundException;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.model.Review;
import com.scalefocus.midterm.trippyapp.service.BusinessService;
import com.scalefocus.midterm.trippyapp.service.ReviewService;
import com.scalefocus.midterm.trippyapp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.scalefocus.midterm.trippyapp.testutils.Business.BusinessConstants.BUSINESS_ID;
import static com.scalefocus.midterm.trippyapp.testutils.Review.ReviewConstants.REVIEW_ID;
import static com.scalefocus.midterm.trippyapp.testutils.Review.ReviewConstants.REVIEW_USERNAME;
import static com.scalefocus.midterm.trippyapp.testutils.Review.ReviewFactory.getDefaultReviewRequest;
import static com.scalefocus.midterm.trippyapp.testutils.User.UserConstants.*;
import static com.scalefocus.midterm.trippyapp.testutils.User.UserFactory.getDefaultUserRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class UserGlobalExceptionHandlerTest {
    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @Mock
    private BusinessService businessService;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private UserControllerImpl userController;
    @InjectMocks
    private BusinessControllerImpl businessController;
    @InjectMocks
    private ReviewControllerImpl reviewController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController, businessController, reviewController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void handleUserAlreadyExists() throws Exception {
        when(userService.createUser(any())).thenThrow(new UserAlreadyExistsException(USER_USERNAME, USER_EMAIL));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultUserRequest());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Errors[0]").exists());

    }

    @Test
    public void handleBusinessAlreadyExists() throws Exception {
        when(businessService.createBusiness(any())).thenThrow(new BusinessAlreadyExistsException("string"));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultUserRequest());

        mockMvc.perform(post("/businesses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Errors[0]").exists());

    }

    @Test
    public void handleUserNotFoundException() throws Exception {
        when(userService.getUserById(any())).thenThrow(new UserNotFoundException("string"));

        mockMvc.perform(get("/users/" + USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.Errors[0]").exists());

    }

    @Test
    public void handleBusinessNotFoundException() throws Exception {
        when(businessService.getBusinessById(any())).thenThrow(new BusinessNotFoundException("string"));
        mockMvc.perform(get("/businesses/" + BUSINESS_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.Errors[0]").exists());
    }

    @Test
    public void handleReviewNotFoundException() throws Exception {
        when(reviewService.getReviewById(any())).thenThrow(new ReviewNotFoundException());

        mockMvc.perform(get("/reviews/" + REVIEW_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.Errors[0]").exists());

    }


    @Test
    public void handleControllerValidationException_invalidUserRequest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserRequest erroneousUserRequest = new UserRequest("dasdad", "Dasdada", "sdsadsa", "asdasd", "sdadsa");
        String json = mapper.writeValueAsString(erroneousUserRequest);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Errors[0]").exists());
    }

    @Test
    public void handleControllerValidationException_invalidBusinessRequest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BusinessRequest erroneousBusinessRequest = new BusinessRequest("Dasd", "sdads", "STREET", "dasdsa", "dasdads", "asdasd", "asdadsa");
        String json = mapper.writeValueAsString(erroneousBusinessRequest);

        mockMvc.perform(post("/businesses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Errors[0]").exists());
    }

    @Test
    public void handleControllerValidationException_invalidReviewRequest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ReviewRequest erroneousReviewRequest = new ReviewRequest("0", "sad", 1L);
        String json = mapper.writeValueAsString(erroneousReviewRequest);

        mockMvc.perform(post("/reviews/?username=" + REVIEW_USERNAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Errors[0]").exists());
    }

    @Test
    public void handleReviewAccessDeniedException_whenDelete() throws Exception {
        when(reviewService.deleteReview(any(), any())).thenThrow(new ReviewAccessDeniedException());
        mockMvc.perform(delete("/reviews/" + REVIEW_ID + "?username=" + REVIEW_USERNAME))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.Errors[0]").exists());
    }

    @Test
    public void handleReviewAccessDeniedException_whenEdit() throws Exception {
        when(reviewService.editReview(any(), any(), any())).thenThrow(new ReviewAccessDeniedException());
        ObjectMapper mapper = new ObjectMapper();
        ReviewRequest reviewRequest = getDefaultReviewRequest();
        String json = mapper.writeValueAsString(reviewRequest);

        mockMvc.perform(put("/reviews/" + REVIEW_ID + "?username=" + REVIEW_USERNAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.Errors[0]").exists());
    }

}