package com.scalefocus.midterm.trippyapp.mapper;

import com.scalefocus.midterm.trippyapp.constants.enums.BusinessType;
import com.scalefocus.midterm.trippyapp.controller.request.BusinessRequest;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.model.dto.BusinessDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusinessMapper implements RowMapper<Business> {
    public BusinessDto mapToDto(Business business) {
        BusinessDto businessDto = new BusinessDto();
        businessDto.setId(business.getId());
        businessDto.setName(business.getName());
        businessDto.setCity(business.getCity());
        businessDto.setBusinessType(business.getBusinessType());
        businessDto.setAverageRating(business.getAverageRate());
        businessDto.setNumberOfReviews(business.getNumberOfReviews());
        businessDto.setAddress(business.getAddress());
        businessDto.setEmail(business.getEmail());
        businessDto.setWebsite(business.getWebsite());
        businessDto.setPhone(business.getPhone());


        return businessDto;
    }

    public Business mapFromDto(BusinessDto businessDto) {
        Business business = new Business();
        business.setId(businessDto.getId());
        business.setName(businessDto.getName());
        business.setCity(businessDto.getCity());
        business.setBusinessType(businessDto.getBusinessType());
        business.setAverageRate(businessDto.getAverageRating());
        business.setNumberOfReviews(businessDto.getNumberOfReviews());
        business.setAddress(businessDto.getAddress());
        business.setEmail(businessDto.getEmail());
        business.setWebsite(businessDto.getWebsite());
        business.setPhone(businessDto.getPhone());


        return business;
    }


    public Business mapFromRequest(BusinessRequest businessRequest) {
        Business business = new Business();
        business.setName(businessRequest.getName());
        business.setCity(businessRequest.getCity());
        business.setBusinessType(BusinessType.valueOf(businessRequest.getBusinessType()));
        business.setAddress(businessRequest.getAddress());
        business.setEmail(businessRequest.getEmail());
        business.setPhone(businessRequest.getPhone());
        business.setWebsite(businessRequest.getWebsite());
        return business;
    }

    @Override
    public Business mapRow(ResultSet rs, int rowNum) throws SQLException {
        Business business = new Business();
        business.setId(rs.getLong("id"));
        business.setName(rs.getString("name"));
        business.setCity(rs.getString("city"));
        business.setBusinessType(BusinessType.valueOf(rs.getString("business_type")));
        business.setNumberOfReviews(rs.getInt("number_of_reviews"));
        business.setAverageRate(rs.getDouble("average_rating"));
        business.setAddress(rs.getString("address"));
        business.setEmail(rs.getString("email"));
        business.setPhone(rs.getString("phone"));
        business.setWebsite(rs.getString("website"));
        return business;
    }
}
