package com.phs.application.model.dto;

import com.phs.application.entity.Cart;
import com.phs.application.entity.Product;

public class CartResponse {
    private Long id;
    private Long userId;
    private ProductDTO product;

    public CartResponse(Cart cartItem) {
        this.id = cartItem.getId(); // Lấy giá trị id từ Cart
        this.userId = cartItem.getUser().getId(); // Lấy userId từ Cart

        // Chuyển đổi từ Product sang ProductDTO
        Product product = cartItem.getProduct();
        this.product = new ProductDTO(product);
    }
}
