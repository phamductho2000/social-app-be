package com.social.controller;

import com.social.config.ServiceDefinitionsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : documentation-apps
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 29/10/18
 * Time: 07.58
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class ServiceDefinitionController {

    @Autowired
    private ServiceDefinitionsContext definitionContext;

    @GetMapping("/service/{servicename}")
    public String getServiceDefinition(@PathVariable("servicename") String serviceName) {

        return definitionContext.getSwaggerDefinition(serviceName);

    }

    @GetMapping("/service/getAll")
    public List<String> getAllServiceDefinition() {
        return definitionContext.getAllSwaggerDefinition();

    }
}
