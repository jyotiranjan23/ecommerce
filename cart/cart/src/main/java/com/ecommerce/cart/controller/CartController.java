package com.ecommerce.cart.controller;

import com.ecommerce.cart.entity.CartRequest;
import com.ecommerce.cart.entity.CartResponse;
import com.ecommerce.cart.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ecommerce/cart")
public class CartController {
    private final CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(HttpServletRequest request, @RequestBody CartRequest cartRequest){
        String token = request.getHeader("Authorization").substring(7);
        return new ResponseEntity<>(cartService.createCart(cartRequest, token), HttpStatus.OK);
    }


}
