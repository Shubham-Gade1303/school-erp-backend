package com.schooleERP.service;

import com.schooleERP.dto.StandardRequest;
import com.schooleERP.dto.StandardResponse;

import java.util.List;

public interface StandardService {

    StandardResponse createStandard(
            StandardRequest request
    );

    List<StandardResponse> getAllStandards();

    StandardResponse getStandardById(Long id);

    StandardResponse updateStandard(
            Long id,
            StandardRequest request
    );

    void deleteStandard(Long id);
}

