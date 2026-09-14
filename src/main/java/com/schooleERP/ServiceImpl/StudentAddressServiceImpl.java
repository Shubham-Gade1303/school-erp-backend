
package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.StudentAddressRequest;
import com.schooleERP.dto.StudentAddressResponse;
import com.schooleERP.entity.Student;
import com.schooleERP.entity.StudentAddress;
import com.schooleERP.repository.StudentAddressRepo;
import com.schooleERP.repository.StudentRepo;
import com.schooleERP.service.StudentAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentAddressServiceImpl implements StudentAddressService {

    private final StudentAddressRepo studentAddressRepo;
    private final StudentRepo studentRepo;

    public StudentAddressServiceImpl(
            StudentAddressRepo studentAddressRepo,
            StudentRepo studentRepo
    ) {
        this.studentAddressRepo = studentAddressRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public StudentAddressResponse createAddress(StudentAddressRequest request) {

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Student not found with id: " + request.getStudentId()
                        )
                );

        if (studentAddressRepo.existsByStudentId(request.getStudentId())) {
            throw new IllegalArgumentException(
                    "Address already exists for student id: " + request.getStudentId()
            );
        }

        StudentAddress address = new StudentAddress();

        address.setStudent(student);
        address.setAddressLine(request.getAddressLine());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());

        return mapToResponse(studentAddressRepo.save(address));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentAddressResponse> getAllAddresses() {

        return studentAddressRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentAddressResponse getAddressById(Long id) {

        StudentAddress address = studentAddressRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Student address not found with id: " + id
                        )
                );

        return mapToResponse(address);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentAddressResponse getAddressByStudent(Long studentId) {

        StudentAddress address = studentAddressRepo.findByStudentId(studentId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Address not found for student id: " + studentId
                        )
                );

        return mapToResponse(address);
    }

    @Override
    public StudentAddressResponse updateAddress(
            Long id,
            StudentAddressRequest request
    ) {

        StudentAddress address = studentAddressRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Student address not found with id: " + id
                        )
                );

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Student not found with id: " + request.getStudentId()
                        )
                );

        StudentAddress existingAddress =
                studentAddressRepo.findByStudentId(request.getStudentId())
                        .orElse(null);

        if (existingAddress != null
                && !existingAddress.getId().equals(id)) {

            throw new IllegalArgumentException(
                    "Address already exists for student id: "
                            + request.getStudentId()
            );
        }

        address.setStudent(student);
        address.setAddressLine(request.getAddressLine());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());

        return mapToResponse(studentAddressRepo.save(address));
    }

    @Override
    public void deleteAddress(Long id) {

        if (!studentAddressRepo.existsById(id)) {
            throw new IllegalArgumentException(
                    "Student address not found with id: " + id
            );
        }

        studentAddressRepo.deleteById(id);
    }

    private StudentAddressResponse mapToResponse(StudentAddress address) {

        Student student = address.getStudent();

        StudentAddressResponse response = new StudentAddressResponse();

        response.setId(address.getId());

        response.setStudentId(student.getId());
        response.setAdmissionNumber(student.getAdmissionNumber());

        String studentName =
                student.getFirstName()
                        + " "
                        + (student.getMiddleName() != null
                        ? student.getMiddleName() + " "
                        : "")
                        + student.getLastName();

        response.setStudentName(studentName.trim());

        response.setAddressLine(address.getAddressLine());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setPostalCode(address.getPostalCode());
        response.setCountry(address.getCountry());

        return response;
    }
}

