package com.example.salessystem.service;

import com.example.salessystem.dto.ClientDTO;
import java.util.List;

public interface ClientService {
	List getAllClients();

	ClientDTO createClient(ClientDTO clientDTO);

	ClientDTO updateClient(Long id, ClientDTO clientDTO);
}