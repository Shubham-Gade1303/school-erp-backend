package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.FeePaymentRequest;
import com.schooleERP.dto.FeePaymentResponse;
import com.schooleERP.entity.FeePayment;
import com.schooleERP.entity.Student;
import com.schooleERP.entity.StudentFees;
import com.schooleERP.repository.FeePaymentRepo;
import com.schooleERP.repository.StudentFeesRepo;
import com.schooleERP.service.FeePaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class FeePaymentServiceImpl implements FeePaymentService {

    private final FeePaymentRepo feePaymentRepo;
    private final StudentFeesRepo studentFeesRepo;

    public FeePaymentServiceImpl(FeePaymentRepo feePaymentRepo, StudentFeesRepo studentFeesRepo
    ) {
        this.feePaymentRepo = feePaymentRepo;
        this.studentFeesRepo = studentFeesRepo;
    }

    // =========================
    // CREATE PAYMENT
    // =========================

    @Override
    public FeePaymentResponse createPayment(FeePaymentRequest request) {

        StudentFees studentFee = studentFeesRepo.findById(request.getStudentFeeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Student fee not found with id: " + request.getStudentFeeId())
                );

        // Check duplicate receipt number
        if (feePaymentRepo.existsByReceiptNumber(request.getReceiptNumber())) {
            throw new IllegalArgumentException("Receipt number already exists: " + request.getReceiptNumber());
        }

        // Calculate already paid amount
        BigDecimal totalPaid = calculateTotalPaid(studentFee);

        // Calculate outstanding amount
        BigDecimal outstandingAmount = studentFee.getAmount().subtract(totalPaid);

        // Prevent overpayment
        if (request.getAmountPaid().compareTo(outstandingAmount) > 0) {
            throw new IllegalArgumentException(
                    "Payment amount exceeds outstanding amount. " + "Outstanding amount: " + outstandingAmount);
        }

        FeePayment payment = new FeePayment();

        payment.setStudentFee(studentFee);
        payment.setPaymentDate(request.getPaymentDate());
        payment.setAmountPaid(request.getAmountPaid());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionReference(request.getTransactionReference());
        payment.setReceiptNumber(request.getReceiptNumber());
        payment.setRemarks(request.getRemarks());

        FeePayment savedPayment = feePaymentRepo.save(payment);

        feePaymentRepo.flush();

        // Update fee status
        updateFeeStatus(studentFee);

        return mapToResponse(savedPayment);
    }

    // =========================
    // GET ALL PAYMENTS
    // =========================

    @Override
    @Transactional(readOnly = true)
    public List<FeePaymentResponse> getAllPayments() {

        return feePaymentRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================
    // GET PAYMENT BY ID
    // =========================

    @Override
    @Transactional(readOnly = true)
    public FeePaymentResponse getPaymentById(Long id) {

        FeePayment payment = feePaymentRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Fee payment not found with id: " + id
                        )
                );

        return mapToResponse(payment);
    }

    // =========================
    // GET PAYMENTS BY STUDENT FEE
    // =========================

    @Override
    @Transactional(readOnly = true)
    public List<FeePaymentResponse> getPaymentsByStudentFee(Long studentFeeId) {

        if (!studentFeesRepo.existsById(studentFeeId)) {
            throw new IllegalArgumentException("Student fee not found with id: " + studentFeeId);
        }

        return feePaymentRepo.findByStudentFeeId(studentFeeId).stream().map(this::mapToResponse).toList();
    }

    // =========================
    // GET PAYMENTS BY DATE
    // =========================

    @Override
    @Transactional(readOnly = true)
    public List<FeePaymentResponse> getPaymentsByDate(LocalDate paymentDate) {

        return feePaymentRepo.findByPaymentDate(paymentDate)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================
    // GET PAYMENT BY RECEIPT
    // =========================

    @Override
    @Transactional(readOnly = true)
    public FeePaymentResponse getPaymentByReceiptNumber(
            String receiptNumber
    ) {

        FeePayment payment = feePaymentRepo
                .findByReceiptNumber(receiptNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException("Payment not found with receipt number: " + receiptNumber));

        return mapToResponse(payment);
    }

    // =========================
    // GET PAYMENTS BY METHOD
    // =========================

    @Override
    @Transactional(readOnly = true)
    public List<FeePaymentResponse> getPaymentsByMethod(String paymentMethod
    ) {

        return feePaymentRepo.findByPaymentMethod(paymentMethod)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================
    // UPDATE PAYMENT
    // =========================

    @Override
    public FeePaymentResponse updatePayment(Long id, FeePaymentRequest request
    ) {

        FeePayment payment = feePaymentRepo.findById(id).orElseThrow(() -> new IllegalArgumentException(
                                "Fee payment not found with id: " + id));

        StudentFees oldStudentFee = payment.getStudentFee();

        StudentFees newStudentFee = studentFeesRepo
                .findById(request.getStudentFeeId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Student fee not found with id: " + request.getStudentFeeId()));

        // Check receipt number uniqueness
        FeePayment paymentWithSameReceipt =
                feePaymentRepo.findByReceiptNumber(request.getReceiptNumber()).orElse(null);

        if (paymentWithSameReceipt != null
                && !paymentWithSameReceipt.getId().equals(id)) {

            throw new IllegalArgumentException(
                    "Receipt number already exists: "
                            + request.getReceiptNumber()
            );
        }

        // =========================
        // CHECK PAYMENT LIMIT
        // =========================

        BigDecimal totalPaidForNewFee =
                calculateTotalPaid(newStudentFee);

        /*
         * If the payment is being updated for the same
         * StudentFees record, remove the old payment
         * amount from the calculation.
         */
        if (oldStudentFee.getId().equals(newStudentFee.getId())) {

            totalPaidForNewFee =
                    totalPaidForNewFee.subtract(
                            payment.getAmountPaid()
                    );
        }

        BigDecimal outstandingAmount =
                newStudentFee.getAmount()
                        .subtract(totalPaidForNewFee);

        if (request.getAmountPaid().compareTo(outstandingAmount) > 0) {

            throw new IllegalArgumentException(
                    "Payment amount exceeds outstanding amount. " + "Outstanding amount: " + outstandingAmount);
        }

        // =========================
        // UPDATE PAYMENT
        // =========================

        payment.setStudentFee(newStudentFee);
        payment.setPaymentDate(request.getPaymentDate());
        payment.setAmountPaid(request.getAmountPaid());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionReference(
                request.getTransactionReference()
        );
        payment.setReceiptNumber(request.getReceiptNumber());
        payment.setRemarks(request.getRemarks());

        FeePayment updatedPayment =
                feePaymentRepo.save(payment);

        feePaymentRepo.flush();

        // Update old fee status if payment moved
        if (!oldStudentFee.getId().equals(newStudentFee.getId())) {
            updateFeeStatus(oldStudentFee);
        }

        // Update new fee status
        updateFeeStatus(newStudentFee);

        return mapToResponse(updatedPayment);
    }

    // =========================
    // DELETE PAYMENT
    // =========================

    @Override
    public void deletePayment(Long id) {

        FeePayment payment = feePaymentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fee payment not found with id: " + id));

        StudentFees studentFee = payment.getStudentFee();

        feePaymentRepo.delete(payment);

        feePaymentRepo.flush();

        // Recalculate fee status
        updateFeeStatus(studentFee);
    }

    // =========================
    // CALCULATE TOTAL PAID
    // =========================

    private BigDecimal calculateTotalPaid(
            StudentFees studentFee
    ) {

        return feePaymentRepo
                .findByStudentFeeId(studentFee.getId())
                .stream()
                .map(FeePayment::getAmountPaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // =========================
    // UPDATE FEE STATUS
    // =========================

    private void updateFeeStatus(
            StudentFees studentFee
    ) {

        BigDecimal totalPaid = calculateTotalPaid(studentFee);

        BigDecimal feeAmount = studentFee.getAmount();

        if (totalPaid.compareTo(feeAmount) >= 0) {

            studentFee.setStatus("PAID");

        } else if (
                studentFee.getDueDate() != null
                        && studentFee.getDueDate().isBefore(LocalDate.now())
        ) {

            studentFee.setStatus("OVERDUE");

        } else if (totalPaid.compareTo(BigDecimal.ZERO) > 0) {

            studentFee.setStatus("PARTIAL");

        } else {

            studentFee.setStatus("PENDING");
        }

        studentFeesRepo.save(studentFee);
    }

    // =========================
    // MAP ENTITY → RESPONSE
    // =========================

    private FeePaymentResponse mapToResponse(
            FeePayment payment
    ) {

        StudentFees studentFee = payment.getStudentFee();

        Student student = studentFee.getStudent();

        FeePaymentResponse response = new FeePaymentResponse();

        response.setId(payment.getId());

        response.setStudentFeeId(studentFee.getId());

        response.setStudentId(student.getId());

        response.setAdmissionNumber(student.getAdmissionNumber());

        response.setStudentName(buildStudentName(student));

        response.setFeeType(studentFee.getFeeType());

        response.setFeeAmount(studentFee.getAmount());

        response.setPaymentDate(payment.getPaymentDate());

        response.setAmountPaid(payment.getAmountPaid());

        response.setPaymentMethod(payment.getPaymentMethod());

        response.setTransactionReference(payment.getTransactionReference());

        response.setReceiptNumber(payment.getReceiptNumber());

        response.setRemarks(payment.getRemarks());

        return response;
    }

    // =========================
    // BUILD STUDENT NAME
    // =========================

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
