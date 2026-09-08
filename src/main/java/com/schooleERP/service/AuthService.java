package com.schooleERP.service;

import com.schooleERP.dto.LoginRequest;
import com.schooleERP.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
