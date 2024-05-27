package com.microsoft.microject.service;

import com.microsoft.microject.domain.Projeto;
import com.microsoft.microject.repository.ProjetoRepository;
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

class ProjetoServiceTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @InjectMocks
    private ProjetoService projetoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void adicionarProjeto_deveSalvarProjeto() {
        Projeto projeto = new Projeto();
        projeto.setNome("Novo Projeto");

        when(projetoRepository.save(projeto)).thenReturn(projeto);

        Projeto resultado = projetoService.adicionarProjeto(projeto);

        assertNotNull(resultado);
        assertEquals("Novo Projeto", resultado.getNome());
        verify(projetoRepository, times(1)).save(projeto);
    }

    @Test
    void listarProjetos_deveRetornarListaDeProjetos() {
        List<Projeto> projetos = new ArrayList<>();
        Projeto projeto1 = new Projeto();
        projeto1.setId(1L);
        projeto1.setNome("Projeto 1");

        Projeto projeto2 = new Projeto();
        projeto2.setId(2L);
        projeto2.setNome("Projeto 2");

        projetos.add(projeto1);
        projetos.add(projeto2);

        when(projetoRepository.findAll()).thenReturn(projetos);

        List<Projeto> resultado = projetoService.listarProjetos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Projeto 1", resultado.get(0).getNome());
        assertEquals("Projeto 2", resultado.get(1).getNome());
        verify(projetoRepository, times(1)).findAll();
    }

    @Test
    void encontrarProjetoPorId_comIdValido_deveRetornarProjeto() {
        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto Existente");

        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        Optional<Projeto> resultado = projetoService.encontrarProjetoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        assertEquals("Projeto Existente", resultado.get().getNome());
        verify(projetoRepository, times(1)).findById(1L);
    }

    @Test
    void encontrarProjetoPorId_comIdInvalido_deveRetornarEmpty() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Projeto> resultado = projetoService.encontrarProjetoPorId(1L);

        assertFalse(resultado.isPresent());
        verify(projetoRepository, times(1)).findById(1L);
    }

    @Test
    void removerProjeto_comIdValido_deveRemoverProjeto() {
        doNothing().when(projetoRepository).deleteById(1L);

        projetoService.removerProjeto(1L);

        verify(projetoRepository, times(1)).deleteById(1L);
    }

    @Test
    void listarProjetosPorCliente_comClienteIdValido_deveRetornarListaDeProjetos() {
        List<Projeto> projetos = new ArrayList<>();
        Projeto projeto1 = new Projeto();
        projeto1.setId(1L);
        projeto1.setNome("Projeto 1");

        Projeto projeto2 = new Projeto();
        projeto2.setId(2L);
        projeto2.setNome("Projeto 2");

        projetos.add(projeto1);
        projetos.add(projeto2);

        when(projetoRepository.findByClienteId(1L)).thenReturn(projetos);

        List<Projeto> resultado = projetoService.listarProjetosPorCliente(1L);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Projeto 1", resultado.get(0).getNome());
        assertEquals("Projeto 2", resultado.get(1).getNome());
        verify(projetoRepository, times(1)).findByClienteId(1L);
    }
}
