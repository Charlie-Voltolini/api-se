package br.com.softexpert.purchase.common.utils;

import br.com.softexpert.purchase.dto.CustomerDTO;
import br.com.softexpert.purchase.dto.ItemDTO;
import br.com.softexpert.purchase.entity.CustomerEntity;
import br.com.softexpert.purchase.entity.ItemEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PurchaseUtilsTest {

    @Test
    @DisplayName("It must return total of purchase.")
    void calcTotalPurchase() {

       CustomerEntity customer1 = new CustomerEntity();
       List<ItemEntity> itensCustomer1 = new ArrayList<>();
       itensCustomer1.add(new ItemEntity("Hamburguer", new BigDecimal("25")));
       customer1.setItemList(itensCustomer1);

        CustomerEntity customer2 = new CustomerEntity();
        List<ItemEntity> itensCustomer2 = new ArrayList<>();
        itensCustomer2.add(new ItemEntity("Hamburguer", new BigDecimal("25")));
        customer2.setItemList(itensCustomer2);

        List<CustomerEntity> customerEntities = new ArrayList<>();
        customerEntities.add(customer1);
        customerEntities.add(customer2);

        BigDecimal totalPurchase = PurchaseUtils.calcTotalPurchase(customerEntities);

        assertEquals(new BigDecimal("50"), totalPurchase);
    }

    @Test
    @DisplayName("It must return an error if the list was empty.")
    void calcTotalPurchase_EmptyList() throws IllegalArgumentException{
        List<CustomerEntity> customerEntities = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BigDecimal totalPurchase = PurchaseUtils.calcTotalPurchase(customerEntities);
        });
    }

    @Test
    @DisplayName("It must return zero if the list of litens was empty.")
    void calcTotalPurchase_EmptyItens() {
        CustomerEntity customer1 = new CustomerEntity();
        customer1.setItemList(new ArrayList<>());

        List<CustomerEntity> customerEntities = new ArrayList<>();
        customerEntities.add(customer1);

        BigDecimal totalPurchase = PurchaseUtils.calcTotalPurchase(customerEntities);

        assertEquals(BigDecimal.ZERO, totalPurchase);
    }
}
