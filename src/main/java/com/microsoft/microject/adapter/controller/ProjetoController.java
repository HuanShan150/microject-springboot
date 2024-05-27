package com.microsoft.microject.adapter.controller;

import com.microsoft.microject.domain.Cliente;
import com.microsoft.microject.domain.Projeto;
import com.microsoft.microject.domain.ProjetoStatus;
import com.microsoft.microject.service.ClienteService;
import com.microsoft.microject.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {
    
    private final ProjetoService projetoService;
    private final ClienteService clienteService;

    @Autowired
    public ProjetoController(ProjetoService projetoService, ClienteService clienteService) {
        this.projetoService = projetoService;
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Projeto> adicionarProjeto(@RequestBody Projeto projeto) {
        Optional<Cliente> cliente = clienteService.encontrarClientePorId(projeto.getCliente().getId());
        if (cliente.isPresent()) {
            projeto.setCliente(cliente.get());
            projeto.setStatus(ProjetoStatus.ABERTO); // Set the status to 'ABERTO'
            Projeto novoProjeto = projetoService.adicionarProjeto(projeto);
            return ResponseEntity.ok(novoProjeto);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Projeto>> listarProjetos() {
        List<Projeto> projetos = projetoService.listarProjetos();
        return ResponseEntity.ok(projetos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> encontrarProjetoPorId(@PathVariable Long id) {
        Optional<Projeto> projeto = projetoService.encontrarProjetoPorId(id);
        return projeto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProjeto(@PathVariable Long id) {
        projetoService.removerProjeto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Projeto>> listarProjetosPorCliente(@PathVariable Long clienteId) {
        List<Projeto> projetos = projetoService.listarProjetosPorCliente(clienteId);
        return ResponseEntity.ok(projetos);
    }
}
