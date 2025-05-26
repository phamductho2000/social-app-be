package com.microservices.categoryservice.controller;

import com.microservices.categoryservice.dto.CategoryPropertyDTO;
import com.microservices.categoryservice.exception.CategoryServiceException;
import com.microservices.categoryservice.service.CategoryPropertyService;
import com.microservices.common.dto.BaseDataRequest;
import com.microservices.common.dto.BaseDataResponse;
import com.microservices.common.dto.FilterRequest;
import com.microservices.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category/category-prop")
public class CategoryPropertyController {

    @Autowired
    private CategoryPropertyService categoryPropertyService;

    @PostMapping("/add")
    public ResponseEntity<BaseDataResponse<CategoryPropertyDTO>> createCatProp(@RequestBody BaseDataRequest<CategoryPropertyDTO> request, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws CategoryServiceException {
        return ResponseUtil.wrap(categoryPropertyService.create(request.getBody(), token));
    }

    @PostMapping("/update")
    public ResponseEntity<BaseDataResponse<CategoryPropertyDTO>> updateCatProp(@RequestBody BaseDataRequest<CategoryPropertyDTO> request) throws CategoryServiceException {
        return ResponseUtil.wrap(categoryPropertyService.update(request.getBody()));
    }

    @PostMapping("/list")
    public ResponseEntity<BaseDataResponse<Page<CategoryPropertyDTO>>> findAllCatProps(@RequestBody BaseDataRequest<FilterRequest> request) throws CategoryServiceException {
        return ResponseUtil.wrap(categoryPropertyService.findAll(request.getBody()));
    }

    @PostMapping("/add-multi")
    public ResponseEntity<BaseDataResponse<List<CategoryPropertyDTO>>> createMultiCatProp(@RequestBody BaseDataRequest<List<CategoryPropertyDTO>> request, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws CategoryServiceException {
        return ResponseUtil.wrap(categoryPropertyService.createMulti(request.getBody(), token));
    }

//    @PostMapping("/find-by-id")
//    public ResponseEntity<BaseDataResponse<CategoryPropertyDTO>> findById(@RequestBody BaseDataRequest<IdPramRequest> request) throws CategoryServiceException {
//        return ResponseUtil.wrap(categoryPropertyService.findById(request.getBody()));
//    }
}
