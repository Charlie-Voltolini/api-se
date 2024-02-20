package br.com.softexpert.purchase.dto;

import java.math.BigDecimal;

public record ItemDTO(
        String name,
        BigDecimal value
) {}
