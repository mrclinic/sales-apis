package com.example.salessystem.service.impl;



import com.example.salessystem.dto.ProductDTO;
import com.example.salessystem.entity.Product;
import com.example.salessystem.repository.ProductRepository;
import com.example.salessystem.service.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;

	@Override
	public List<ProductDTO> getAllProducts() {
		return productRepository.findAll().stream()
				.map(p -> ProductDTO.builder().id(p.getId()).name(p.getName()).description(p.getDescription())
						.category(p.getCategory()).creationDate(p.getCreationDate()).build())
				.collect(Collectors.toList());
	}

	@Override
	public ProductDTO createProduct(ProductDTO productDTO) {
		Product product = Product.builder().name(productDTO.getName()).description(productDTO.getDescription())
				.category(productDTO.getCategory()).creationDate(LocalDateTime.now()).build();
		Product saved = productRepository.save(product);
		productDTO.setId(saved.getId());
		productDTO.setCreationDate(saved.getCreationDate());
		return productDTO;
	}

	@Override
	public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
		return productRepository.findById(id).map(product -> {
			product.setName(productDTO.getName());
			product.setDescription(productDTO.getDescription());
			product.setCategory(productDTO.getCategory());
			productRepository.save(product);
			productDTO.setCreationDate(product.getCreationDate());
			return productDTO;
		}).orElse(null);
	}
}