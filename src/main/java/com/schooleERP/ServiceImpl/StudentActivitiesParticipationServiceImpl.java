package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.StudentActivitiesParticipationRequest;
import com.schooleERP.dto.StudentActivitiesParticipationResponse;
import com.schooleERP.entity.Activities;
import com.schooleERP.entity.Student;
import com.schooleERP.entity.StudentActivitiesParticipation;
import com.schooleERP.repository.ActivitiesRepo;
import com.schooleERP.repository.StudentActivitiesParticipationRepo;
import com.schooleERP.repository.StudentRepo;
import com.schooleERP.service.StudentActivitiesParticipationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class StudentActivitiesParticipationServiceImpl
        implements StudentActivitiesParticipationService {

    private final StudentActivitiesParticipationRepo participationRepo;
    private final StudentRepo studentRepo;
    private final ActivitiesRepo activitiesRepo;

    public StudentActivitiesParticipationServiceImpl(
            StudentActivitiesParticipationRepo participationRepo,
            StudentRepo studentRepo,
            ActivitiesRepo activitiesRepo) {

        this.participationRepo = participationRepo;
        this.studentRepo = studentRepo;
        this.activitiesRepo = activitiesRepo;
    }

    @Override
    public StudentActivitiesParticipationResponse createParticipation(
            StudentActivitiesParticipationRequest request) {

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with id: "
                                        + request.getStudentId()));

        Activities activity = activitiesRepo.findById(request.getActivityId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Activity not found with id: "
                                        + request.getActivityId()));

        StudentActivitiesParticipation participation =
                new StudentActivitiesParticipation();

        participation.setStudent(student);
        participation.setActivity(activity);
        participation.setParticipationDate(
                request.getParticipationDate());
        participation.setRole(request.getRole());
        participation.setResult(request.getResult());
        participation.setPosition(request.getPosition());
        participation.setRemarks(request.getRemarks());

        StudentActivitiesParticipation savedParticipation =
                participationRepo.save(participation);

        return mapToResponse(savedParticipation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentActivitiesParticipationResponse>
    getAllParticipations() {

        return participationRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentActivitiesParticipationResponse getParticipationById(
            Long id) {

        StudentActivitiesParticipation participation =
                participationRepo.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Participation not found with id: "
                                                + id));

        return mapToResponse(participation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentActivitiesParticipationResponse>
    getParticipationsByStudent(Long studentId) {

        if (!studentRepo.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found with id: " + studentId);
        }

        return participationRepo.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentActivitiesParticipationResponse>
    getParticipationsByActivity(Long activityId) {

        if (!activitiesRepo.existsById(activityId)) {
            throw new RuntimeException(
                    "Activity not found with id: " + activityId);
        }

        return participationRepo.findByActivityId(activityId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentActivitiesParticipationResponse>
    getParticipationsByDate(LocalDate participationDate) {

        return participationRepo
                .findByParticipationDate(participationDate)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public StudentActivitiesParticipationResponse updateParticipation(
            Long id,
            StudentActivitiesParticipationRequest request) {

        StudentActivitiesParticipation participation =
                participationRepo.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Participation not found with id: "
                                                + id));

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with id: "
                                        + request.getStudentId()));

        Activities activity = activitiesRepo.findById(request.getActivityId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Activity not found with id: "
                                        + request.getActivityId()));

        participation.setStudent(student);
        participation.setActivity(activity);
        participation.setParticipationDate(
                request.getParticipationDate());
        participation.setRole(request.getRole());
        participation.setResult(request.getResult());
        participation.setPosition(request.getPosition());
        participation.setRemarks(request.getRemarks());

        StudentActivitiesParticipation updatedParticipation =
                participationRepo.save(participation);

        return mapToResponse(updatedParticipation);
    }

    @Override
    public void deleteParticipation(Long id) {

        StudentActivitiesParticipation participation =
                participationRepo.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Participation not found with id: "
                                                + id));

        participationRepo.delete(participation);
    }

    private StudentActivitiesParticipationResponse mapToResponse(
            StudentActivitiesParticipation participation) {

        StudentActivitiesParticipationResponse response = new StudentActivitiesParticipationResponse();

        response.setId(participation.getId());

        response.setStudentId(participation.getStudent().getId());

        response.setStudentName(buildStudentName(participation.getStudent()));

        response.setActivityId(participation.getActivity().getId());

        response.setActivityName(participation.getActivity().getActivityName());

        response.setParticipationDate(participation.getParticipationDate());

        response.setRole(participation.getRole());

        response.setResult(participation.getResult());

        response.setPosition(participation.getPosition());

        response.setRemarks(participation.getRemarks());

        return response;
    }

    private String buildStudentName(Student student) {

        StringBuilder name = new StringBuilder();

        if (student.getFirstName() != null
                && !student.getFirstName().isBlank()) {
            name.append(student.getFirstName());
        }

        if (student.getMiddleName() != null
                && !student.getMiddleName().isBlank()) {
            if (!name.isEmpty()) {
                name.append(" ");
            }
            name.append(student.getMiddleName());
        }

        if (student.getLastName() != null
                && !student.getLastName().isBlank()) {
            if (!name.isEmpty()) {
                name.append(" ");
            }
            name.append(student.getLastName());
        }

        return name.toString();
    }
}
