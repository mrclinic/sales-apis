package com.example.salessystem.service;

import com.example.salessystem.dto.SaleDTO;
import java.util.List;

public interface SaleService {
	List getAllSales();

	SaleDTO createSale(SaleDTO saleDTO);

	SaleDTO updateSaleItem(Long saleId, Long productId, Integer quantity, Double price);
}