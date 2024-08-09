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
    public ResponseEntity<CartResponse> addOrUpdateCartItem(
            @RequestParam Long userId,
            @RequestParam String productId) {

        // Gọi dịch vụ để thêm hoặc cập nhật mục giỏ hàng và nhận phản hồi
        CartResponse cartResponse = cartService.addOrUpdateCartItem(userId, productId);

        // Trả về phản hồi với mã trạng thái HTTP 200 (OK) và đối tượng CartResponse
        return ResponseEntity.ok(cartResponse);
    }
    @GetMapping("/getCartByUserId")
    public ResponseEntity<List<CartResponse>> getCartByUserId(@RequestParam Long userId) {
        // Gọi dịch vụ để lấy giỏ hàng theo ID người dùng
        List<CartResponse> cartResponses = cartService.getCartByUserId(userId);

        // Trả về phản hồi với mã trạng thái HTTP 200 (OK) và danh sách CartResponse
        return ResponseEntity.ok(cartResponses);
    }
}
