package com.mysite.askAnything.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface RequestService {
    String getClientIp(HttpServletRequest request);
}

System.out.println("수정수정");
