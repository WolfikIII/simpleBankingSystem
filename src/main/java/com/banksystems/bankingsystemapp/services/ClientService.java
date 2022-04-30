package com.banksystems.bankingsystemapp.services;

import com.banksystems.bankingsystemapp.models.Client;

public interface ClientService {

    Client addNewClient(Client client);

    void deleteClientById(Long clientId);

    Client getClientById(Long clientId);
}
