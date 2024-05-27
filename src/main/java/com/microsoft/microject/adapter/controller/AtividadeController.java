package com.microsoft.microject.adapter.controller;

import com.microsoft.microject.domain.Atividade;
import com.microsoft.microject.service.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    private final AtividadeService atividadeService;

    @Autowired
    public AtividadeController(AtividadeService atividadeService) {
        this.atividadeService = atividadeService;
    }

    @PostMapping
    public ResponseEntity<Atividade> adicionarAtividade(@RequestBody Atividade atividade) {
        Atividade novaAtividade = atividadeService.adicionarAtividade(atividade);
        return ResponseEntity.ok(novaAtividade);
    }

    @GetMapping
    public ResponseEntity<List<Atividade>> listarAtividades() {
        List<Atividade> atividades = atividadeService.listarAtividades();
        return ResponseEntity.ok(atividades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atividade> encontrarAtividadePorId(@PathVariable Long id) {
        Optional<Atividade> atividade = atividadeService.encontrarAtividadePorId(id);
        return atividade.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAtividade(@PathVariable Long id) {
        atividadeService.removerAtividade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<Atividade>> listarAtividadesPorProjeto(@PathVariable Long projetoId) {
        List<Atividade> atividades = atividadeService.listarAtividadesPorProjeto(projetoId);
        return ResponseEntity.ok(atividades);
    }

    @PutMapping("editar/{id}")
    public ResponseEntity<Atividade> atualizarAtividade(@PathVariable Long id, @RequestBody Atividade atividadeAtualizada) {
        Optional<Atividade> atividadeOptional = atividadeService.encontrarAtividadePorId(id);
        if (atividadeOptional.isPresent()) {
            Atividade atividade = atividadeOptional.get();
            atividade.setStatus(atividadeAtualizada.getStatus());
            // Atualize outras propriedades se necessário
            Atividade atividadeAtualizadaResult = atividadeService.atualizarAtividade(atividade);
            return ResponseEntity.ok(atividadeAtualizadaResult);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
