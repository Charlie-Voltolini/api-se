package br.com.softexpert.purchase.common.utils;

import br.com.softexpert.purchase.entity.CustomerEntity;
import br.com.softexpert.purchase.entity.ItemEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TotalPurchaseUnitTest {

    @Test
    @DisplayName("It must return total of purchase.")
    void calcTotalPurchase() {
        CustomerEntity customer1 = createCustomerWithItems("Hamburguer", new BigDecimal("25"));
        CustomerEntity customer2 = createCustomerWithItems("Hamburguer", new BigDecimal("25"));
        List<CustomerEntity> customerEntities = List.of(customer1, customer2);

        BigDecimal totalPurchase = PurchaseUtils.calcTotalPurchase(customerEntities);

        assertEquals(new BigDecimal("50"), totalPurchase);
    }

    private CustomerEntity createCustomerWithItems(String itemName, BigDecimal itemValue) {
        CustomerEntity customer = new CustomerEntity();
        List<ItemEntity> items = List.of(new ItemEntity(itemName, itemValue));
        customer.setItemList(items);
        return customer;
    }

    @Test
    @DisplayName("It must get an exception if the list was empty.")
    void calcTotalPurchase_EmptyList() throws IllegalArgumentException{
        List<CustomerEntity> customerEntities = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            PurchaseUtils.calcTotalPurchase(customerEntities);
        });
    }

    @Test
    @DisplayName("It must return zero if the list of itens was empty.")
    void calcTotalPurchase_EmptyItensList() {
        CustomerEntity customer1 = new CustomerEntity();
        customer1.setItemList(Collections.emptyList());

        List<CustomerEntity> customerEntities = Collections.singletonList(customer1);

        assertEquals(BigDecimal.ZERO, PurchaseUtils.calcTotalPurchase(customerEntities));
    }

}
