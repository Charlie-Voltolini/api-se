package br.com.softexpert.purchase.entity;

import br.com.softexpert.purchase.common.utils.PurchaseUtils;
import br.com.softexpert.purchase.dto.OrderDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    private List<CustomerEntity> customerEntityList;
    private BigDecimal discount;
    private BigDecimal deliveryFee;
    private BigDecimal totalPurchase;
    private BigDecimal finalValue;

    public void fillObjectValues() {
        this.totalPurchase = PurchaseUtils.calcTotalPurchase(customerEntityList);
        this.finalValue = PurchaseUtils.setFinalValue(totalPurchase, discount, deliveryFee);
        PurchaseUtils.setPercentageByCustomers(totalPurchase, customerEntityList);
        PurchaseUtils.setTotalPayableByCustomer(finalValue, customerEntityList);
    }

    public static OrderEntity buildFromDTO(OrderDTO dto) {
        return builder()
                .discount(dto.discount())
                .deliveryFee(dto.deliveryFee())
                .customerEntityList(dto.customerList().stream().map(CustomerEntity::buildFromDTO).toList())
                .build();
    }
}
