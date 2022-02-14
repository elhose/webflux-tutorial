package com.js.orderservice.mapper;

import com.js.orderservice.dto.OrderStatus;
import com.js.orderservice.dto.PurchaseOrderResponseDTO;
import com.js.orderservice.dto.service.user.TransactionStatus;
import com.js.orderservice.entity.PurchaseOrder;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public OrderStatus mapTransactionStatusToOrderStatus(TransactionStatus transactionStatus) {
        return transactionStatus == TransactionStatus.APPROVED ? OrderStatus.COMPLETED : OrderStatus.FAILED;
    }

    public PurchaseOrderResponseDTO entityToResponseDTO(PurchaseOrder purchaseOrder) {
        return new PurchaseOrderResponseDTO(purchaseOrder.getId(), purchaseOrder.getUserId(),
                                            purchaseOrder.getProductId(), purchaseOrder.getAmount(),
                                            purchaseOrder.getOrderStatus());
    }
}
