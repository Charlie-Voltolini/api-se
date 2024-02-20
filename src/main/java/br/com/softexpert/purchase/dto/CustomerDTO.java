package br.com.softexpert.purchase.dto;

import java.math.BigDecimal;
import java.util.List;

public record CustomerDTO(
        String name,
        List<ItemDTO> itemList,
        BigDecimal percentage,
        BigDecimal totalPayable
) {}
