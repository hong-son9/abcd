package com.phs.application.controller.admin;

import com.phs.application.entity.Cart;
import com.phs.application.model.dto.CartResponse;
import com.phs.application.model.request.CartRequest;
import com.phs.application.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/addOrUpdateCart")
    public ResponseEntity<Cart> addOrUpdateCartItem(
            @RequestParam Long userId,
            @RequestParam String productId) {

        // Gọi dịch vụ để thêm hoặc cập nhật mục giỏ hàng
        Cart cartItem = cartService.addOrUpdateCartItem(userId, productId);

        // Tạo đối tượng CartResponse từ Cart
//        CartResponse cartResponse = new CartResponse(cartItem);

        // Trả về phản hồi
        return ResponseEntity.ok(cartItem);
    }
//    @PostMapping("/addOrUpdate")
//    public ResponseEntity<CartResponse> addOrUpdate(@RequestBody CartRequest cartRequest) {
//        Cart updatedCartItem = cartItemService.addOrUpdateCartItem(
//                cartRequest.getUserId(),
//                cartRequest.getProductId()
//        );
//        CartResponse cartResponse = new CartResponse(updatedCartItem);
//        return ResponseEntity.ok(cartResponse);
//    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<List<CartResponse>> getCartByIdUser(@PathVariable Long userId) {
        // Lấy danh sách các mục trong giỏ hàng của người dùng theo userId
        List<Cart> cartItems = cartService.getCartItemsByUserId(userId);

        // Chuyển đổi danh sách CartItem thành danh sách CartResponse
        List<CartResponse> cartResponses = cartItems.stream()
                .map(CartResponse::new)
                .collect(Collectors.toList()); // Sử dụng Collectors.toList()

        // Trả về danh sách CartResponse
        return ResponseEntity.ok(cartResponses);
    }

//    @DeleteMapping("/xóa/{userId}/{productId}")
//    public ResponseEntity<Void> removeCart(@PathVariable Long userId, @PathVariable String productId) {
//        cartItemService.removeCartItem(userId, productId);
//        return ResponseEntity.noContent().build();
//    }
}
