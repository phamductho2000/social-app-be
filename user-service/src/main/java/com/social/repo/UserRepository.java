package com.social.repo;

import com.social.domain.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserInfo, String> {

    Optional<UserInfo> findFirstByUserName(String username);
}
