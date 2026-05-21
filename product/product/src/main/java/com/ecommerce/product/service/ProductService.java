package com.ecommerce.product.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.repository.ProductRepository;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @PreAuthorize("hasRole('ADMIN')")
    public void createProduct(ProductRequest productRequest){
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setStock(productRequest.getStock());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        productRepository.save(product);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("No product found: "+id));
            return mapToProductResponse(product);
        
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll()
                                .stream()
                                .map(product -> mapToProductResponse(product))
                                .toList();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse updateProduct(Long id, ProductRequest productRequest){
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException(" Product not Found with the id: "+id));
        product.setName(productRequest.getName());
        product.setStock(productRequest.getStock());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        productRepository.save(product);
        log.info("Image url {}", product.getImageUrl());
        return mapToProductResponse(product);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException(" Product not Found with the id: "+id));
        productRepository.delete(product);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<ProductResponse> getProductByKeyword(String keyword){
        return productRepository.searchProducts(keyword).stream().map(this::mapToProductResponse).toList();
    }
    private ProductResponse mapToProductResponse(Product product){
        ProductResponse productResponse = new ProductResponse();
            productResponse.setId(product.getId());
            productResponse.setName(product.getName());
            productResponse.setStock(product.getStock());
            productResponse.setDescription(product.getDescription());
            productResponse.setCategory(product.getCategory());
            productResponse.setPrice(product.getPrice());
            productResponse.setImageUrl(product.getImageUrl());
            return productResponse;
    }
    

}