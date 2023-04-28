package com.scalefocus.midterm.trippyapp.service.impl;

import com.scalefocus.midterm.trippyapp.constants.enums.BusinessType;
import com.scalefocus.midterm.trippyapp.controller.request.BusinessRequest;
import com.scalefocus.midterm.trippyapp.exception.NoDataFoundException;
import com.scalefocus.midterm.trippyapp.mapper.BusinessMapper;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.model.dto.BusinessDto;
import com.scalefocus.midterm.trippyapp.repository.CustomRepository;
import com.scalefocus.midterm.trippyapp.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {
    private static final Logger log = LoggerFactory.getLogger(BusinessServiceImpl.class);
    private final CustomRepository<Business> businessCustomRepository;
    private final BusinessMapper businessMapper;

    @Autowired
    public BusinessServiceImpl(CustomRepository<Business> businessCustomRepository, BusinessMapper businessMapper) {
        this.businessCustomRepository = businessCustomRepository;
        this.businessMapper = businessMapper;
    }

    @Override
    public Long createBusiness(BusinessRequest businessRequest) {
        Business business = businessMapper.mapFromRequest(businessRequest);
//        if (businessExists(business)) {
//
//        }
        try {
            Long id = businessCustomRepository.add(business);
            log.info("success");
            return id;
        } catch (SQLException e) {
            throw new RuntimeException("Unexpected error", e);
        }
    }

    @Override
    public BusinessDto editBusiness(BusinessRequest businessRequest, Integer id) {
        return null;
    }

    @Override
    public BusinessDto getBusinessById(Long id) {
        return null;
    }

    @Override
    public List<BusinessDto> getAllBusinesses() {
        List<BusinessDto> businessDtos = businessCustomRepository.getAll().stream().map(businessMapper::mapToDto).toList();
        if (businessDtos.isEmpty()) {
            log.error("No data found in database!");
            throw new NoDataFoundException();
        }
        return businessDtos;
    }

    @Override
    public BusinessDto getBusinessByCity(String city) {
        return null;
    }

    @Override
    public BusinessDto getByBusinessType(BusinessType type) {
        return null;
    }

    @Override
    public Boolean businessExists(Business business) {
        return null;
    }
}
