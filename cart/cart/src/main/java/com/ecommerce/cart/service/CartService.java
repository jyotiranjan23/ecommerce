package com.ecommerce.cart.service;

import com.ecommerce.cart.entity.*;
import com.ecommerce.cart.externalService.ProductService;
import com.ecommerce.cart.repository.CartRepository;
import com.ecommerce.cart.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final JWTUtil jwtutil;
    private final ProductService productService;
    private final CartRepository cartRepository;

    public CartResponse createCart(CartRequest cartRequest, String token) {
        List<CartItemRequest> cartItemsRequest = cartRequest.getCartItems();
        cartItemsRequest = cartItemsRequest.stream()
                .filter(cartitemRequest -> {
                    Product product = productService.getProduct(cartitemRequest.getProductId());
                    return product.getStock() >= cartitemRequest.getQuantity();
                }).toList();
        Cart cart = new Cart();
        Long userId = Long.parseLong(jwtutil.extractClaims(token).getSubject());
        cart.setUserId(userId);
        cart.setCartItems(mapToCartItem(cartItemsRequest, cart));
        Cart savedCart = cartRepository.save(cart);

        return (mapToCartResponse(savedCart));


    }

    private CartResponse mapToCartResponse(Cart savedCart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setUserId(savedCart.getUserId());
        cartResponse.setItems(mapToCartItemResponse(savedCart.getCartItems()));
        return cartResponse;
    }

    private List<CartItem> mapToCartItem(List<CartItemRequest> cartItems, Cart cart) {
        return cartItems.stream()
                .map(cartItemRequest -> {
                    CartItem cartItem = new CartItem();
                    cartItem.setPrice(productService.getProduct(cartItemRequest.getProductId()).getPrice());
                    cartItem.setProductId(cartItemRequest.getProductId());
                    cartItem.setQuantity(cartItemRequest.getQuantity());
                    cartItem.setCart(cart);
                    return cartItem;

                }).toList();
    }

    private List<CartItemResponse> mapToCartItemResponse(List<CartItem> cartItems) {
        List<CartItemResponse> cartItemResponseList = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            CartItemResponse cartItemResponse = new CartItemResponse();
            cartItemResponse.setPrice(cartItem.getPrice());
            cartItemResponse.setProductId(cartItem.getProductId());
            cartItemResponse.setQuantity(cartItem.getQuantity());
            cartItemResponseList.add(cartItemResponse);

        }
        return cartItemResponseList;

    }

}
