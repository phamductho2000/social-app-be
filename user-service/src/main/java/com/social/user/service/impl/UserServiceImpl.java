package com.social.user.service.impl;

import com.social.common.page.CustomPageScroll;
import com.social.common.util.QueryBuilder;
import com.social.user.constants.UserStatus;
import com.social.user.domain.UserInfo;
import com.social.user.dto.UserRequestDTO;
import com.social.user.dto.UserResponseDTO;
import com.social.user.dto.request.SearchUserRequestDto;
import com.social.user.exception.UserServiceException;
import com.social.user.repo.UserRepository;
import com.social.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.social.common.util.AppUtils.decodeSearchAfter;
import static com.social.common.util.AppUtils.encodeSearchAfter;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper mapper;

    private final UserRepository userRepository;

    private final MongoTemplate mongoTemplate;

    @Override
    public UserResponseDTO create(UserRequestDTO request) throws UserServiceException {
        if (null == request) {
            throw new UserServiceException("Payload empty", "PAYLOAD_EMPTY");
        }
        try {
            UserInfo user = mapper.map(request, UserInfo.class);
            user.setUserName(request.getEmail());
            user.setFullName(request.getFirstName() + " " + request.getLastName());
            user.setStatus(UserStatus.ACTIVE);
            UserInfo userSaved = userRepository.save(user);
            return mapper.map(userRepository.save(userSaved), UserResponseDTO.class);
        } catch (Exception e) {
            throw new UserServiceException("Create user error", "SAVE_USER_ERROR");
        }
    }

    @Override
    public UserResponseDTO update(UserRequestDTO request) throws UserServiceException {
//        if (null == request) {
//            throw new UserServiceException("Payload empty", "PAYLOAD_EMPTY");
//        }
//        if (checkUserExistById(request)) {
//            throw new UserServiceException("Could not found user by id: " + request.getId(), "NOT_FOUND");
//        }
//        User user = mapper.map(request, User.class);
//        user.setUpdatedAt(Instant.now());
//        return mapper.map(userRepository.save(user), UserResponseDTO.class);
        return null;
    }

    @Override
    public CustomPageScroll<UserResponseDTO> getScrollUsers(SearchUserRequestDto request) throws UserServiceException {
        if (null == request) {
            throw new UserServiceException("Payload empty", "PAYLOAD_EMPTY");
        }

        ScrollPosition scrollPosition = ScrollPosition.keyset();
        Map<String, Object> extendData = new HashMap<>();

        if (StringUtils.isNotEmpty(request.getSearchAfter())) {
            scrollPosition = decodeSearchAfter(request.getSearchAfter());
        }

        Query query = QueryBuilder.builder()
                .regex("fullName", request.getSearchValue())
                .eq("status", UserStatus.ACTIVE)
                .build()
                .limit(request.getLimit())
                .with(Sort.by(Sort.Direction.DESC, "createdAt"))
                .with(scrollPosition);

        Window<UserInfo> result = mongoTemplate.scroll(query, UserInfo.class);

        if (result.hasNext()) {
            scrollPosition = result.positionAt(result.size() - 1);
            extendData.put("searchAfter", encodeSearchAfter(scrollPosition));
        }

        return CustomPageScroll.buildPage(result.getContent()
                                .stream().map(e -> mapper.map(e, UserResponseDTO.class))
                                .toList(),
                        result.size(), extendData);
    }

    @Override
    public UserResponseDTO getUserById(String id) throws UserServiceException {
        if (StringUtils.isEmpty(id)) {
            throw new UserServiceException("Payload empty", "PAYLOAD_EMPTY");
        }
        Optional<UserInfo> user = userRepository.findById(id);
        return user.map(m -> mapper.map(m, UserResponseDTO.class))
                .orElse(null);
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) throws UserServiceException {
        if (StringUtils.isEmpty(username)) {
            throw new UserServiceException("Payload empty", "PAYLOAD_EMPTY");
        }
        Optional<UserInfo> user = userRepository.findFirstByUserName(username);
        return user.map(m -> mapper.map(m, UserResponseDTO.class))
                .orElse(null);
    }

    @Override
    public List<UserResponseDTO> getUsersByIds(List<String> ids) throws UserServiceException {
        if (null == ids || ids.isEmpty()) {
            throw new UserServiceException("Payload empty", "PAYLOAD_EMPTY");
        }
        return userRepository.findAllByUserIdIn(ids).stream()
                .map(m -> mapper.map(m, UserResponseDTO.class))
                .toList();
    }
}
