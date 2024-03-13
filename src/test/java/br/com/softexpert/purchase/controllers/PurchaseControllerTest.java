package br.com.softexpert.purchase.controllers;

import br.com.softexpert.purchase.dto.CustomerDTO;
import br.com.softexpert.purchase.dto.ItemDTO;
import br.com.softexpert.purchase.dto.OrderDTO;
import br.com.softexpert.purchase.entity.OrderEntity;
import br.com.softexpert.purchase.services.PurchaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseController.class)
class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PurchaseService purchaseService;

    @Test
    @DisplayName("It must return the value of total purchase.")
    public void testGetPurchaseInfoSuccess() throws Exception {
        List<ItemDTO> itemDTOList = Collections.singletonList(new ItemDTO("Hamburguer", new BigDecimal(25)));
        CustomerDTO customerDTO = new CustomerDTO("Cliente", itemDTOList, BigDecimal.ZERO, BigDecimal.ZERO);
        OrderDTO orderDTO = new OrderDTO(Collections.singletonList(customerDTO), BigDecimal.ZERO, BigDecimal.ZERO);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTotalPurchase(new BigDecimal("100.0"));

        Mockito.when(purchaseService.getPurchaseInformation(Mockito.any(OrderDTO.class)))
                .thenReturn(orderEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/purchase/processOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("totalPurchase").value("100.0"));
    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}