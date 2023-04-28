package com.scalefocus.midterm.trippyapp.repository;

import com.scalefocus.midterm.trippyapp.constants.enums.BusinessType;
import com.scalefocus.midterm.trippyapp.model.Business;

import java.util.List;

public interface BusinessRepository extends CustomRepository<Business> {

    List<Business> getBusinessByCityName(String city);

    List<Business> getBusinessByType(BusinessType type);
}
