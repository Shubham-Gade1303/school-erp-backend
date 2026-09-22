package com.schooleERP.service;

import com.schooleERP.dto.FeePaymentRequest;
import com.schooleERP.dto.FeePaymentResponse;

import java.time.LocalDate;
import java.util.List;

public interface FeePaymentService {

    FeePaymentResponse createPayment(FeePaymentRequest request);

    List<FeePaymentResponse> getAllPayments();

    FeePaymentResponse getPaymentById(Long id);

    List<FeePaymentResponse> getPaymentsByStudentFee(Long studentFeeId);

    List<FeePaymentResponse> getPaymentsByDate(LocalDate paymentDate);

    FeePaymentResponse getPaymentByReceiptNumber(String receiptNumber);

    List<FeePaymentResponse> getPaymentsByMethod(String paymentMethod);

    FeePaymentResponse updatePayment(Long id, FeePaymentRequest request);

    void deletePayment(Long id);
}
