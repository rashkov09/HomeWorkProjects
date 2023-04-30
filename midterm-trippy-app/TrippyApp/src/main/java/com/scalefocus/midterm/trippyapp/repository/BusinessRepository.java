package com.scalefocus.midterm.trippyapp.repository;

import com.scalefocus.midterm.trippyapp.constants.enums.BusinessType;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.model.dto.BusinessDto;

import java.util.List;

public interface BusinessRepository extends CustomRepository<Business> {

    List<Business> getBusinessByCityName(String city);

    List<Business> getBusinessByType(BusinessType type);

    Business getByName(String name);

    Business getByNameAndCity(String name, String city);

    void update(Business business);

    Business getByEmail(String email);

    List<BusinessDto> getBusinessByRateBiggerThan(Double averageRate);

    List<BusinessDto> getBusinessByRateLowerThan(Double averageRate);
}
