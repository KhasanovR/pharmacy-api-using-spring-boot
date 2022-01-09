package com.example.pharmacy.cashier.controller;

import com.example.pharmacy.account.model.AppUser;
import com.example.pharmacy.account.service.AppUserService;
import com.example.pharmacy.cashier.model.Client;
import com.example.pharmacy.cashier.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/clients")
@Slf4j
public class ClientController {

    private final ClientService clientService;
    private final AppUserService appUserService;

    @Autowired
    public ClientController(ClientService ClientService, AppUserService appUserService) {
        this.clientService = ClientService;
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findClients() {
    Collection<Client> clients = this.clientService.getClients();
        log.info("listing clients: {}", clients);
        return ResponseEntity.ok().body(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findClient(@PathVariable("id") Long id) {
        Client client = this.clientService.findClientById(id);
        log.info("listing client: {}", client);
        return ResponseEntity.ok().body(client);
    }

    @PostMapping("/add")
    public ResponseEntity<?> registerClient(@RequestBody Client client, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("adding client: {}", client);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Client savedClient = this.clientService.saveClient(client, user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/clients/").toUriString()
                + savedClient.getId());
        return ResponseEntity.created(uri).body(savedClient);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateClient(@RequestBody Client client, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("updating client: {}", client);
        String username = userDetails.getUsername();
        AppUser user = appUserService.getUser(username);
        Client updatedClient = this.clientService.updateClient(client, user);
        return ResponseEntity.ok().body(updatedClient);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeClient(@PathVariable("id") Long id){
        log.info("deleting client with id: {}", id);
        this.clientService.deleteClientById(id);
        return ResponseEntity.ok().build();
    }
}