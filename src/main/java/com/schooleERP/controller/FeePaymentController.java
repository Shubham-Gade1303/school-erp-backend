package com.schooleERP.controller;

import com.schooleERP.dto.FeePaymentRequest;
import com.schooleERP.dto.FeePaymentResponse;
import com.schooleERP.service.FeePaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/fee-payments")
public class FeePaymentController {

    private final FeePaymentService feePaymentService;

    public FeePaymentController(FeePaymentService feePaymentService) {
        this.feePaymentService = feePaymentService;
    }


    // CREATE PAYMENT

    @PostMapping

    public ResponseEntity<FeePaymentResponse> createPayment(
            @Valid @RequestBody FeePaymentRequest request
    ) {

        FeePaymentResponse response =
                feePaymentService.createPayment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET ALL PAYMENTS


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<FeePaymentResponse>> getAllPayments() {

        return ResponseEntity.ok(feePaymentService.getAllPayments());
    }

    // ==========================================
    // GET PAYMENT BY ID
    // ==========================================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<FeePaymentResponse> getPaymentById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(feePaymentService.getPaymentById(id));
    }

    // GET PAYMENTS BY STUDENT FEE

    @GetMapping("/student-fee/{studentFeeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<FeePaymentResponse>>
    getPaymentsByStudentFee(
            @PathVariable Long studentFeeId
    ) {

        return ResponseEntity.ok(
                feePaymentService.getPaymentsByStudentFee(studentFeeId));
    }

    // GET PAYMENTS BY DATE


    @GetMapping("/date/{paymentDate}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<FeePaymentResponse>>
    getPaymentsByDate(
            @PathVariable LocalDate paymentDate
    ) {
        return ResponseEntity.ok(feePaymentService.getPaymentsByDate(paymentDate));
    }
    // GET PAYMENT BY RECEIPT NUMBER

    @GetMapping("/receipt/{receiptNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<FeePaymentResponse>
    getPaymentByReceiptNumber(@PathVariable String receiptNumber
    ) {
        return ResponseEntity.ok(feePaymentService.getPaymentByReceiptNumber(receiptNumber));
    }

    // GET PAYMENTS BY PAYMENT METHOD

    @GetMapping("/method/{paymentMethod}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<FeePaymentResponse>>
    getPaymentsByMethod(
            @PathVariable String paymentMethod
    ) {
        return ResponseEntity.ok(feePaymentService.getPaymentsByMethod(paymentMethod));
    }

    // UPDATE PAYMENT
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<FeePaymentResponse> updatePayment(@PathVariable Long id, @Valid @RequestBody FeePaymentRequest request
    ) {
        return ResponseEntity.ok(feePaymentService.updatePayment(id, request));
    }

    // DELETE PAYMENT

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deletePayment(
            @PathVariable Long id
    ) {

        feePaymentService.deletePayment(id);

        return ResponseEntity.noContent().build();
    }
}
