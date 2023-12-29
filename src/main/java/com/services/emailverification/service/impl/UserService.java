package com.services.emailverification.service.impl;

import com.services.emailverification.dto.requests.RegisterRequest;
import com.services.emailverification.dto.responses.AllUserResponses;
import com.services.emailverification.dto.responses.RegisterResponse;

import java.util.List;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

    List<AllUserResponses> getAllUsers();

    void verify(String email, String otp);
}
