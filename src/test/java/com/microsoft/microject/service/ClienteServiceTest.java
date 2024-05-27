package com.microsoft.microject.service;

import com.microsoft.microject.domain.Cliente;
import com.microsoft.microject.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void adicionarCliente_deveSalvarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Novo Cliente");
        cliente.setEmail("cliente@exemplo.com");

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.adicionarCliente(cliente);

        assertNotNull(resultado);
        assertEquals("Novo Cliente", resultado.getNome());
        assertEquals("cliente@exemplo.com", resultado.getEmail());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void listarClientes_deveRetornarListaDeClientes() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("Cliente 1");
        cliente1.setEmail("cliente1@exemplo.com");

        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNome("Cliente 2");
        cliente2.setEmail("cliente2@exemplo.com");

        clientes.add(cliente1);
        clientes.add(cliente2);

        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> resultado = clienteService.listarClientes();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Cliente 1", resultado.get(0).getNome());
        assertEquals("Cliente 2", resultado.get(1).getNome());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void encontrarClientePorId_comIdValido_deveRetornarCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Existente");
        cliente.setEmail("cliente@exemplo.com");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.encontrarClientePorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.isPresent() ? resultado.get().getId() : null);
        assertEquals("Cliente Existente", resultado.isPresent() ? resultado.get().getNome() : null);
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void removerCliente_comIdValido_deveRemoverCliente() {
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.removerCliente(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
    }

}
