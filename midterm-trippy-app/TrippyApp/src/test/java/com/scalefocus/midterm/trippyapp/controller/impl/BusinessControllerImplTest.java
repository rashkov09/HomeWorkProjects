package com.scalefocus.midterm.trippyapp.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.midterm.trippyapp.controller.request.BusinessRequest;
import com.scalefocus.midterm.trippyapp.mapper.BusinessMapper;
import com.scalefocus.midterm.trippyapp.model.dto.BusinessDto;
import com.scalefocus.midterm.trippyapp.service.BusinessService;
import org.junit.Assert;
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

import static com.scalefocus.midterm.trippyapp.testutils.Business.BusinessConstants.*;
import static com.scalefocus.midterm.trippyapp.testutils.Business.BusinessFactory.*;
import static com.scalefocus.midterm.trippyapp.testutils.User.UserConstants.USER_ID;
import static com.scalefocus.midterm.trippyapp.testutils.User.UserFactory.getDefaultUserDto;
import static com.scalefocus.midterm.trippyapp.testutils.User.UserFactory.getDefaultUserRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class BusinessControllerImplTest {
   private MockMvc mockMvc;
   @Mock
   private  BusinessService businessService;
   @InjectMocks
   private BusinessControllerImpl businessController;
   @Before
   public void setUp() {
      mockMvc = MockMvcBuilders
              .standaloneSetup(businessController)
              .build();
   }

   @Test
    public void getAllBusinesses_noException_success() throws Exception {
       BusinessDto businessDto = getDefaultBusinessDto();
       when(businessService.getAllBusinesses()).thenReturn(Collections.singletonList(businessDto));

       mockMvc.perform(get("/businesses"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(BUSINESS_ID))
               .andExpect(jsonPath("$[0].name").value(BUSINESS_NAME))
               .andExpect(jsonPath("$[0].city").value(BUSINESS_CITY))
               .andExpect(jsonPath("$[0].businessType").value("BAR"))
               .andExpect(jsonPath("$[0].averageRating").value(BUSINESS_AVERAGE_RATING))
               .andExpect(jsonPath("$[0].numberOfReviews").value(BUSINESS_NUMBER_OF_REVIEWS))
               .andExpect(jsonPath("$[0].address").value(BUSINESS_ADDRESS))
               .andExpect(jsonPath("$[0].email").value(BUSINESS_EMAIL))
               .andExpect(jsonPath("$[0].phone").value(BUSINESS_PHONE))
               .andExpect(jsonPath("$[0].website").value(BUSINESS_WEBSITE));
    }

    @Test
    public void getAllBusinesses_noException_emptyList() throws Exception{
        when(businessService.getAllBusinesses()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/businesses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }


    @Test
    public void getBusinessById_noException_success() throws Exception {
        when(businessService.getBusinessById(BUSINESS_ID)).thenReturn(getDefaultBusinessDto());

        mockMvc.perform(get("/businesses/" + BUSINESS_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(BUSINESS_NAME))
                .andExpect(jsonPath("$.city").value(BUSINESS_CITY))
                .andExpect(jsonPath("$.businessType").value("BAR"))
                .andExpect(jsonPath("$.averageRating").value(BUSINESS_AVERAGE_RATING))
                .andExpect(jsonPath("$.numberOfReviews").value(BUSINESS_NUMBER_OF_REVIEWS))
                .andExpect(jsonPath("$.address").value(BUSINESS_ADDRESS))
                .andExpect(jsonPath("$.email").value(BUSINESS_EMAIL))
                .andExpect(jsonPath("$.phone").value(BUSINESS_PHONE))
                .andExpect(jsonPath("$.website").value(BUSINESS_WEBSITE));
    }

    @Test
    public void getBusinessByCity_noException_success() throws Exception{
        when(businessService.getBusinessesByCity(BUSINESS_CITY)).thenReturn(Collections.singletonList(getDefaultBusinessDto()));

        mockMvc.perform(get("/businesses?city=" + BUSINESS_CITY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value(BUSINESS_NAME))
                .andExpect(jsonPath("$[0].city").value(BUSINESS_CITY))
                .andExpect(jsonPath("$[0].businessType").value("BAR"))
                .andExpect(jsonPath("$[0].averageRating").value(BUSINESS_AVERAGE_RATING))
                .andExpect(jsonPath("$[0].numberOfReviews").value(BUSINESS_NUMBER_OF_REVIEWS))
                .andExpect(jsonPath("$[0].address").value(BUSINESS_ADDRESS))
                .andExpect(jsonPath("$[0].email").value(BUSINESS_EMAIL))
                .andExpect(jsonPath("$[0].phone").value(BUSINESS_PHONE))
                .andExpect(jsonPath("$[0].website").value(BUSINESS_WEBSITE));
    }

    @Test
    public void getByBusinessType_noException_success() throws Exception{
        when(businessService.getByBusinessType ("BAR")).thenReturn(Collections.singletonList(getDefaultBusinessDto()));

        mockMvc.perform(get("/businesses?type=" + BUSINESS_TYPE.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value(BUSINESS_NAME))
                .andExpect(jsonPath("$[0].city").value(BUSINESS_CITY))
                .andExpect(jsonPath("$[0].businessType").value("BAR"))
                .andExpect(jsonPath("$[0].averageRating").value(BUSINESS_AVERAGE_RATING))
                .andExpect(jsonPath("$[0].numberOfReviews").value(BUSINESS_NUMBER_OF_REVIEWS))
                .andExpect(jsonPath("$[0].address").value(BUSINESS_ADDRESS))
                .andExpect(jsonPath("$[0].email").value(BUSINESS_EMAIL))
                .andExpect(jsonPath("$[0].phone").value(BUSINESS_PHONE))
                .andExpect(jsonPath("$[0].website").value(BUSINESS_WEBSITE));
    }

    @Test
    public void getByBusinessEmail_noException_success() throws Exception {
        when(businessService.getBusinessByEmail(BUSINESS_EMAIL)).thenReturn(getDefaultBusinessDto());

        mockMvc.perform(get("/businesses?email=" + BUSINESS_EMAIL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(BUSINESS_NAME))
                .andExpect(jsonPath("$.city").value(BUSINESS_CITY))
                .andExpect(jsonPath("$.businessType").value("BAR"))
                .andExpect(jsonPath("$.averageRating").value(BUSINESS_AVERAGE_RATING))
                .andExpect(jsonPath("$.numberOfReviews").value(BUSINESS_NUMBER_OF_REVIEWS))
                .andExpect(jsonPath("$.address").value(BUSINESS_ADDRESS))
                .andExpect(jsonPath("$.email").value(BUSINESS_EMAIL))
                .andExpect(jsonPath("$.phone").value(BUSINESS_PHONE))
                .andExpect(jsonPath("$.website").value(BUSINESS_WEBSITE));
    }

    @Test
    public void getByBusinessName_noException_success() throws Exception{
        when(businessService.getBusinessByName(BUSINESS_NAME)).thenReturn(getDefaultBusinessDto());

        mockMvc.perform(get("/businesses?name=" + BUSINESS_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(BUSINESS_NAME))
                .andExpect(jsonPath("$.city").value(BUSINESS_CITY))
                .andExpect(jsonPath("$.businessType").value("BAR"))
                .andExpect(jsonPath("$.averageRating").value(BUSINESS_AVERAGE_RATING))
                .andExpect(jsonPath("$.numberOfReviews").value(BUSINESS_NUMBER_OF_REVIEWS))
                .andExpect(jsonPath("$.address").value(BUSINESS_ADDRESS))
                .andExpect(jsonPath("$.email").value(BUSINESS_EMAIL))
                .andExpect(jsonPath("$.phone").value(BUSINESS_PHONE))
                .andExpect(jsonPath("$.website").value(BUSINESS_WEBSITE));
    }

    @Test
    public void getByBusinessNameAndCity_noException_success() throws Exception {
        when(businessService.getBusinessByNameAndCity(BUSINESS_NAME,BUSINESS_CITY)).thenReturn(getDefaultBusinessDto());

        mockMvc.perform(get("/businesses?name=" + BUSINESS_NAME+"&city="+ BUSINESS_CITY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(BUSINESS_NAME))
                .andExpect(jsonPath("$.city").value(BUSINESS_CITY))
                .andExpect(jsonPath("$.businessType").value("BAR"))
                .andExpect(jsonPath("$.averageRating").value(BUSINESS_AVERAGE_RATING))
                .andExpect(jsonPath("$.numberOfReviews").value(BUSINESS_NUMBER_OF_REVIEWS))
                .andExpect(jsonPath("$.address").value(BUSINESS_ADDRESS))
                .andExpect(jsonPath("$.email").value(BUSINESS_EMAIL))
                .andExpect(jsonPath("$.phone").value(BUSINESS_PHONE))
                .andExpect(jsonPath("$.website").value(BUSINESS_WEBSITE));
    }

    @Test
    public void addBusiness_noException_success() {
       when(businessService.createBusiness(any())).thenReturn(BUSINESS_ID);
        Assert.assertEquals(BUSINESS_ID,businessService.createBusiness(any()));
    }

    @Test
    public  void addBusinessSecond_noException_success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(getDefaultBusinessRequest());

        when(businessService.createBusiness(any())).thenReturn(getDefaultBusiness().getId());
        mockMvc.perform(MockMvcRequestBuilders.post("/businesses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/businesses/1"));
    }

    @Test
    public void updateBusiness_noException_success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultUserRequest());

        when(businessService.editBusiness(any(), eq(BUSINESS_ID.intValue()))).thenReturn(getDefaultBusinessDto());

        mockMvc.perform(put("/businesses/" + BUSINESS_ID)
                        .queryParam("returnOld", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(BUSINESS_NAME))
                .andExpect(jsonPath("$.city").value(BUSINESS_CITY))
                .andExpect(jsonPath("$.businessType").value("BAR"))
                .andExpect(jsonPath("$.averageRating").value(BUSINESS_AVERAGE_RATING))
                .andExpect(jsonPath("$.numberOfReviews").value(BUSINESS_NUMBER_OF_REVIEWS))
                .andExpect(jsonPath("$.address").value(BUSINESS_ADDRESS))
                .andExpect(jsonPath("$.email").value(BUSINESS_EMAIL))
                .andExpect(jsonPath("$.phone").value(BUSINESS_PHONE))
                .andExpect(jsonPath("$.website").value(BUSINESS_WEBSITE));

    }
}