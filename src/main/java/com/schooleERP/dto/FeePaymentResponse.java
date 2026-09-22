
package com.schooleERP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeePaymentResponse {

    private Long id;

    private Long studentFeeId;

    private Long studentId;
    private String admissionNumber;
    private String studentName;

    private String feeType;
    private BigDecimal feeAmount;

    private LocalDate paymentDate;
    private BigDecimal amountPaid;

    private String paymentMethod;
    private String transactionReference;
    private String receiptNumber;

    private String remarks;
}
