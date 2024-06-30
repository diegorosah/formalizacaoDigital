package com.example.formalizacaodigital.cartao_credito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.formalizacaodigital.cartao_credito.model.Cliente;
import com.example.formalizacaodigital.cartao_credito.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente getClienteById(String clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        return cliente.orElseThrow(() -> new ResourceNotFoundException("Cliente not found with id: " + clienteId));
    }

    public Cliente updateCliente(String id, Cliente cliente) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente not found with id: " + id);
        }
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    public void deleteCliente(String id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente not found with id: " + id);
        }
        clienteRepository.deleteById(id);
    }

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }
}
