package com.social.repo;

import com.social.domain.WsUserConnect;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WsUserConnectRepository extends CrudRepository<WsUserConnect, String> {

    List<WsUserConnect> findAllByStatus(String status);
}
