package com.banksystems.bankingsystemapp.services.impl;

import com.banksystems.bankingsystemapp.models.Client;
import com.banksystems.bankingsystemapp.models.Transactions;
import com.banksystems.bankingsystemapp.repositories.ClientRepository;
import com.banksystems.bankingsystemapp.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public Client addNewClient(Client client){
        return clientRepository.save(client);
    }

    @Override
    public void deleteClientById(Long clientId){
        clientRepository.deleteById(clientId);
    }

    @Override
    public Client getClientById(Long clientId){
       return clientRepository.getById(clientId);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void basicDataClients(){
        List<Transactions> list = new ArrayList<>();
        clientRepository.save(new Client(1L, "Paweł", "Gaweł", list));
        clientRepository.save(new Client(2L, "Marcin", "Lisek", list));
        clientRepository.save(new Client(1L, "Michał", "Ryba", list));
        clientRepository.save(new Client(1L, "Daniel", "Osioł", list));
    }
}
