package com.phs.application.service;

import com.phs.application.entity.Cart;
import com.phs.application.entity.Product;
import com.phs.application.entity.User;
import com.phs.application.repository.CartRepository;
import com.phs.application.repository.ProductRepository;
import com.phs.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Cart addOrUpdateCartItem(Long userId, String productId) {
        Cart cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        if (cartItem != null) {
            // If the cart item exists, you can update the quantity or other attributes here
            // For example, increasing quantity:
//            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            // If the cart item doesn't exist, create a new one
            cartItem = new Cart();
            cartItem.setUser(user);
            cartItem.setProduct(product);
//            cartItem.setQuantity(1); // Initialize quantity or other attributes as needed
        }
        System.out.println(product.toString());
        return cartItemRepository.save(cartItem);
    }


    public void removeCartItem(Long userId, String productId) {
        Cart cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
        }
    }
}
