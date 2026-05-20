package com.ecommerce.cart.externalService;

import com.ecommerce.cart.config.FeignConfig;
import com.ecommerce.cart.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE", url = "PRODUCT-SERVICE", configuration = FeignConfig.class)
public interface ProductService {
    @GetMapping("/ecommerce/product/{id}")
    Product getProduct(@PathVariable("id") Long id);
}
