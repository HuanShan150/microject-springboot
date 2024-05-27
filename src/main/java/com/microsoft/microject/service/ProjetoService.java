package com.microsoft.microject.service;

import com.microsoft.microject.domain.Projeto;
import com.microsoft.microject.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {
    
    private final ProjetoRepository projetoRepository;

    @Autowired
    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public Projeto adicionarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public List<Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }

    public Optional<Projeto> encontrarProjetoPorId(Long id) {
        return projetoRepository.findById(id);
    }

    public void removerProjeto(Long id) {
        projetoRepository.deleteById(id);
    }

    public List<Projeto> listarProjetosPorCliente(Long clienteId) {
        return projetoRepository.findByClienteId(clienteId);
    }
}
