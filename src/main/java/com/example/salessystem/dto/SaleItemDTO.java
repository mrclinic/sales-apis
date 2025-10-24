package com.example.salessystem.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItemDTO {
	private Long productId;
	private Integer quantity;
	private Double price;
}