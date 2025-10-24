package com.example.salessystem.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String mobile;
}