package com.microservices.categoryservice.controller;

import com.microservices.categoryservice.dto.CategoryDTO;
import com.microservices.categoryservice.dto.IdPramRequest;
import com.microservices.categoryservice.exception.CategoryServiceException;
import com.microservices.categoryservice.service.CategoryService;
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
@RequestMapping("/api/category/type")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<BaseDataResponse<CategoryDTO>> createCatType(@RequestBody BaseDataRequest<CategoryDTO> request, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws CategoryServiceException {
        return ResponseUtil.wrap(categoryService.create(request.getBody(), token));
    }

    @PostMapping("/update")
    public ResponseEntity<BaseDataResponse<CategoryDTO>> updateCatType(@RequestBody BaseDataRequest<CategoryDTO> request) throws CategoryServiceException {
        return ResponseUtil.wrap(categoryService.update(request.getBody()));
    }

    @PostMapping("/list")
    public ResponseEntity<BaseDataResponse<Page<CategoryDTO>>> findAllCatTypes(@RequestBody BaseDataRequest<FilterRequest> request) throws CategoryServiceException {
        return ResponseUtil.wrap(categoryService.findAll(request.getBody()));
    }

    @PostMapping("/find-by-id")
    public ResponseEntity<BaseDataResponse<CategoryDTO>> findByIdCatType(@RequestBody BaseDataRequest<IdPramRequest> request) throws CategoryServiceException {
        return ResponseUtil.wrap(categoryService.findById(request.getBody()));
    }

    @PostMapping("/list-tree")
    public ResponseEntity<BaseDataResponse<List<CategoryDTO>>> findAllTreeCatType(@RequestBody BaseDataRequest<FilterRequest> request) throws CategoryServiceException {
        return ResponseUtil.wrap(categoryService.findAllTree(request.getBody()));
    }
}
