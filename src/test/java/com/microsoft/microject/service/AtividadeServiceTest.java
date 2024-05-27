package com.microsoft.microject.service;

import com.microsoft.microject.domain.Atividade;
import com.microsoft.microject.domain.AtividadeStatus;
import com.microsoft.microject.domain.Projeto;
import com.microsoft.microject.exception.BadRequestException;
import com.microsoft.microject.exception.ResourceNotFoundException;
import com.microsoft.microject.repository.AtividadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtividadeServiceTest {

    @Mock
    private AtividadeRepository atividadeRepository;

    @InjectMocks
    private AtividadeService atividadeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void adicionarAtividade_comDescricaoValida_deveSalvarAtividade() {
        Atividade atividade = new Atividade();
        atividade.setDescricao("Nova Atividade");
        atividade.setProjeto(new Projeto());
        atividade.setStatus(AtividadeStatus.BACKLOG);

        when(atividadeRepository.save(atividade)).thenReturn(atividade);

        Atividade resultado = atividadeService.adicionarAtividade(atividade);

        assertNotNull(resultado);
        assertEquals("Nova Atividade", resultado.getDescricao());
        verify(atividadeRepository, times(1)).save(atividade);
    }

    @Test
    void adicionarAtividade_comDescricaoVazia_deveLancarBadRequestException() {
        Atividade atividade = new Atividade();
        atividade.setDescricao("");

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            atividadeService.adicionarAtividade(atividade);
        });

        assertEquals("Projeto, Descricao e Status sao obrigatorios.", exception.getMessage());
        verify(atividadeRepository, never()).save(atividade);
    }

    @Test
    void encontrarAtividadePorId_comIdValido_deveRetornarAtividade() {
        Atividade atividade = new Atividade();
        atividade.setId(1L);
        atividade.setDescricao("Atividade Existente");

        when(atividadeRepository.findById(1L)).thenReturn(Optional.of(atividade));

        Optional<Atividade> resultado = atividadeService.encontrarAtividadePorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.isPresent() ? resultado.get().getId() : null);
        assertEquals("Atividade Existente", resultado.isPresent() ? resultado.get().getDescricao() : null);
        verify(atividadeRepository, times(1)).findById(1L);
    }

    @Test
    void removerAtividade_comIdValido_deveRemoverAtividade() {
        when(atividadeRepository.existsById(1L)).thenReturn(true);

        atividadeService.removerAtividade(1L);

        verify(atividadeRepository, times(1)).existsById(1L);
        verify(atividadeRepository, times(1)).deleteById(1L);
    }

    @Test
    void removerAtividade_comIdInvalido_deveLancarResourceNotFoundException() {
        when(atividadeRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            atividadeService.removerAtividade(1L);
        });

        assertEquals("Atividade não encontrada com ID: 1", exception.getMessage());
        verify(atividadeRepository, times(1)).existsById(1L);
        verify(atividadeRepository, never()).deleteById(1L);
    }

    @Test
    void atualizarAtividade_comIdValido_deveAtualizarAtividade() {
        Atividade atividade = new Atividade();
        atividade.setId(1L);
        atividade.setDescricao("Atividade Atualizada");

        when(atividadeRepository.existsById(atividade.getId())).thenReturn(true);
        when(atividadeRepository.save(atividade)).thenReturn(atividade);

        Atividade resultado = atividadeService.atualizarAtividade(atividade);

        assertNotNull(resultado);
        assertEquals("Atividade Atualizada", resultado.getDescricao());
        verify(atividadeRepository, times(1)).existsById(atividade.getId());
        verify(atividadeRepository, times(1)).save(atividade);
    }

    @Test
    void atualizarAtividade_comIdInvalido_deveLancarResourceNotFoundException() {
        Atividade atividade = new Atividade();
        atividade.setId(1L);

        when(atividadeRepository.existsById(atividade.getId())).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            atividadeService.atualizarAtividade(atividade);
        });

        assertEquals("Atividade não encontrada com ID: 1", exception.getMessage());
        verify(atividadeRepository, times(1)).existsById(atividade.getId());
        verify(atividadeRepository, never()).save(atividade);
    }
}
