package com.banksystems.bankingsystemapp.repositories;

import com.banksystems.bankingsystemapp.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {


}
