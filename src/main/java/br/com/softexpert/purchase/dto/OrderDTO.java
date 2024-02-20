package br.com.softexpert.purchase.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record OrderDTO(
        @NotEmpty
        @NotNull
        List<CustomerDTO> customerList,
        @NotNull
        BigDecimal discount,
        @NotNull
        BigDecimal deliveryFee) {}
