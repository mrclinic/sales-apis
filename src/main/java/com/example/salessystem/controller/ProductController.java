package com.example.salessystem.controller;

import com.example.salessystem.dto.ProductDTO;
import com.example.salessystem.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allows requests from frontend clients
@Tag(name = "Products", description = "API for managing products")
public class ProductController {

	private final ProductService productService;

	/**
	 * Fetch all available products.
	 *
	 * @return List of all ProductDTOs
	 */
	@Operation(summary = "Fetch all products", description = "Retrieve all available products from the system.")
	@ApiResponse(responseCode = "200", description = "Products fetched successfully")
	@GetMapping
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
		List<ProductDTO> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}

	/**
	 * Create a new product.
	 *
	 * @param productDTO Product details
	 * @return Created ProductDTO with status 201
	 */
	@Operation(summary = "Create a new product", description = "Add a new product to the catalog.")
	@ApiResponse(responseCode = "201", description = "Product created successfully")
	@PostMapping
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
		ProductDTO createdProduct = productService.createProduct(productDTO);
		return ResponseEntity.created(URI.create("/api/products/" + createdProduct.getId())).body(createdProduct);
	}

	/**
	 * Update an existing product.
	 *
	 * @param id         Product ID
	 * @param productDTO Updated product data
	 * @return Updated ProductDTO or 404 if not found
	 */
	@Operation(summary = "Update a product", description = "Update an existing product’s details by ID.")
	@ApiResponse(responseCode = "200", description = "Product updated successfully")
	@ApiResponse(responseCode = "404", description = "Product not found")
	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
		ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
		if (updatedProduct == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(updatedProduct);
	}
}
