package com.social.user.repo;

import com.social.user.domain.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserInfo, String> {

    Optional<UserInfo> findFirstByUserName(String username);

    List<UserInfo> findAllByUserIdIn(List<String> userIds);
}
