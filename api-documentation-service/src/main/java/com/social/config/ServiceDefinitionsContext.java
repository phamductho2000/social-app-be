package com.social.config;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * Project : documentation-apps
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 29/10/18
 * Time: 07.54
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServiceDefinitionsContext {

    private final ConcurrentHashMap<String, String> serviceDescriptions;

    private ServiceDefinitionsContext() {
        serviceDescriptions = new ConcurrentHashMap<String, String>();
    }

    public void addServiceDefinition(String serviceName, String serviceDescription) {
        serviceDescriptions.put(serviceName, serviceDescription);
    }

    public String getSwaggerDefinition(String serviceId) {
        return this.serviceDescriptions.get(serviceId);
    }

    public List<String> getAllSwaggerDefinition() {
        return (List<String>) this.serviceDescriptions.values();
    }

//    public List<SwaggerResource> getSwaggerDefinitions() {
//        return serviceDescriptions.entrySet().stream().map(serviceDefinition -> {
//            SwaggerResource resource = new SwaggerResource();
//            resource.setLocation("/service/" + serviceDefinition.getKey());
//            resource.setName(serviceDefinition.getKey());
//            resource.setSwaggerVersion("2.0");
//            return resource;
//        }).collect(Collectors.toList());
//    }
}
