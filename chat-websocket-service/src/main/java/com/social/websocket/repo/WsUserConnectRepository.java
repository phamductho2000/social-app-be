package com.social.websocket.repo;

import com.social.websocket.domain.WsUserConnect;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WsUserConnectRepository extends CrudRepository<WsUserConnect, String> {

    List<WsUserConnect> findAllByStatus(String status);
}
