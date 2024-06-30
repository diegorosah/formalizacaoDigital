package com.example.formalizacaodigital.cartao_credito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.formalizacaodigital.cartao_credito.model.Cliente;
import com.example.formalizacaodigital.cartao_credito.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    // Adicionar cliente
    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente newCliente = clienteService.createCliente(cliente);
        return new ResponseEntity<>(newCliente, HttpStatus.CREATED);
    }

    // Buscar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable String id) {
        Cliente cliente = clienteService.getClienteById(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    // Atualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable String id, @RequestBody Cliente cliente) {
        Cliente updatedCliente = clienteService.updateCliente(id, cliente);
        return new ResponseEntity<>(updatedCliente, HttpStatus.OK);
    }

    // Remover cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable String id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Retornar todos os clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getAllClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
}
