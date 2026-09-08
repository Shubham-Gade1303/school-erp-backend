
        package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.SubjectRequest;
import com.schooleERP.dto.SubjectResponse;
import com.schooleERP.entity.Subject;
import com.schooleERP.repository.SubjectRepo;
import com.schooleERP.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepo subjectRepo;

    public SubjectServiceImpl(SubjectRepo subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    @Override
    public SubjectResponse createSubject(SubjectRequest request) {

        if (subjectRepo.existsBySubjectName(request.getSubjectName())) {
            throw new IllegalArgumentException(
                    "Subject name already exists"
            );
        }

        if (subjectRepo.existsBySubjectCode(request.getSubjectCode())) {
            throw new IllegalArgumentException(
                    "Subject code already exists"
            );
        }

        Subject subject = new Subject();

        subject.setSubjectName(request.getSubjectName());
        subject.setSubjectCode(request.getSubjectCode());
        subject.setDescription(request.getDescription());
        subject.setActive(request.isActive());

        Subject savedSubject = subjectRepo.save(subject);

        return mapToResponse(savedSubject);
    }

    @Override
    public List<SubjectResponse> getAllSubjects() {

        return subjectRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SubjectResponse getSubjectById(Long id) {

        Subject subject = subjectRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Subject not found with id: " + id
                        )
                );

        return mapToResponse(subject);
    }

    @Override
    public SubjectResponse updateSubject(
            Long id,
            SubjectRequest request) {

        Subject subject = subjectRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Subject not found with id: " + id
                        )
                );

        if (subjectRepo.existsBySubjectNameAndIdNot(
                request.getSubjectName(),
                id)) {

            throw new IllegalArgumentException(
                    "Subject name already exists"
            );
        }

        if (subjectRepo.existsBySubjectCodeAndIdNot(
                request.getSubjectCode(),
                id)) {

            throw new IllegalArgumentException(
                    "Subject code already exists"
            );
        }

        subject.setSubjectName(request.getSubjectName());
        subject.setSubjectCode(request.getSubjectCode());
        subject.setDescription(request.getDescription());
        subject.setActive(request.isActive());

        Subject updatedSubject = subjectRepo.save(subject);

        return mapToResponse(updatedSubject);
    }

    @Override
    public void deleteSubject(Long id) {

        Subject subject = subjectRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Subject not found with id: " + id
                        )
                );

        subjectRepo.delete(subject);
    }

    private SubjectResponse mapToResponse(Subject subject) {

        return new SubjectResponse(
                subject.getId(),
                subject.getSubjectName(),
                subject.getSubjectCode(),
                subject.getDescription(),
                subject.isActive()
        );
    }
}

