package com.scalefocus.midterm.trippyapp.controller.impl;

import com.scalefocus.midterm.trippyapp.controller.BusinessController;
import com.scalefocus.midterm.trippyapp.controller.request.BusinessRequest;
import com.scalefocus.midterm.trippyapp.model.dto.BusinessDto;
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
        return ResponseEntity.ok(businessService.getBusinessById(id));
    }

    @Override
    public ResponseEntity<List<BusinessDto>> getBusinessByCity(String city) {
        List<BusinessDto> filteredList = businessService.getBusinessesByCity(city);
        return ResponseEntity.ok(filteredList);
    }

    @Override
    public ResponseEntity<List<BusinessDto>> getByBusinessType(String businessType) {
        List<BusinessDto> filteredList = businessService.getByBusinessType(businessType);
        return ResponseEntity.ok(filteredList);
    }

    @Override
    public ResponseEntity<BusinessDto> getByBusinessEmail(String email) {
        BusinessDto businessDto = businessService.getBusinessByEmail(email);
        return ResponseEntity.ok(businessDto);
    }

    @Override
    public ResponseEntity<BusinessDto> getByBusinessName(String name) {
        return ResponseEntity.ok(businessService.getBusinessByName(name));
    }

    @Override
    public ResponseEntity<BusinessDto> getByBusinessNameAndCity(String name, String city) {
        return ResponseEntity.ok(businessService.getBusinessByNameAndCity(name, city));
    }

    @Override
    public ResponseEntity<List<BusinessDto>> getByBusinessRate(double averageRate, String query) {
        return ResponseEntity.ok(businessService.getBusinessesByRate(averageRate, query));
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
    public ResponseEntity<BusinessDto> updateBusiness(BusinessRequest businessRequest, int id, boolean returnOld) {
        BusinessDto businessDto = businessService.editBusiness(businessRequest, id);
        if (returnOld) {
            return ResponseEntity.ok(businessDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
