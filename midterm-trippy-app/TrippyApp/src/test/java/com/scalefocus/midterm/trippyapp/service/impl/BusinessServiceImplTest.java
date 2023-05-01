package com.scalefocus.midterm.trippyapp.service.impl;

import com.scalefocus.midterm.trippyapp.constants.enums.BusinessType;
import com.scalefocus.midterm.trippyapp.controller.request.BusinessRequest;
import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessAlreadyExistsException;
import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessNotFoundException;
import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessTypeNotFoundException;
import com.scalefocus.midterm.trippyapp.exception.NoDataFoundException;
import com.scalefocus.midterm.trippyapp.mapper.BusinessMapper;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.model.dto.BusinessDto;
import com.scalefocus.midterm.trippyapp.repository.impl.BusinessRepositoryImpl;
import com.scalefocus.midterm.trippyapp.util.ObjectChecker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static com.scalefocus.midterm.trippyapp.testutils.Business.BusinessConstants.*;
import static com.scalefocus.midterm.trippyapp.testutils.Business.BusinessFactory.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BusinessServiceImplTest {
    @Mock
    private BusinessRepositoryImpl businessRepository;

    @Mock
    private BusinessMapper businessMapper;

    @Mock
    private ObjectChecker<BusinessRequest> businessObjectChecker;

    @InjectMocks
    private BusinessServiceImpl businessService;

    @Test
    public void createBusiness_noException_returnsId() throws Exception {
        when(businessRepository.add(any())).thenReturn(BUSINESS_ID);
        when(businessMapper.mapFromRequest(any())).thenReturn(getDefaultBusiness());
        Long id = businessService.createBusiness(getDefaultBusinessRequest());

        Assert.assertEquals(BUSINESS_ID, id);
    }

    @Test
    public void editBusiness_noException_returnsUpdatedBusinessDto() throws Exception {
        Business updatedBusiness = getDefaultBusiness();
        updatedBusiness.setName("Test2");
        updatedBusiness.setCity("Test3");
        updatedBusiness.setAddress("tamantam");
        updatedBusiness.setEmail("tamantam@asdadd.com");
        BusinessDto updatedBusinessDto = getDefaultBusinessDto();
        updatedBusinessDto.setName("Test2");
        updatedBusinessDto.setCity("Test3");
        updatedBusinessDto.setAddress("tamantam");
        updatedBusinessDto.setEmail("tamantam@asdadd.com");
        when(businessMapper.mapFromRequest(any())).thenReturn(updatedBusiness);
        when(businessRepository.edit(any(), any())).thenReturn(updatedBusiness);
        when(businessMapper.mapToDto(any())).thenReturn(updatedBusinessDto);
        BusinessDto businessDto = businessService.editBusiness(getDefaultBusinessRequest(), BUSINESS_ID.intValue());
        assertEquals(businessDto.getName(), updatedBusiness.getName());
        assertEquals(businessDto.getCity(), updatedBusiness.getCity());
        assertEquals(businessDto.getAddress(), updatedBusiness.getAddress());
        assertEquals(businessDto.getEmail(), updatedBusiness.getEmail());
    }

    @Test
    public void getBusinessById_noException_success() {
        when(businessRepository.getById(any())).thenReturn(getDefaultBusiness());
        when(businessMapper.mapToDto(any())).thenReturn(getDefaultBusinessDto());
        BusinessDto businessDto = businessService.getBusinessById(BUSINESS_ID);
        Assert.assertEquals(BUSINESS_ID, businessDto.getId());
        Assert.assertEquals(BUSINESS_NAME, businessDto.getName());
        Assert.assertEquals(BUSINESS_CITY, businessDto.getCity());
        Assert.assertEquals(BusinessType.valueOf(BUSINESS_TYPE), businessDto.getBusinessType());
        Assert.assertEquals(BUSINESS_AVERAGE_RATING, businessDto.getAverageRating());
        Assert.assertEquals(BUSINESS_NUMBER_OF_REVIEWS, businessDto.getNumberOfReviews());
        Assert.assertEquals(BUSINESS_ADDRESS, businessDto.getAddress());
        Assert.assertEquals(BUSINESS_EMAIL, businessDto.getEmail());
        Assert.assertEquals(BUSINESS_PHONE, businessDto.getPhone());
        Assert.assertEquals(BUSINESS_WEBSITE, businessDto.getWebsite());
    }

    @Test
    public void getBusinessByEmail_noException_success() {
        when(businessRepository.getByEmail(any())).thenReturn(getDefaultBusiness());
        when(businessMapper.mapToDto(any())).thenReturn(getDefaultBusinessDto());
        BusinessDto businessDto = businessService.getBusinessByEmail(BUSINESS_EMAIL);
        Assert.assertEquals(BUSINESS_ID, businessDto.getId());
        Assert.assertEquals(BUSINESS_NAME, businessDto.getName());
        Assert.assertEquals(BUSINESS_CITY, businessDto.getCity());
        Assert.assertEquals(BusinessType.valueOf(BUSINESS_TYPE), businessDto.getBusinessType());
        Assert.assertEquals(BUSINESS_AVERAGE_RATING, businessDto.getAverageRating());
        Assert.assertEquals(BUSINESS_NUMBER_OF_REVIEWS, businessDto.getNumberOfReviews());
        Assert.assertEquals(BUSINESS_ADDRESS, businessDto.getAddress());
        Assert.assertEquals(BUSINESS_EMAIL, businessDto.getEmail());
        Assert.assertEquals(BUSINESS_PHONE, businessDto.getPhone());
        Assert.assertEquals(BUSINESS_WEBSITE, businessDto.getWebsite());
    }

    @Test
    public void getBusinessByName_noException_success() {
        when(businessRepository.getByName(any())).thenReturn(getDefaultBusiness());
        when(businessMapper.mapToDto(any())).thenReturn(getDefaultBusinessDto());
        BusinessDto businessDto = businessService.getBusinessByName(BUSINESS_NAME);
        Assert.assertEquals(BUSINESS_ID, businessDto.getId());
        Assert.assertEquals(BUSINESS_NAME, businessDto.getName());
        Assert.assertEquals(BUSINESS_CITY, businessDto.getCity());
        Assert.assertEquals(BusinessType.valueOf(BUSINESS_TYPE), businessDto.getBusinessType());
        Assert.assertEquals(BUSINESS_AVERAGE_RATING, businessDto.getAverageRating());
        Assert.assertEquals(BUSINESS_NUMBER_OF_REVIEWS, businessDto.getNumberOfReviews());
        Assert.assertEquals(BUSINESS_ADDRESS, businessDto.getAddress());
        Assert.assertEquals(BUSINESS_EMAIL, businessDto.getEmail());
        Assert.assertEquals(BUSINESS_PHONE, businessDto.getPhone());
        Assert.assertEquals(BUSINESS_WEBSITE, businessDto.getWebsite());
    }

    @Test
    public void getBusinessByNameAndCity_noException_success() {
        when(businessRepository.getByNameAndCity(any(), any())).thenReturn(getDefaultBusiness());
        when(businessMapper.mapToDto(any())).thenReturn(getDefaultBusinessDto());
        BusinessDto businessDto = businessService.getBusinessByNameAndCity(BUSINESS_NAME, BUSINESS_CITY);
        Assert.assertEquals(BUSINESS_ID, businessDto.getId());
        Assert.assertEquals(BUSINESS_NAME, businessDto.getName());
        Assert.assertEquals(BUSINESS_CITY, businessDto.getCity());
        Assert.assertEquals(BusinessType.valueOf(BUSINESS_TYPE), businessDto.getBusinessType());
        Assert.assertEquals(BUSINESS_AVERAGE_RATING, businessDto.getAverageRating());
        Assert.assertEquals(BUSINESS_NUMBER_OF_REVIEWS, businessDto.getNumberOfReviews());
        Assert.assertEquals(BUSINESS_ADDRESS, businessDto.getAddress());
        Assert.assertEquals(BUSINESS_EMAIL, businessDto.getEmail());
        Assert.assertEquals(BUSINESS_PHONE, businessDto.getPhone());
        Assert.assertEquals(BUSINESS_WEBSITE, businessDto.getWebsite());
    }

    @Test
    public void getAllBusinesses_noException_success() {
        when(businessRepository.getAll()).thenReturn(Collections.singletonList(getDefaultBusiness()));
        when(businessMapper.mapToDto(any())).thenReturn(getDefaultBusinessDto());
        List<BusinessDto> businesses = businessService.getAllBusinesses();
        Assert.assertEquals(BUSINESS_ID, businesses.get(0).getId());
        Assert.assertEquals(BUSINESS_NAME, businesses.get(0).getName());
        Assert.assertEquals(BUSINESS_CITY, businesses.get(0).getCity());
        Assert.assertEquals(BusinessType.valueOf(BUSINESS_TYPE), businesses.get(0).getBusinessType());
        Assert.assertEquals(BUSINESS_AVERAGE_RATING, businesses.get(0).getAverageRating());
        Assert.assertEquals(BUSINESS_NUMBER_OF_REVIEWS, businesses.get(0).getNumberOfReviews());
        Assert.assertEquals(BUSINESS_ADDRESS, businesses.get(0).getAddress());
        Assert.assertEquals(BUSINESS_EMAIL, businesses.get(0).getEmail());
        Assert.assertEquals(BUSINESS_PHONE, businesses.get(0).getPhone());
        Assert.assertEquals(BUSINESS_WEBSITE, businesses.get(0).getWebsite());
    }

    @Test
    public void getBusinessesByCity_noException_success() {
        when(businessRepository.getBusinessByCityName(any())).thenReturn(Collections.singletonList(getDefaultBusiness()));
        when(businessMapper.mapToDto(any())).thenReturn(getDefaultBusinessDto());
        List<BusinessDto> businessDto = businessService.getBusinessesByCity(BUSINESS_CITY);
        Assert.assertEquals(BUSINESS_ID, businessDto.get(0).getId());
        Assert.assertEquals(BUSINESS_NAME, businessDto.get(0).getName());
        Assert.assertEquals(BUSINESS_CITY, businessDto.get(0).getCity());
        Assert.assertEquals(BusinessType.valueOf(BUSINESS_TYPE), businessDto.get(0).getBusinessType());
        Assert.assertEquals(BUSINESS_AVERAGE_RATING, businessDto.get(0).getAverageRating());
        Assert.assertEquals(BUSINESS_NUMBER_OF_REVIEWS, businessDto.get(0).getNumberOfReviews());
        Assert.assertEquals(BUSINESS_ADDRESS, businessDto.get(0).getAddress());
        Assert.assertEquals(BUSINESS_EMAIL, businessDto.get(0).getEmail());
        Assert.assertEquals(BUSINESS_PHONE, businessDto.get(0).getPhone());
        Assert.assertEquals(BUSINESS_WEBSITE, businessDto.get(0).getWebsite());
    }

    @Test
    public void getByBusinessType_noException_success() {
        when(businessRepository.getBusinessByType(any())).thenReturn(Collections.singletonList(getDefaultBusiness()));
        when(businessMapper.mapToDto(any())).thenReturn(getDefaultBusinessDto());
        List<BusinessDto> businessDto = businessService.getByBusinessType(BUSINESS_TYPE);
        Assert.assertEquals(BUSINESS_ID, businessDto.get(0).getId());
        Assert.assertEquals(BUSINESS_NAME, businessDto.get(0).getName());
        Assert.assertEquals(BUSINESS_CITY, businessDto.get(0).getCity());
        Assert.assertEquals(BusinessType.valueOf(BUSINESS_TYPE), businessDto.get(0).getBusinessType());
        Assert.assertEquals(BUSINESS_AVERAGE_RATING, businessDto.get(0).getAverageRating());
        Assert.assertEquals(BUSINESS_NUMBER_OF_REVIEWS, businessDto.get(0).getNumberOfReviews());
        Assert.assertEquals(BUSINESS_ADDRESS, businessDto.get(0).getAddress());
        Assert.assertEquals(BUSINESS_EMAIL, businessDto.get(0).getEmail());
        Assert.assertEquals(BUSINESS_PHONE, businessDto.get(0).getPhone());
        Assert.assertEquals(BUSINESS_WEBSITE, businessDto.get(0).getWebsite());
    }

    @Test
    public void getByBusinessRateBiggerThan_noException_success() {
        when(businessRepository.getBusinessByRateBiggerThan(any())).thenReturn(Collections.singletonList(getDefaultBusinessDto()));
        List<BusinessDto> businessDto = businessService.getBusinessesByRate(2.00, "biggerThan");
        Assert.assertEquals(BUSINESS_ID, businessDto.get(0).getId());
        Assert.assertEquals(BUSINESS_NAME, businessDto.get(0).getName());
        Assert.assertEquals(BUSINESS_CITY, businessDto.get(0).getCity());
        Assert.assertEquals(BusinessType.valueOf(BUSINESS_TYPE), businessDto.get(0).getBusinessType());
        Assert.assertEquals(BUSINESS_AVERAGE_RATING, businessDto.get(0).getAverageRating());
        Assert.assertEquals(BUSINESS_NUMBER_OF_REVIEWS, businessDto.get(0).getNumberOfReviews());
        Assert.assertEquals(BUSINESS_ADDRESS, businessDto.get(0).getAddress());
        Assert.assertEquals(BUSINESS_EMAIL, businessDto.get(0).getEmail());
        Assert.assertEquals(BUSINESS_PHONE, businessDto.get(0).getPhone());
        Assert.assertEquals(BUSINESS_WEBSITE, businessDto.get(0).getWebsite());
    }

    @Test
    public void getByBusinessRateLowerThan_noException_success() {
        when(businessRepository.getBusinessByRateLowerThan(any())).thenReturn(Collections.singletonList(getDefaultBusinessDto()));
        List<BusinessDto> businessDto = businessService.getBusinessesByRate(4.00, "lowerThan");
        Assert.assertEquals(BUSINESS_ID, businessDto.get(0).getId());
        Assert.assertEquals(BUSINESS_NAME, businessDto.get(0).getName());
        Assert.assertEquals(BUSINESS_CITY, businessDto.get(0).getCity());
        Assert.assertEquals(BusinessType.valueOf(BUSINESS_TYPE), businessDto.get(0).getBusinessType());
        Assert.assertEquals(BUSINESS_AVERAGE_RATING, businessDto.get(0).getAverageRating());
        Assert.assertEquals(BUSINESS_NUMBER_OF_REVIEWS, businessDto.get(0).getNumberOfReviews());
        Assert.assertEquals(BUSINESS_ADDRESS, businessDto.get(0).getAddress());
        Assert.assertEquals(BUSINESS_EMAIL, businessDto.get(0).getEmail());
        Assert.assertEquals(BUSINESS_PHONE, businessDto.get(0).getPhone());
        Assert.assertEquals(BUSINESS_WEBSITE, businessDto.get(0).getWebsite());
    }

    @Test(expected = NoDataFoundException.class)
    public void getByBusinessRateLowerThan_withException_throws() {
        businessService.getBusinessesByRate(1.00, "lowerThan");
    }

    @Test(expected = NoDataFoundException.class)
    public void getByBusinessRateBiggerThan_withException_throws() {
        businessService.getBusinessesByRate(4.00, "biggerThan");
    }

    @Test
    public void businessExists_noException_exists() {
        when(businessRepository.getByNameAndCity(any(), any())).thenReturn(getDefaultBusiness());
        Business search = businessRepository.getByNameAndCity(BUSINESS_NAME, BUSINESS_CITY);
        assertTrue(businessService.businessExists(search));
    }

    @Test(expected = BusinessTypeNotFoundException.class)
    public void getByBusinessTypeNotFound_throwsException_throws() {
        when(businessService.getByBusinessType("CINEMA")).thenThrow(new BusinessTypeNotFoundException(""));
        businessService.getByBusinessType("CINEMA");

    }

    @Test(expected = BusinessNotFoundException.class)
    public void getByBusinessNoData_throwsException_throws() {
        when(businessService.getByBusinessType(BUSINESS_TYPE)).thenReturn(Collections.emptyList());
        businessService.getByBusinessType("BAR");
    }

    @Test(expected = BusinessNotFoundException.class)
    public void getBusinessesByCity_throwsException_throws() {
        when(businessService.getBusinessesByCity(BUSINESS_CITY)).thenReturn(Collections.emptyList());
        businessService.getBusinessesByCity(BUSINESS_CITY);
    }

    @Test(expected = BusinessNotFoundException.class)
    public void getBusinessByNameAndCity_throwsException_throws() {
        when(businessService.getBusinessByNameAndCity(BUSINESS_NAME, BUSINESS_CITY)).thenReturn(null);
        businessService.getBusinessByNameAndCity(BUSINESS_NAME, BUSINESS_CITY);
    }

    @Test(expected = BusinessNotFoundException.class)
    public void getBusinessByName_throwsException_throws() {
        when(businessService.getBusinessByName(BUSINESS_NAME)).thenReturn(null);
        businessService.getBusinessByName(BUSINESS_NAME);
    }

    @Test(expected = BusinessNotFoundException.class)
    public void getBusinessByEmail_throwsException_throws() {
        when(businessService.getBusinessByEmail(BUSINESS_EMAIL)).thenReturn(null);
        businessService.getBusinessByName(BUSINESS_EMAIL);
    }

    @Test(expected = BusinessNotFoundException.class)
    public void getBusinessById_throwsException_throws() {
        when(businessService.getBusinessById(BUSINESS_ID)).thenReturn(null);
        businessService.getBusinessById(BUSINESS_ID);
    }

    @Test(expected = BusinessAlreadyExistsException.class)
    public void createBusiness_throwsException_throws() {
        when(businessObjectChecker.checkForMissingFields(any())).thenReturn(false);
        when(businessRepository.getByNameAndCity(any(), any())).thenReturn(getDefaultBusiness());
        when(businessMapper.mapFromRequest(any())).thenReturn(getDefaultBusiness());
        businessService.createBusiness(getDefaultBusinessRequest());
    }

    @Test(expected = BusinessAlreadyExistsException.class)
    public void editBusiness_throwsException_throws() {
        when(businessObjectChecker.checkForMissingFields(any())).thenReturn(false);
        when(businessRepository.getByNameAndCity(any(), any())).thenReturn(getDefaultBusiness());
        when(businessMapper.mapFromRequest(any())).thenReturn(getDefaultBusiness());
        businessService.editBusiness(getDefaultBusinessRequest(), BUSINESS_ID.intValue());
    }

    @Test(expected = NoDataFoundException.class)
    public void getAllBusinesses_throwsException_throws() {
        when(businessService.getAllBusinesses()).thenReturn(Collections.emptyList());
        businessService.getAllBusinesses();
    }


}