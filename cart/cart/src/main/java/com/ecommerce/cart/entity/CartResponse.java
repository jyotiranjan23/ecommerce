package com.ecommerce.cart.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartResponse {

    private Long userId;
    private List<CartItemResponse> items;
}
