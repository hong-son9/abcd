package com.phs.application.service;

import com.phs.application.entity.Cart;
import com.phs.application.entity.Product;
import com.phs.application.entity.User;
import com.phs.application.model.dto.CartResponse;
import com.phs.application.model.dto.ProductInfoDTO;
import com.phs.application.model.response.ProductResponse;
import com.phs.application.repository.CartRepository;
import com.phs.application.repository.ProductRepository;
import com.phs.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Cart> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public CartResponse addOrUpdateCartItem(Long userId, String productId) {
        // Tìm giỏ hàng hiện tại của người dùng
        List<Cart> existingCarts = cartItemRepository.findByUserId(userId);

        // Tìm người dùng và sản phẩm
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        Cart cartItem = null;

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng của người dùng chưa
        for (Cart cart : existingCarts) {
            if (cart.getProduct().getId().equals(productId)) {
                cartItem = cart;
                break;
            }
        }

        if (cartItem != null) {
            // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
            cartItem.setQuantity(cartItem.getQuantity() + 1); // Ví dụ: tăng số lượng lên 1
        } else {
            // Nếu sản phẩm chưa có trong giỏ hàng, tạo mới
            cartItem = new Cart();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(1); // Khởi tạo số lượng mặc định là 1
        }

        Cart updatedCartItem = cartItemRepository.save(cartItem); // Lưu và trả về cartItem đã cập nhật hoặc mới

        // Trả về đối tượng CartResponse với thông tin cần thiết
        return new CartResponse(
                updatedCartItem.getId(), // ID của giỏ hàng
                userId,
                productId,
                updatedCartItem.getQuantity(), // Số lượng trong giỏ hàng
                product.getName(),
                product.getPrice(),
                product.getSalePrice(),
                product.getTotalSold(),
                product.getStatus(),
                product.getImages(),
                "OK",
                "SUCCESS"
        );
    }
    public List<CartResponse> getCartByUserId(Long userId) {
        List<Cart> cartItems = cartItemRepository.findByUserId(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        List<CartResponse> cartResponses = new ArrayList<>();
        for (Cart cartItem : cartItems) {
            Product product = cartItem.getProduct();
            CartResponse response = new CartResponse(
                    cartItem.getId(),
                    userId,
                    product.getId(),
                    cartItem.getQuantity(),
                    product.getName(),
                    product.getPrice(),
                    product.getSalePrice(),
                    product.getTotalSold(),
                    product.getStatus(),
                    product.getImages(),
                    "OK",
                    "SUCCESS"
            );
            cartResponses.add(response);
        }
        return cartResponses;
    }

}
