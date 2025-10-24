package com.example.salessystem.dto;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
	private Long id;
	private String name;
	private String description;
	private String category;
	private LocalDateTime creationDate;
}