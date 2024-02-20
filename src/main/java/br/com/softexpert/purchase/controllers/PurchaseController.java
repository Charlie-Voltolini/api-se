package br.com.softexpert.purchase.controllers;

import br.com.softexpert.purchase.dto.OrderDTO;
import br.com.softexpert.purchase.entity.OrderEntity;
import br.com.softexpert.purchase.services.PurchaseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @ResponseBody
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/processOrder")
    public ResponseEntity<OrderEntity> getPurchaseInfo(@Valid @RequestBody OrderDTO orderDTO) {
        OrderEntity purchaseEntity = purchaseService.getPurchaseInformation(orderDTO);
        return ResponseEntity.ok(purchaseEntity);
    }
}
