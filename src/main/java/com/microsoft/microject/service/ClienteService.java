package com.microsoft.microject.service;

import com.microsoft.microject.domain.Cliente;
import com.microsoft.microject.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente adicionarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> encontrarClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    public void removerCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}