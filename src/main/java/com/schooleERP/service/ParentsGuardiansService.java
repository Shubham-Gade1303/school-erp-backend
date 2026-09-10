package com.schooleERP.service;

import com.schooleERP.dto.ParentsGuardiansRequest;
import com.schooleERP.dto.ParentsGuardiansResponse;

import java.util.List;

public interface ParentsGuardiansService {

    ParentsGuardiansResponse createParentGuardian(
            ParentsGuardiansRequest request
    );

    List<ParentsGuardiansResponse> getAllParentsGuardians();

    ParentsGuardiansResponse getParentGuardianById(Long id);

    List<ParentsGuardiansResponse> getParentsGuardiansByStudent(
            Long studentId
    );

    ParentsGuardiansResponse updateParentGuardian(
            Long id,
            ParentsGuardiansRequest request
    );

    void deleteParentGuardian(Long id);
}

