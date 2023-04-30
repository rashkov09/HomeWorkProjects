package com.scalefocus.midterm.trippyapp.service;

import com.scalefocus.midterm.trippyapp.controller.request.BusinessRequest;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.model.dto.BusinessDto;

import java.util.List;

public interface BusinessService {
    Long createBusiness(BusinessRequest businessRequest);

    BusinessDto editBusiness(BusinessRequest businessRequest, Integer id);

    BusinessDto getBusinessById(Long id);

    BusinessDto getBusinessByEmail(String email);

    BusinessDto getBusinessByName(String name);

    BusinessDto getBusinessByNameAndCity(String name, String city);

    List<BusinessDto> getAllBusinesses();

    List<BusinessDto> getBusinessesByCity(String city);

    List<BusinessDto> getByBusinessType(String type);

    Boolean businessExists(Business business);


    void updateBusiness(Business business);
}
