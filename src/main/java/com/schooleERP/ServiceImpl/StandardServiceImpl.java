package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.StandardRequest;
import com.schooleERP.dto.StandardResponse;
import com.schooleERP.entity.Standard;
import com.schooleERP.repository.StandardRepo;
import com.schooleERP.service.StandardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandardServiceImpl
        implements StandardService {

    private final StandardRepo standardRepo;

    public StandardServiceImpl(
            StandardRepo standardRepo) {
        this.standardRepo = standardRepo;
    }

    @Override
    public StandardResponse createStandard(
            StandardRequest request) {

        if (standardRepo.existsByStandardName(
                request.getStandardName())) {

            throw new IllegalArgumentException(
                    "Standard already exists: "
                            + request.getStandardName()
            );
        }

        Standard standard = new Standard();

        mapRequestToEntity(request, standard);

        Standard savedStandard =
                standardRepo.save(standard);

        return mapEntityToResponse(savedStandard);
    }

    @Override
    public List<StandardResponse> getAllStandards() {

        return standardRepo.findAll()
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    @Override
    public StandardResponse getStandardById(
            Long id) {

        Standard standard =
                standardRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Standard not found with id: "
                                                + id
                                ));

        return mapEntityToResponse(standard);
    }

    @Override
    public StandardResponse updateStandard(
            Long id,
            StandardRequest request) {

        Standard standard =
                standardRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Standard not found with id: "
                                                + id
                                ));

        if (!standard.getStandardName()
                .equals(request.getStandardName())
                && standardRepo.existsByStandardName(
                request.getStandardName())) {

            throw new IllegalArgumentException(
                    "Standard already exists: "
                            + request.getStandardName()
            );
        }

        mapRequestToEntity(request, standard);

        Standard updatedStandard =
                standardRepo.save(standard);

        return mapEntityToResponse(updatedStandard);
    }

    @Override
    public void deleteStandard(Long id) {

        if (!standardRepo.existsById(id)) {

            throw new IllegalArgumentException(
                    "Standard not found with id: " + id
            );
        }

        standardRepo.deleteById(id);
    }

    private void mapRequestToEntity(
            StandardRequest request,
            Standard standard) {

        standard.setStandardName(
                request.getStandardName()
        );

        standard.setDisplayOrder(
                request.getDisplayOrder()
        );

        standard.setActive(
                request.isActive()
        );
    }

    private StandardResponse mapEntityToResponse(
            Standard standard) {

        return new StandardResponse(
                standard.getId(),
                standard.getStandardName(),
                standard.getDisplayOrder(),
                standard.isActive(),
                standard.getCreatedAt(),
                standard.getUpdatedAt()
        );
    }
}

