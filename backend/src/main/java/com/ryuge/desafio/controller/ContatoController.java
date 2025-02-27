package com.ryuge.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryuge.desafio.dto.ContatoDTO;
import com.ryuge.desafio.model.Contato;
import com.ryuge.desafio.service.ContatoService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/contatos")
public class ContatoController {

    @Autowired
    private ContatoService service;

    @GetMapping
    public ResponseEntity<List<Contato>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contato> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Contato> salvar(@RequestBody ContatoDTO contatoDTO) {
        Contato novoContato = new Contato();
        novoContato.setNome(contatoDTO.getNome());
        novoContato.setEmail(contatoDTO.getEmail());
        novoContato.setCelular(contatoDTO.getCelular());
        novoContato.setTelefone(contatoDTO.getTelefone());
        novoContato.setFavorito(contatoDTO.getFavorito());
        novoContato.setAtivo(true); // Contatos são ativos por padrão

        return ResponseEntity.ok(service.salvar(novoContato));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contato> atualizar(@PathVariable Long id, @RequestBody ContatoDTO contatoDTO) {
        return service.buscarPorId(id)
                .map(contato -> {
                    contato.setNome(contatoDTO.getNome());
                    contato.setEmail(contatoDTO.getEmail());
                    contato.setCelular(contatoDTO.getCelular());
                    contato.setTelefone(contatoDTO.getTelefone());
                    contato.setFavorito(contatoDTO.getFavorito());
                    return ResponseEntity.ok(service.salvar(contato));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
