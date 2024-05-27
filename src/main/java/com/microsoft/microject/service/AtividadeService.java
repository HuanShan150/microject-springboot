package com.microsoft.microject.service;

import com.microsoft.microject.domain.Atividade;
import com.microsoft.microject.exception.BadRequestException;
import com.microsoft.microject.exception.ResourceNotFoundException;
import com.microsoft.microject.repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;

    @Autowired
    public AtividadeService(AtividadeRepository atividadeRepository) {
        this.atividadeRepository = atividadeRepository;
    }

    public Atividade adicionarAtividade(Atividade atividade) {
        if (atividade.getDescricao() == null || atividade.getDescricao().trim().isEmpty() || atividade.getProjeto() == null || atividade.getStatus() == null || atividade.getStatus() == null) {
            throw new BadRequestException("Projeto, Descricao e Status sao obrigatorios.");
        }
        return atividadeRepository.save(atividade);
    }

    public List<Atividade> listarAtividades() {
        return atividadeRepository.findAll();
    }

    public Optional<Atividade> encontrarAtividadePorId(Long id) {
        return atividadeRepository.findById(id);
    }

    public void removerAtividade(Long id) {
        if (!atividadeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Atividade não encontrada com ID: " + id);
        }
        atividadeRepository.deleteById(id);
    }

    public List<Atividade> listarAtividadesPorProjeto(Long projetoId) {
        return atividadeRepository.findByProjetoId(projetoId);
    }

    public Atividade atualizarAtividade(Atividade atividade) {
        if (!atividadeRepository.existsById(atividade.getId())) {
            throw new ResourceNotFoundException("Atividade não encontrada com ID: " + atividade.getId());
        }
        return atividadeRepository.save(atividade);
    }
}
