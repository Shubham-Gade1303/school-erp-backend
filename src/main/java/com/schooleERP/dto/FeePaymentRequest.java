package com.schooleERP.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class FeePaymentRequest {

    @NotNull
    private Long studentFeeId;

    @NotNull
    private LocalDate paymentDate;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amountPaid;

    @NotBlank
    @Size(max = 30)
    private String paymentMethod;

    @Size(max = 100)
    private String transactionReference;

    @NotBlank
    @Size(max = 50)
    private String receiptNumber;

    @Size(max = 500)
    private String remarks;
}
