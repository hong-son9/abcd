package com.phs.application.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartResponse {
    private Long cartId;
    private Long userId;
    private String productId;
    private int quantity;
    private String productName;
    private long productPrice;
    private long productSalePrice;
    private long productTotalSold;
    private int productStatus;
    private ArrayList<String> productImages;
    private String status;
    private String message;

}
