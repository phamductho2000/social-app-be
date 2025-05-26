package com.microservices.categoryservice.service;

import com.microservices.categoryservice.domain.Category;
import com.microservices.categoryservice.dto.CategoryDTO;
import com.microservices.categoryservice.dto.IdPramRequest;
import com.microservices.categoryservice.exception.CategoryServiceException;
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
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private WebClient webClient;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {CategoryServiceException.class, Throwable.class})
    public CategoryDTO create(CategoryDTO request, String token) throws CategoryServiceException {
        if (null == request) {
            throw new CategoryServiceException("Payload empty", "PAYLOAD_EMPTY");
        }
//        Optional<String> userLogin = Optional.ofNullable(webClient.get().uri("/api/user/unau/get-current-user-login")
//                .headers(h -> h.setBearerAuth(token.substring(7)))
//                .retrieve()
//                .bodyToMono(String.class)
//                .block());
        Category category = mapper.map(request, Category.class);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
//        userLogin.ifPresent(category::setCreatedBy);
//        userLogin.ifPresent(category::setUpdatedBy);
        Category saved = categoryRepository.save(category);
        return mapper.map(saved, CategoryDTO.class);
    }

    @Transactional
    public CategoryDTO update(CategoryDTO request) throws CategoryServiceException {
        if (null == request || null == request.getId()) {
            throw new CategoryServiceException("Payload empty", "PAYLOAD_EMPTY");
        }
        Category category = Optional.of(categoryRepository.findById(request.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .orElseThrow(() -> new CategoryServiceException("Could not found category by id: " + request.getId(), "NOT_FOUND"));
//        category.setName(request.getName());
//        category.setParentId(request.getParentId());
        category.setUpdatedAt(LocalDateTime.now());
        return mapper.map(categoryRepository.save(category), CategoryDTO.class);
    }

    public CategoryDTO findById(IdPramRequest request) throws CategoryServiceException {
        if (null == request || StringUtils.isEmpty(request.getId())) {
            throw new CategoryServiceException("Payload empty", "PAYLOAD_EMPTY");
        }
        CategoryDTO category = Optional.of(categoryRepository.findById(request.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(opCate -> mapper.map(opCate, CategoryDTO.class))
                .orElseThrow(() -> new CategoryServiceException("Could not found category by id: " + request.getId(), "NOT_FOUND"));

//        if (!StringUtils.isEmpty(category.getParentId())) {
//            CategoryDTO cateParent = Optional.of(categoryRepository.findById(category.getParentId()))
//                    .filter(Optional::isPresent)
//                    .map(Optional::get)
//                    .map(opCate -> mapper.map(opCate, CategoryDTO.class))
//                    .orElseThrow(() -> new CategoryServiceException("Could not found category parent by id: " + request.getId(), "NOT_FOUND"));
//            category.setParent(cateParent);
//        }
        return category;
    }

    public Page<CategoryDTO> findAll(FilterRequest request) throws CategoryServiceException {
        if (null == request) {
            throw new CategoryServiceException("Payload empty", "PAYLOAD_EMPTY");
        }
        Query query = SpecificationUtil.createSpecification(request.getFilters());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        long count = mongoTemplate.count(query, Category.class);
        List<CategoryDTO> categories = mongoTemplate.find(query, Category.class)
                .stream()
                .map(category -> mapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        categories = loop(categories, "", "");
        return PageableExecutionUtils.getPage(categories, pageable, () -> count);
    }

    public List<CategoryDTO> findAllTree(FilterRequest request) throws CategoryServiceException {
        if (null == request) {
            throw new CategoryServiceException("Payload empty", "PAYLOAD_EMPTY");
        }
        Query query = SpecificationUtil.createSpecification(request.getFilters());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        long count = mongoTemplate.count(query, Category.class);
        List<CategoryDTO> categories = mongoTemplate.find(query, Category.class)
                .stream()
                .map(category -> mapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        return recursionTree(categories, "");
    }

    public List<CategoryDTO> loop(List<CategoryDTO> list, String parentId, String character) {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        list.forEach(cate -> {
            if (cate.getParentId().equals(parentId)) {
//                cate.setLabel(character + cate.getName());
                categoryDTOList.add(cate);
                categoryDTOList.addAll(loop(list, cate.getId(), character + "|--- "));
            }
        });
        return categoryDTOList;
    }

    public List<CategoryDTO> recursionTree(List<CategoryDTO> data, String parentId) {
        List<CategoryDTO> result = new ArrayList<>();
        for (CategoryDTO cat: data) {
            if (parentId.equals(cat.getParentId())) {
                List<CategoryDTO> children = recursionTree(data, cat.getId());
                if (null != children && !children.isEmpty()) {
                    cat.setChildren(children);
                }
                result.add(cat);
            }
        }
        return result;
    }
}
