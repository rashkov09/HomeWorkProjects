package com.scalefocus.midterm.trippyapp.service;

import com.scalefocus.midterm.trippyapp.controller.request.BusinessRequest;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.model.dto.BusinessDto;

import java.util.List;

public interface BusinessService {
    Long createBusiness(BusinessRequest businessRequest);

    BusinessDto editBusiness(BusinessRequest businessRequest, Integer id);

    BusinessDto getBusinessById(Long id);

    List<BusinessDto> getAllBusinesses();

    List<BusinessDto> getBusinessByCity(String city);

    List<BusinessDto> getByBusinessType(String type);

    Boolean businessExists(Business business);
}
