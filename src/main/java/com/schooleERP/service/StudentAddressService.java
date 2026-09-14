package com.schooleERP.service;

import com.schooleERP.dto.StudentAddressRequest;
import com.schooleERP.dto.StudentAddressResponse;

import java.util.List;

public interface StudentAddressService {

    StudentAddressResponse createAddress(StudentAddressRequest request);

    List<StudentAddressResponse> getAllAddresses();

    StudentAddressResponse getAddressById(Long id);

    StudentAddressResponse getAddressByStudent(Long studentId);

    StudentAddressResponse updateAddress(Long id, StudentAddressRequest request);

    void deleteAddress(Long id);
}
