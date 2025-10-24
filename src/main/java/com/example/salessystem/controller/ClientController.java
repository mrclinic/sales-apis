package com.example.salessystem.controller;

import com.example.salessystem.dto.ClientDTO;
import com.example.salessystem.service.ClientService;
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
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allows requests from frontend clients
@Tag(name = "Clients", description = "API for managing clients")
public class ClientController {

	private final ClientService clientService;

	@Operation(summary = "Fetch all clients", description = "Retrieve all clients from the system.")
	@ApiResponse(responseCode = "200", description = "Clients fetched successfully")
	@GetMapping
	public ResponseEntity<List<ClientDTO>> getAllClients() {
		List<ClientDTO> clients = clientService.getAllClients();
		return ResponseEntity.ok(clients);
	}

	@Operation(summary = "Create a new client", description = "Add a new client.")
	@ApiResponse(responseCode = "201", description = "Client created successfully")
	@PostMapping
	public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
		ClientDTO createdClient = clientService.createClient(clientDTO);
		return ResponseEntity.created(URI.create("/api/clients/" + createdClient.getId())).body(createdClient);
	}

	@Operation(summary = "Update a client", description = "Update an existing client by ID.")
	@ApiResponse(responseCode = "200", description = "Client updated successfully")
	@ApiResponse(responseCode = "404", description = "Client not found")
	@PutMapping("/{id}")
	public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDTO clientDTO) {
		ClientDTO updatedClient = clientService.updateClient(id, clientDTO);
		if (updatedClient == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(updatedClient);
	}
}
