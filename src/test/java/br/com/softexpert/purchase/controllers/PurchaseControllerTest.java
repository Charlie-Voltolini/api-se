package br.com.softexpert.purchase.controllers;

import br.com.softexpert.purchase.dto.CustomerDTO;
import br.com.softexpert.purchase.dto.ItemDTO;
import br.com.softexpert.purchase.dto.OrderDTO;
import br.com.softexpert.purchase.entity.OrderEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO: 12/03/2024 Necessário finalizar o teste. Corrigir primeiro o TotalPurchase
@WebMvcTest(PurchaseController.class)
class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PurchaseController purchaseController;

    private static final BigDecimal BIG_DECIMAL_ZERO = new BigDecimal(0);

    @Test
    @DisplayName("Case 1 - Should be sucess")
    public void testGetPurchaseInfoSuccess() throws Exception {
        List<ItemDTO> itemDTOList = new ArrayList<>();
        itemDTOList.add(new ItemDTO("Hamburguer", new BigDecimal(25)));

        List<CustomerDTO> customerDTOList = new ArrayList<>();
        CustomerDTO customerDTO1 = new CustomerDTO("Cliente 1",itemDTOList, BIG_DECIMAL_ZERO, BIG_DECIMAL_ZERO);
        CustomerDTO customerDTO2 = new CustomerDTO("Cliente 2",itemDTOList, BIG_DECIMAL_ZERO, BIG_DECIMAL_ZERO);
        CustomerDTO customerDTO3 = new CustomerDTO("Cliente 3",itemDTOList, BIG_DECIMAL_ZERO, BIG_DECIMAL_ZERO);
        CustomerDTO customerDTO4 = new CustomerDTO("Cliente 4",itemDTOList, BIG_DECIMAL_ZERO, BIG_DECIMAL_ZERO);
        customerDTOList.add(customerDTO1);
        customerDTOList.add(customerDTO2);
        customerDTOList.add(customerDTO3);
        customerDTOList.add(customerDTO4);

        OrderDTO orderDTO = new OrderDTO(customerDTOList, new BigDecimal("20.0"), new BigDecimal("8.0"));
        OrderEntity orderEntity = OrderEntity.buildFromDTO(orderDTO);

        Mockito.when(purchaseController.getPurchaseInfo(Mockito.any(OrderDTO.class)))
                .thenReturn(ResponseEntity.ok().body(orderEntity));

        mockMvc.perform(MockMvcRequestBuilders.post("/purchase/processOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("totalPurchase").value(100));
    }

    private static String asJsonString(OrderDTO orderDTO) {
        try {
            return new ObjectMapper().writeValueAsString(orderDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}