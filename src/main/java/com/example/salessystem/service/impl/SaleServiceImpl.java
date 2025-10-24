package com.example.salessystem.service.impl;

import com.example.salessystem.aop.LogUpdate;
import com.example.salessystem.dto.SaleDTO;
import com.example.salessystem.dto.SaleItemDTO;
import com.example.salessystem.entity.Client;
import com.example.salessystem.entity.Product;
import com.example.salessystem.entity.Sale;
import com.example.salessystem.entity.SaleItem;
import com.example.salessystem.repository.ClientRepository;
import com.example.salessystem.repository.ProductRepository;
import com.example.salessystem.repository.SaleRepository;
import com.example.salessystem.service.SaleService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class SaleServiceImpl implements SaleService {

	private final SaleRepository saleRepository;
	private final ClientRepository clientRepository;
	private final ProductRepository productRepository;

	@Override
	public List<SaleDTO> getAllSales() {
		return saleRepository.findAll().stream()
				.map(sale -> SaleDTO.builder().id(sale.getId()).creationDate(sale.getCreationDate())
						.clientId(sale.getClient().getId()).seller(sale.getSeller()).total(sale.getTotal())
						.items(sale.getItems().stream()
								.map(item -> SaleItemDTO.builder().productId(item.getProduct().getId())
										.quantity(item.getQuantity()).price(item.getPrice()).build())
								.collect(Collectors.toList()))
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public SaleDTO createSale(SaleDTO saleDTO) {
		Client client = clientRepository.findById(saleDTO.getClientId()).orElseThrow();
		Sale sale = Sale.builder().client(client).seller(saleDTO.getSeller()).creationDate(LocalDateTime.now()).build();

		List<SaleItem> items = saleDTO.getItems().stream().map(itemDTO -> {
			Product product = productRepository.findById(itemDTO.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found"));
			return SaleItem.builder().sale(sale).product(product).quantity(itemDTO.getQuantity())
					.price(itemDTO.getPrice()).build();
		}).collect(Collectors.toList());

		sale.setItems(items);
		double total = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
		sale.setTotal(total);

		Sale saved = saleRepository.save(sale);
		saleDTO.setId(saved.getId());
		saleDTO.setCreationDate(saved.getCreationDate());
		return saleDTO;
	}

	@Override
	@Transactional
	@LogUpdate
	public SaleDTO updateSaleItem(Long saleId, Long productId, Integer quantity, Double price) {
		Sale sale = saleRepository.findById(saleId).orElse(null);
		if (sale == null)
			return null;

		SaleItem item = sale.getItems().stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst()
				.orElse(null);
		if (item == null)
			return null;

		// Update the sale item
		item.setQuantity(quantity);
		item.setPrice(price);

		// Recalculate total
		double total = sale.getItems().stream().mapToDouble(i -> i.getQuantity() * i.getPrice()).sum();
		sale.setTotal(total);

		saleRepository.save(sale);

		// Return updated SaleDTO
		return SaleDTO.builder().id(sale.getId()).creationDate(sale.getCreationDate())
				.clientId(sale.getClient().getId()).seller(sale.getSeller()).total(sale.getTotal()).items(
						sale.getItems().stream()
								.map(i -> SaleItemDTO.builder().productId(i.getProduct().getId())
										.quantity(i.getQuantity()).price(i.getPrice()).build())
								.collect(Collectors.toList()))
				.build();
	}

}
