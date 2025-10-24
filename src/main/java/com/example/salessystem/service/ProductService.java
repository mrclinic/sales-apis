package com.example.salessystem.service;

import com.example.salessystem.dto.ProductDTO;
import java.util.List;

public interface ProductService {
	List getAllProducts();

	ProductDTO createProduct(ProductDTO productDTO);

	ProductDTO updateProduct(Long id, ProductDTO productDTO);
}