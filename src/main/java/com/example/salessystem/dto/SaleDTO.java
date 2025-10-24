package com.example.salessystem.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDTO {
	private Long id;
	private LocalDateTime creationDate;
	private Long clientId;
	private String seller;
	private Double total;
	private List<SaleItemDTO> items;
}