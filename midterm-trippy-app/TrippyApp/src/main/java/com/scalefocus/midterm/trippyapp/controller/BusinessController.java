package com.scalefocus.midterm.trippyapp.controller;

import com.scalefocus.midterm.trippyapp.controller.request.BusinessRequest;
import com.scalefocus.midterm.trippyapp.model.dto.BusinessDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/businesses")
public interface BusinessController {
    @GetMapping()
    ResponseEntity<List<BusinessDto>> getAllBusinesses();

    @GetMapping("/{id}")
    ResponseEntity<BusinessDto> getBusinessById(@PathVariable
                                                Long id);

    @GetMapping(params = "city")
    ResponseEntity<List<BusinessDto>> getBusinessByCity(@RequestParam(value = "city") String city);

    @GetMapping(params = "type")
    ResponseEntity<List<BusinessDto>> getByBusinessType(@RequestParam(value = "type") String businessType);

    @GetMapping(params = "email")
    ResponseEntity<BusinessDto> getByBusinessEmail(@RequestParam(value = "email") String email);

    @PostMapping()
    ResponseEntity<Void> addBusiness(@RequestBody @Valid BusinessRequest businessRequest);

    @PutMapping("/{id}")
    ResponseEntity<BusinessDto> updateBusiness(
            @RequestBody @Valid BusinessRequest businessRequest, @PathVariable int id,
            @RequestParam(required = false) boolean returnOld);
}
