package com.microservices.categoryservice.service;

import com.microservices.categoryservice.domain.Category;
import com.microservices.categoryservice.domain.CategoryProperty;
import com.microservices.categoryservice.dto.CategoryDTO;
import com.microservices.categoryservice.dto.CategoryPropertyDTO;
import com.microservices.categoryservice.exception.CategoryServiceException;
import com.microservices.categoryservice.repo.CategoryPropertyRepository;
import com.microservices.categoryservice.repo.CategoryRepository;
import com.microservices.common.dto.FilterRequest;
import com.microservices.common.util.SpecificationUtil;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryPropertyService {

    @Autowired
    CategoryPropertyRepository categoryPropertyRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private WebClient webClient;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {CategoryServiceException.class, Throwable.class})
    public CategoryPropertyDTO create(CategoryPropertyDTO request, String token) throws CategoryServiceException {
        if (null == request) {
            throw new CategoryServiceException("Payload empty", "PAYLOAD_EMPTY");
        }
//        Optional<String> userLogin = Optional.ofNullable(webClient.get().uri("/api/user/unau/get-current-user-login")
//                .headers(h -> h.setBearerAuth(token.substring(7)))
//                .retrieve()
//                .bodyToMono(String.class)
//                .block());
        CategoryProperty CategoryProperty = mapper.map(request, CategoryProperty.class);
        CategoryProperty.setCreatedAt(LocalDateTime.now());
        CategoryProperty.setUpdatedAt(LocalDateTime.now());
//        userLogin.ifPresent(CategoryProperty::setCreatedBy);
//        userLogin.ifPresent(CategoryProperty::setUpdatedBy);
        CategoryProperty saved = categoryPropertyRepository.save(CategoryProperty);
        return mapper.map(saved, CategoryPropertyDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {CategoryServiceException.class, Throwable.class})
    public List<CategoryPropertyDTO> createMulti(List<CategoryPropertyDTO> request, String token) throws CategoryServiceException {
        if (null == request) {
            throw new CategoryServiceException("Payload empty", "PAYLOAD_EMPTY");
        }
//        Optional<String> userLogin = Optional.ofNullable(webClient.get().uri("/api/user/unau/get-current-user-login")
//                .headers(h -> h.setBearerAuth(token.substring(7)))
//                .retrieve()
//                .bodyToMono(String.class)
//                .block());
        List<CategoryProperty> categoryProperties = request.stream().map(dto -> {
            dto.setCreatedAt(LocalDateTime.now());
            dto.setUpdatedAt(LocalDateTime.now());
            return mapper.map(dto, CategoryProperty.class);
        }).toList();

        categoryProperties =  categoryPropertyRepository.saveAll(categoryProperties);

        Optional<Category> category = categoryRepository.findById(request.get(0).getCTypeId());
        if (category.isPresent()) {
            category.get().setProps(categoryProperties);
            categoryRepository.save(category.get());
        }

        return categoryProperties.stream().map(e -> mapper.map(e, CategoryPropertyDTO.class)).toList();
    }

    @Transactional
    public CategoryPropertyDTO update(CategoryPropertyDTO request) throws CategoryServiceException {
        if (null == request || null == request.getId()) {
            throw new CategoryServiceException("Payload empty", "PAYLOAD_EMPTY");
        }
        CategoryProperty CategoryProperty = Optional.of(categoryPropertyRepository.findById(request.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .orElseThrow(() -> new CategoryServiceException("Could not found CategoryProperty by id: " + request.getId(), "NOT_FOUND"));
        CategoryProperty.setName(request.getName());
        CategoryProperty.setUpdatedAt(LocalDateTime.now());
        return mapper.map(categoryPropertyRepository.save(CategoryProperty), CategoryPropertyDTO.class);
    }

//    public CategoryPropertyDTO findById(IdPramRequest request) throws CategoryServiceException {
//        if (null == request || StringUtils.isEmpty(request.getId())) {
//            throw new CategoryPropertyServiceException("Payload empty", "PAYLOAD_EMPTY");
//        }
//        CategoryPropertyDTO CategoryProperty = Optional.of(CategoryPropertyRepository.findById(request.getId()))
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .map(opCate -> mapper.map(opCate, CategoryPropertyDTO.class))
//                .orElseThrow(() -> new CategoryPropertyServiceException("Could not found CategoryProperty by id: " + request.getId(), "NOT_FOUND"));
//
//        if (!StringUtils.isEmpty(CategoryProperty.getParentId())) {
//            CategoryPropertyDTO cateParent = Optional.of(CategoryPropertyRepository.findById(CategoryProperty.getParentId()))
//                    .filter(Optional::isPresent)
//                    .map(Optional::get)
//                    .map(opCate -> mapper.map(opCate, CategoryPropertyDTO.class))
//                    .orElseThrow(() -> new CategoryPropertyServiceException("Could not found CategoryProperty parent by id: " + request.getId(), "NOT_FOUND"));
//            CategoryProperty.setParent(cateParent);
//        }
//        return CategoryProperty;
//    }

    public Page<CategoryPropertyDTO> findAll(FilterRequest request) throws CategoryServiceException {
        if (null == request) {
            throw new CategoryServiceException("Payload empty", "PAYLOAD_EMPTY");
        }
        Query query = SpecificationUtil.createSpecification(request.getFilters());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        long count = mongoTemplate.count(query, CategoryProperty.class);
        List<CategoryPropertyDTO> categories = mongoTemplate.find(query, CategoryProperty.class)
                .stream()
                .map(CategoryProperty -> mapper.map(CategoryProperty, CategoryPropertyDTO.class))
                .collect(Collectors.toList());
        return PageableExecutionUtils.getPage(categories, pageable, () -> count);
    }
}
