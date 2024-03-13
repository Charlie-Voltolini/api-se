package br.com.softexpert.purchase.common.utils;

import br.com.softexpert.purchase.entity.CustomerEntity;
import br.com.softexpert.purchase.entity.ItemEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PurchaseUtils {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    public static BigDecimal calcTotalPurchase(List<CustomerEntity> customerEntityList) {
        if (customerEntityList == null || customerEntityList.isEmpty()) {
            throw new IllegalArgumentException("A lista de clientes não pode ser vazia ou nula.");
        }
        return customerEntityList.stream().map(PurchaseUtils::getTotalItensByCustomer).reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.DOWN);
    }

    private static BigDecimal getTotalItensByCustomer(CustomerEntity customerEntity) {
        List<ItemEntity> itemList = customerEntity.getItemList();
        if (itemList == null || itemList.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return itemList.stream().map(ItemEntity::getValue).reduce(new BigDecimal("0"),BigDecimal::add);
    }

    public static BigDecimal setFinalValue(BigDecimal totalPurchase,BigDecimal discount, BigDecimal deliveryFee) {
        BigDecimal finalValue = totalPurchase;
        if (!discount.equals(BigDecimal.ZERO)) {
            finalValue = PurchaseUtils.applyDiscount(totalPurchase, discount);
        }
        if (!deliveryFee.equals(BigDecimal.ZERO)) {
            finalValue = PurchaseUtils.applyDeliveryFee(finalValue, deliveryFee);
        }
        return finalValue.setScale(2, RoundingMode.DOWN);
    }

    private static BigDecimal applyDeliveryFee(BigDecimal totalPurchase, BigDecimal deliveryFee) {
        return totalPurchase.add(deliveryFee);
    }

    private static BigDecimal applyDiscount(BigDecimal totalPurchase, BigDecimal discount) {
        return totalPurchase.subtract(discount);
    }

    public static void setPercentageByCustomers(BigDecimal totalPurchase, List<CustomerEntity> customerEntityList) {
        customerEntityList.parallelStream().forEach(i -> i.setPercentage(calcPercentageByCustomer(totalPurchase, i)));
    }

    private static BigDecimal calcPercentageByCustomer(BigDecimal total, CustomerEntity customerEntity) {
        BigDecimal totalSpent = PurchaseUtils.getTotalItensByCustomer(customerEntity);
        return totalSpent.divide(total, 2, RoundingMode.HALF_UP).multiply(ONE_HUNDRED)
                .setScale(2, RoundingMode.DOWN);
    }

    public static void setTotalPayableByCustomer(BigDecimal finalValue, List<CustomerEntity> customerEntityList) {
        customerEntityList.parallelStream().forEach(i -> i.setTotalPayable(calcTotalPayableFromCustomer(finalValue, i)));
    }

    private static BigDecimal calcTotalPayableFromCustomer(BigDecimal finalValue, CustomerEntity customerEntity) {
        BigDecimal customerPercentage = customerEntity.getPercentage();
        return customerPercentage.multiply(finalValue).divide(ONE_HUNDRED)
                .setScale(2, RoundingMode.DOWN);
    }
}
