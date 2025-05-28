package com.social.common.log;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.social.common.constants.CommonConstants.HEADER_USER_NAME;
import static com.social.common.constants.CommonConstants.HEADER_USER_ID;

@Component
@Data
@RequestScope
public class Logger {
    private String path;
    private String method;
    private Map<String, Object> header = new HashMap<>();

    public void addServletRequest(HttpServletRequest request) {
        this.path = request.getServletPath();
        this.method = request.getMethod();
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println("addServletRequest");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            header.put(headerName, headerValue);
            System.out.println("headerName:" + headerName);
            System.out.println("headerValue:" + headerValue);
        }
    }

    public String getUserId() {return (String) this.header.get(HEADER_USER_ID);}

    public String getUserName() {return (String) this.header.get(HEADER_USER_NAME);}
}
