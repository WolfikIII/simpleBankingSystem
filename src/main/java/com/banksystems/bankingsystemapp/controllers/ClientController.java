package com.banksystems.bankingsystemapp.controllers;

import com.banksystems.bankingsystemapp.models.Client;
import com.banksystems.bankingsystemapp.services.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientServiceImpl clientService;

    @Autowired
    public ClientController(ClientServiceImpl clientService){
        this.clientService = clientService;
    }

    @PostMapping("/")
    public ResponseEntity<Client> addNewClient(@RequestBody Client client){
        return new ResponseEntity<>(clientService.addNewClient(client), HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    public void deleteClient(@RequestParam Long clientId){
        clientService.deleteClientById(clientId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@RequestParam Long clientId){
        return new ResponseEntity<> (clientService.getClientById(clientId), HttpStatus.OK);
    }
}
