package com.schooleERP.repository;

import com.schooleERP.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeePaymentRepo extends JpaRepository<FeePayment, Long> {

    List<FeePayment> findByStudentFeeId(Long studentFeeId);

    List<FeePayment> findByPaymentDate(LocalDate paymentDate);

    Optional<FeePayment> findByReceiptNumber(String receiptNumber);

    boolean existsByReceiptNumber(String receiptNumber);

    List<FeePayment> findByPaymentMethod(String paymentMethod);
}
