package br.com.softexpert.purchase.services;

import br.com.softexpert.purchase.dto.OrderDTO;
import br.com.softexpert.purchase.entity.OrderEntity;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    public OrderEntity getPurchaseInformation(OrderDTO dto) {
        OrderEntity newPurchaseEntity = OrderEntity.buildFromDTO(dto);
        newPurchaseEntity.fillObjectValues();
        return newPurchaseEntity;
    }
}
