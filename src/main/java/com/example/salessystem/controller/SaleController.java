package com.example.salessystem.controller;

import com.example.salessystem.dto.SaleDTO;
import com.example.salessystem.service.SaleService;

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
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Sales", description = "API for managing sales operations")
public class SaleController {

	private final SaleService saleService;

	@Operation(summary = "Fetch all sales", description = "Retrieve all sales operations.")
	@ApiResponse(responseCode = "200", description = "Sales fetched successfully")
	@GetMapping
	public ResponseEntity<List<SaleDTO>> getAllSales() {
		List<SaleDTO> sales = saleService.getAllSales();
		return ResponseEntity.ok(sales);
	}

	@Operation(summary = "Create a new sale", description = "Add a new sale with multiple items.")
	@ApiResponse(responseCode = "201", description = "Sale created successfully")
	@PostMapping
	public ResponseEntity<SaleDTO> createSale(@Valid @RequestBody SaleDTO saleDTO) {
		SaleDTO createdSale = saleService.createSale(saleDTO);
		return ResponseEntity.created(URI.create("/api/sales/" + createdSale.getId())).body(createdSale);
	}

	@Operation(summary = "Update a sale item", description = "Edit quantity and price of a sale item.")
	@ApiResponse(responseCode = "200", description = "Sale item updated successfully")
	@ApiResponse(responseCode = "404", description = "Sale or item not found")
	@PutMapping("/{saleId}/items/{productId}")
	public ResponseEntity<SaleDTO> updateSaleItem(@PathVariable Long saleId, @PathVariable Long productId,
			@RequestParam Integer quantity, @RequestParam Double price) {
		SaleDTO updatedSale = saleService.updateSaleItem(saleId, productId, quantity, price);
		if (updatedSale == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(updatedSale);
	}
}
