package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.ParentsGuardiansRequest;
import com.schooleERP.dto.ParentsGuardiansResponse;
import com.schooleERP.entity.ParentsGuardians;
import com.schooleERP.entity.Student;
import com.schooleERP.repository.ParentsGuardiansRepo;
import com.schooleERP.repository.StudentRepo;
import com.schooleERP.service.ParentsGuardiansService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ParentsGuardiansServiceImpl implements ParentsGuardiansService {

    private final ParentsGuardiansRepo parentsGuardiansRepo;
    private final StudentRepo studentRepo;

    public ParentsGuardiansServiceImpl(
            ParentsGuardiansRepo parentsGuardiansRepo,
            StudentRepo studentRepo
    ) {
        this.parentsGuardiansRepo = parentsGuardiansRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public ParentsGuardiansResponse createParentGuardian(
            ParentsGuardiansRequest request
    ) {

        Student student = getStudent(request.getStudentId());

        ParentsGuardians parentGuardian = new ParentsGuardians();

        mapRequestToEntity(
                parentGuardian,
                request,
                student
        );

        ParentsGuardians savedParentGuardian =
                parentsGuardiansRepo.save(parentGuardian);

        return mapToResponse(savedParentGuardian);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParentsGuardiansResponse> getAllParentsGuardians() {

        return parentsGuardiansRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ParentsGuardiansResponse getParentGuardianById(
            Long id
    ) {

        ParentsGuardians parentGuardian =
                parentsGuardiansRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Parent/Guardian not found with id: " + id
                        ));

        return mapToResponse(parentGuardian);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParentsGuardiansResponse> getParentsGuardiansByStudent(
            Long studentId
    ) {

        getStudent(studentId);

        return parentsGuardiansRepo.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ParentsGuardiansResponse updateParentGuardian(
            Long id,
            ParentsGuardiansRequest request
    ) {

        ParentsGuardians parentGuardian =
                parentsGuardiansRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Parent/Guardian not found with id: " + id
                        ));

        Student student = getStudent(request.getStudentId());

        mapRequestToEntity(
                parentGuardian,
                request,
                student
        );

        ParentsGuardians updatedParentGuardian =
                parentsGuardiansRepo.save(parentGuardian);

        return mapToResponse(updatedParentGuardian);
    }

    @Override
    public void deleteParentGuardian(Long id) {

        ParentsGuardians parentGuardian =
                parentsGuardiansRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Parent/Guardian not found with id: " + id
                        ));

        parentsGuardiansRepo.delete(parentGuardian);
    }

    private Student getStudent(Long studentId) {

        return studentRepo.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Student not found with id: " + studentId
                ));
    }

    private void mapRequestToEntity(
            ParentsGuardians parentGuardian,
            ParentsGuardiansRequest request,
            Student student
    ) {

        parentGuardian.setStudent(student);
        parentGuardian.setFullName(request.getFullName());
        parentGuardian.setRelationship(request.getRelationship());
        parentGuardian.setPhoneNumber(request.getPhoneNumber());
        parentGuardian.setEmail(request.getEmail());
        parentGuardian.setOccupation(request.getOccupation());
    }

    private ParentsGuardiansResponse mapToResponse(
            ParentsGuardians parentGuardian
    ) {

        Student student = parentGuardian.getStudent();

        ParentsGuardiansResponse response =
                new ParentsGuardiansResponse();

        response.setId(parentGuardian.getId());

        response.setStudentId(student.getId());

        response.setAdmissionNumber(
                student.getAdmissionNumber()
        );

        response.setStudentName(
                buildStudentName(student)
        );

        response.setFullName(
                parentGuardian.getFullName()
        );

        response.setRelationship(
                parentGuardian.getRelationship()
        );

        response.setPhoneNumber(
                parentGuardian.getPhoneNumber()
        );

        response.setEmail(
                parentGuardian.getEmail()
        );

        response.setOccupation(
                parentGuardian.getOccupation()
        );

        return response;
    }

    private String buildStudentName(Student student) {

        StringBuilder name = new StringBuilder();

        name.append(student.getFirstName());

        if (student.getMiddleName() != null
                && !student.getMiddleName().isBlank()) {

            name.append(" ")
                    .append(student.getMiddleName());
        }

        name.append(" ")
                .append(student.getLastName());

        return name.toString();
    }
}
