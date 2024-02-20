package br.com.softexpert.purchase.entity;

import br.com.softexpert.purchase.dto.ItemDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEntity {
    private String name;
    private BigDecimal value;

    public static ItemEntity buildFromDTO(ItemDTO dto) {
        return builder()
                .name(dto.name())
                .value(dto.value())
                .build();
    }
}
