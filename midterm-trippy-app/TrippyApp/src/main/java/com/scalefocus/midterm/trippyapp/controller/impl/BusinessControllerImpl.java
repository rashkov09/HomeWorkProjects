package com.scalefocus.midterm.trippyapp.controller.impl;

import com.scalefocus.midterm.trippyapp.constants.enums.BusinessType;
import com.scalefocus.midterm.trippyapp.controller.BusinessController;
import com.scalefocus.midterm.trippyapp.controller.request.BusinessRequest;
import com.scalefocus.midterm.trippyapp.model.dto.BusinessDto;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;
import com.scalefocus.midterm.trippyapp.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class BusinessControllerImpl implements BusinessController {
    private final BusinessService businessService;

    @Autowired
    public BusinessControllerImpl(BusinessService businessService) {
        this.businessService = businessService;
    }

    @Override
    public ResponseEntity<List<BusinessDto>> getAllBusinesses() {
        List<BusinessDto> businessDtos = businessService.getAllBusinesses();
        return ResponseEntity.ok(businessDtos);
    }

    @Override
    public ResponseEntity<BusinessDto> getBusinessById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<BusinessDto>> getBusinessByCity(String city) {
        return null;
    }

    @Override
    public ResponseEntity<List<BusinessDto>> getByBusinessType(BusinessType businessType) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addBusiness(BusinessRequest businessRequest) {
        Long id = businessService.createBusiness(businessRequest);

        URI location = UriComponentsBuilder.fromUriString("/businesses/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<UserDto> updateBusiness(BusinessRequest businessRequest, int id, boolean returnOld) {
        return null;
    }
}
