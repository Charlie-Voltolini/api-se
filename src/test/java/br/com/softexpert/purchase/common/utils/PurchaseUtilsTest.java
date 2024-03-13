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

class PurchaseUtilsTest {

    @Test
    @DisplayName("It must return total of purchase.")
    void calcTotalPurchase() {
        CustomerEntity customer1 = createCustomerWithItems("Hamburguer", new BigDecimal("25.00"));
        CustomerEntity customer2 = createCustomerWithItems("Hamburguer", new BigDecimal("25.00"));
        List<CustomerEntity> customerEntities = List.of(customer1, customer2);

        BigDecimal totalPurchase = PurchaseUtils.calcTotalPurchase(customerEntities);

        assertEquals(new BigDecimal("50.00"), totalPurchase);
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

        assertEquals(BigDecimal.ZERO.setScale(2), PurchaseUtils.calcTotalPurchase(customerEntities));
    }

    @Test
    @DisplayName("Discount and Delivery fee are ZERO.")
    void setFinalValue_BothZero() {
        BigDecimal totalPurchase = new BigDecimal("100.00");
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal deliveryFee = BigDecimal.ZERO;

        assertEquals(totalPurchase, PurchaseUtils.setFinalValue(totalPurchase, discount, deliveryFee));
    }

    @Test
    @DisplayName("Discount isn`t ZERO, but delivery fee is.")
    void setFinalValue_DiscountNotZero() {
        BigDecimal totalPurchase = new BigDecimal("100.00");
        BigDecimal discount = new BigDecimal("20.00");
        BigDecimal deliveryFee = BigDecimal.ZERO;

        assertEquals(new BigDecimal("80.00"), PurchaseUtils.setFinalValue(totalPurchase, discount, deliveryFee));
    }

    @Test
    @DisplayName("Discount is ZERO, but delivery fee isn`t.")
    void setFinalValue_DeliveryFeeNotZero() {
        BigDecimal totalPurchase = new BigDecimal("100.00");
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal deliveryFee = new BigDecimal("10.00");

        assertEquals(new BigDecimal("110.00"), PurchaseUtils.setFinalValue(totalPurchase, discount, deliveryFee));
    }

    @Test
    @DisplayName("Discount and delivery fee isn`t ZERO.")
    void setFinalValue_BothNotZero() {
        BigDecimal totalPurchase = new BigDecimal("100.00");
        BigDecimal discount = new BigDecimal("20.00");
        BigDecimal deliveryFee = new BigDecimal("10.00");

        assertEquals(new BigDecimal("90.00"), PurchaseUtils.setFinalValue(totalPurchase, discount, deliveryFee));
    }

    @Test
    @DisplayName("Needs to return the correctly percentage by customer.")
    void calcPercentageByCustomerTest() {
        CustomerEntity customer1 = createCustomerWithItems("Hamburguer", new BigDecimal("25.00"));
        CustomerEntity customer2 = createCustomerWithItems("Hamburguer", new BigDecimal("25.00"));
        List<CustomerEntity> customerEntityList = List.of(customer1, customer2);

        BigDecimal totalPurchase = new BigDecimal("50.00");
        PurchaseUtils.setPercentageByCustomers(totalPurchase, customerEntityList);

        BigDecimal expected = new BigDecimal("50.00");

        assertEquals(expected, customer1.getPercentage());
        assertEquals(expected, customer2.getPercentage());
    }

    @Test
    @DisplayName("Needs to return total payable by customer.")
    void setTotalPayableByCustomerTest() {
        BigDecimal finalValue = new BigDecimal("90.00");

        CustomerEntity customer1 = new CustomerEntity();
        CustomerEntity customer2 = new CustomerEntity();

        customer1.setPercentage(new BigDecimal("50.00"));
        customer2.setPercentage(new BigDecimal("50.00"));

        List<CustomerEntity> customerEntityList = List.of(customer1, customer2);

        PurchaseUtils.setTotalPayableByCustomer(finalValue, customerEntityList);

        assertEquals(new BigDecimal("45.00"), customer1.getTotalPayable());
        assertEquals(new BigDecimal("45.00"), customer2.getTotalPayable());
    }
}