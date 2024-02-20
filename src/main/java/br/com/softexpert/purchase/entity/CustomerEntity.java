package br.com.softexpert.purchase.entity;

import br.com.softexpert.purchase.dto.CustomerDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerEntity {
    private String name;
    @Builder.Default
    private List<ItemEntity> itemList = new ArrayList<>();
    private BigDecimal percentage;
    private BigDecimal totalPayable;

    public static CustomerEntity buildFromDTO(CustomerDTO dto) {
        return builder()
                .name(dto.name())
                .itemList(dto.itemList().stream().map(ItemEntity::buildFromDTO).toList())
                .percentage(dto.percentage())
                .totalPayable(dto.totalPayable())
                .build();
    }
}
