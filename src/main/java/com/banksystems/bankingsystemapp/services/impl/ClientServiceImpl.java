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
import java.util.Optional;

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
        if (clientRepository.getById(clientId)==null)
            throw new NullPointerException("Client not found");
        else
        clientRepository.deleteById(clientId);
    }

    @Override
    public Optional<Client> getClientById(Long clientId){
        if(clientRepository.findById(clientId)==null)
            throw new NullPointerException("Client not found");
       return clientRepository.findById(clientId);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void basicDataClients(){
        List<Transactions> list = new ArrayList<>();
        clientRepository.save(new Client(1L, "Paweł", "Gaweł", list));
        clientRepository.save(new Client(2L, "Marcin", "Lisek", list));
        clientRepository.save(new Client(3L, "Michał", "Ryba", list));
        clientRepository.save(new Client(4L, "Daniel", "Osioł", list));
    }
}
