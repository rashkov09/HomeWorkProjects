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
import com.scalefocus.midterm.trippyapp.repository.BusinessRepository;
import com.scalefocus.midterm.trippyapp.repository.impl.BusinessRepositoryImpl;
import com.scalefocus.midterm.trippyapp.service.BusinessService;
import com.scalefocus.midterm.trippyapp.util.ObjectChecker;
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
    private final ObjectChecker<BusinessRequest> businessObjectChecker;

    @Autowired
    public BusinessServiceImpl(BusinessRepositoryImpl businessRepository, BusinessMapper businessMapper, ObjectChecker<BusinessRequest> businessObjectChecker) {
        this.businessRepository = businessRepository;
        this.businessMapper = businessMapper;
        this.businessObjectChecker = businessObjectChecker;
    }

    @Override
    public Long createBusiness(BusinessRequest businessRequest) {
        businessObjectChecker.checkForMissingFields(businessRequest);
        Business business = businessMapper.mapFromRequest(businessRequest);

        if (businessExists(business)) {
            log.error(String.format("Business with name %s and city %s not found!", business.getName(), business.getCity()));
            throw new BusinessAlreadyExistsException("name " + business.getName() + " and city " + business.getCity());
        }
        try {
            Long id = businessRepository.add(business);
            log.info(String.format("Business added with name %s and city %s successfully!", business.getName(), business.getCity()));
            return id;
        } catch (SQLException e) {
            throw new RuntimeException("Unexpected error", e);
        }
    }

    @Override
    public BusinessDto editBusiness(BusinessRequest businessRequest, Integer id) {
        businessObjectChecker.checkForMissingFields(businessRequest);
        Business business = businessMapper.mapFromRequest(businessRequest);

        if (businessExists(business)) {
            throw new BusinessAlreadyExistsException("name " + business.getName() + " and city " + business.getCity());
        }
        try {
            Business old = businessRepository.edit(business, id.longValue());
            business.setId(id.longValue());
            log.info(String.format("Business with id %d edited successfully!", id));
            return businessMapper.mapToDto(old);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BusinessDto getBusinessById(Long id) {
        Business business = businessRepository.getById(id);
        if (business == null) {
            log.info(String.format("Business with id %d not found!", id));
            throw new BusinessNotFoundException("id " + id);
        }
        return businessMapper.mapToDto(business);
    }

    @Override
    public BusinessDto getBusinessByEmail(String email) {
        Business business = businessRepository.getByEmail(email);
        if (business == null) {
            log.info(String.format("Business with email %s not found!", email));
            throw new BusinessNotFoundException("email " + email);
        }
        return businessMapper.mapToDto(business);
    }

    @Override
    public BusinessDto getBusinessByName(String name) {
        Business business = businessRepository.getByName(name);
        if (business == null) {
            log.error(String.format("Business with name %s not found!", name));
            throw new BusinessNotFoundException("name " + name);
        }
        return businessMapper.mapToDto(business);
    }

    @Override
    public BusinessDto getBusinessByNameAndCity(String name, String city) {
        Business business = businessRepository.getByNameAndCity(name, city);
        if (business == null) {
            log.error(String.format("Business with name %s and city %s not found!", name, city));
            throw new BusinessNotFoundException("name " + name + " and city " + city);
        }
        return businessMapper.mapToDto(business);
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
    public List<BusinessDto> getBusinessesByCity(String city) {
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
        Business search = businessRepository.getByNameAndCity(business.getName(), business.getCity());
        return search != null;
    }

    @Override
    public void updateBusiness(Business business) {
        businessRepository.update(business);
    }
}
