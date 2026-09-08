package com.schooleERP.service;

import com.schooleERP.dto.UserCreateRequest;
import com.schooleERP.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserCreateRequest request);

    List<UserResponse> listUsers();



}