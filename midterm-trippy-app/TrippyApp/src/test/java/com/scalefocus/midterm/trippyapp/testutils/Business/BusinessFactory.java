package com.scalefocus.midterm.trippyapp.testutils.Business;

import com.scalefocus.midterm.trippyapp.controller.request.BusinessRequest;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.model.dto.BusinessDto;

import static com.scalefocus.midterm.trippyapp.testutils.Business.BusinessConstants.*;

public final class BusinessFactory {
    private BusinessFactory() {
        throw new IllegalStateException();
    }

    public static Business getDefaultBusiness() {
        return new Business(BUSINESS_ID, BUSINESS_NAME, BUSINESS_CITY, BUSINESS_TYPE, BUSINESS_ADDRESS, BUSINESS_EMAIL, BUSINESS_PHONE, BUSINESS_WEBSITE);
    }

    public static BusinessDto getDefaultBusinessDto() {
        return new BusinessDto(BUSINESS_ID, BUSINESS_NAME, BUSINESS_CITY, BUSINESS_TYPE, BUSINESS_NUMBER_OF_REVIEWS, BUSINESS_AVERAGE_RATING, BUSINESS_ADDRESS, BUSINESS_EMAIL, BUSINESS_PHONE, BUSINESS_WEBSITE);
    }

    public static BusinessRequest getDefaultBusinessRequest() {
        return new BusinessRequest(BUSINESS_NAME, BUSINESS_CITY, BUSINESS_TYPE, BUSINESS_ADDRESS, BUSINESS_EMAIL, BUSINESS_PHONE, BUSINESS_WEBSITE);
    }
}
