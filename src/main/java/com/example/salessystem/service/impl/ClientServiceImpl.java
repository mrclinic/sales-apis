package com.example.salessystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.salessystem.dto.ClientDTO;
import com.example.salessystem.entity.Client;
import com.example.salessystem.repository.ClientRepository;
import com.example.salessystem.service.ClientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	@Override
	public List<ClientDTO> getAllClients() {
		return clientRepository.findAll().stream().map(c -> ClientDTO.builder().id(c.getId())
				.firstName(c.getFirstName()).lastName(c.getLastName()).mobile(c.getMobile()).build())
				.collect(Collectors.toList());
	}

	@Override
	public ClientDTO createClient(ClientDTO clientDTO) {
		Client client = Client.builder().firstName(clientDTO.getFirstName()).lastName(clientDTO.getLastName())
				.mobile(clientDTO.getMobile()).build();
		Client saved = clientRepository.save(client);
		clientDTO.setId(saved.getId());
		return clientDTO;
	}

	@Override
	public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
		return clientRepository.findById(id).map(client -> {
			client.setFirstName(clientDTO.getFirstName());
			client.setLastName(clientDTO.getLastName());
			client.setMobile(clientDTO.getMobile());
			clientRepository.save(client);
			return clientDTO;
		}).orElse(null);
	}

}
