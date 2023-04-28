package com.scalefocus.midterm.trippyapp.service.impl;

import com.scalefocus.midterm.trippyapp.constants.enums.BusinessType;
import com.scalefocus.midterm.trippyapp.controller.request.BusinessRequest;
import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessNotFoundException;
import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessTypeNotFoundException;
import com.scalefocus.midterm.trippyapp.exception.NoDataFoundException;
import com.scalefocus.midterm.trippyapp.mapper.BusinessMapper;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.model.dto.BusinessDto;
import com.scalefocus.midterm.trippyapp.repository.BusinessRepository;
import com.scalefocus.midterm.trippyapp.repository.impl.BusinessRepositoryImpl;
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
    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;

    @Autowired
    public BusinessServiceImpl(BusinessRepositoryImpl businessRepository, BusinessMapper businessMapper) {
        this.businessRepository = businessRepository;
        this.businessMapper = businessMapper;
    }

    @Override
    public Long createBusiness(BusinessRequest businessRequest) {
        Business business = businessMapper.mapFromRequest(businessRequest);
//        if (businessExists(business)) {
//
//        }
        try {
            Long id = businessRepository.add(business);
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
        List<BusinessDto> businessDtos = businessRepository.getAll().stream().map(businessMapper::mapToDto).toList();
        if (businessDtos.isEmpty()) {
            log.error("No data found in database!");
            throw new NoDataFoundException();
        }
        return businessDtos;
    }

    @Override
    public List<BusinessDto> getBusinessByCity(String city) {
        List<BusinessDto> businessDtos = businessRepository.getBusinessByCityName(city).stream().map(businessMapper::mapToDto).toList();
        if (businessDtos.isEmpty()) {
            log.error("No data found in database!");
            throw new BusinessNotFoundException("city " + city);
        }
        return businessDtos;
    }

    @Override
    public List<BusinessDto> getByBusinessType(String type) {
        try {
            BusinessType businessType = BusinessType.valueOf(type);
            List<BusinessDto> businessDtos = businessRepository.getBusinessByType(businessType).stream().map(businessMapper::mapToDto).toList();
            if (businessDtos.isEmpty()) {
                log.error("No data found in database!");
                throw new BusinessNotFoundException("type " + type);
            }
            return businessDtos;
        } catch (IllegalArgumentException e) {
            throw new BusinessTypeNotFoundException(type);
        }
    }

    @Override
    public Boolean businessExists(Business business) {
        return null;
    }
}
