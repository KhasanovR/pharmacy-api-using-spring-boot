package com.example.pharmacy.cashier.service;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.cashier.exception.ClientNotFoundException;
import com.example.pharmacy.cashier.model.Client;
import com.example.pharmacy.cashier.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client saveClient(Client client, AppUser user) {
        Client save = clientRepository.save(client);
        save.setCreatedAt(Instant.now());
        save.setCreatedBy(user);
        save.setLastModifiedAt(save.getCreatedAt());
        save.setLastModifiedBy(save.getCreatedBy());
        return save;
    }

    public Collection<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client findClientById(Long id) {
        return clientRepository
                .findById(id)
                .orElseThrow(
                        () -> new ClientNotFoundException("Client by id " + id + " was not found")
                );
    }

    public Client updateClient(Client client, AppUser user) {
        Client update = clientRepository.save(client);
        update.setLastModifiedAt(Instant.now());
        update.setLastModifiedBy(user);
        return update;
    }

    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }
}
