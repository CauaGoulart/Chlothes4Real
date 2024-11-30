package com.clothes4happines.controller;

import com.clothes4happines.models.Equipamento;
import com.clothes4happines.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    // Listar todos os equipamentos
    @GetMapping
    public List<Equipamento> getAllEquipamentos() {
        return equipamentoRepository.findAll();
    }

    // Obter equipamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Equipamento> getEquipamentoById(@PathVariable Long id) {
        Optional<Equipamento> equipamento = equipamentoRepository.findById(id);
        return equipamento.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Cadastrar um novo equipamento
    @PostMapping
    public ResponseEntity<Equipamento> createEquipamento(@RequestBody Equipamento equipamento) {
        if (equipamento.getNome() == null || equipamento.getNome().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Equipamento savedEquipamento = equipamentoRepository.save(equipamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEquipamento);
    }

    // Atualizar um equipamento existente
    @PutMapping("/{id}")
    public ResponseEntity<Equipamento> updateEquipamento(@PathVariable Long id, @RequestBody Equipamento equipamentoDetails) {
        Optional<Equipamento> optionalEquipamento = equipamentoRepository.findById(id);
        if (optionalEquipamento.isPresent()) {
            Equipamento existingEquipamento = optionalEquipamento.get();
            existingEquipamento.setNome(equipamentoDetails.getNome());
            Equipamento updatedEquipamento = equipamentoRepository.save(existingEquipamento);
            return ResponseEntity.ok(updatedEquipamento);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Deletar um equipamento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipamento(@PathVariable Long id) {
        if (equipamentoRepository.existsById(id)) {
            equipamentoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

