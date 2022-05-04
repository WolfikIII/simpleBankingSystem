package com.banksystems.bankingsystemapp.services;

import com.banksystems.bankingsystemapp.models.Client;

import java.util.Optional;

public interface ClientService {

    Client addNewClient(Client client);

    void deleteClientById(Long clientId);

    Optional<Client> getClientById(Long clientId);
}
