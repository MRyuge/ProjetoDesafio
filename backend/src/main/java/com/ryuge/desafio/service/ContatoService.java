package com.ryuge.desafio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ryuge.desafio.exceptions.ContatoNaoEncontradoException;
import com.ryuge.desafio.model.Contato;
import com.ryuge.desafio.repository.ContatoRepository;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository repository;

    public List<Contato> listarTodos() {
        return repository.findAll();
    }

    public Contato salvar(Contato contato) {
        return repository.save(contato);
    }

    public Optional<Contato> buscarPorId(Long id) {  
        return repository.findById(id);
    }

    public Optional<Contato> buscarPorCelular(String celular) {
        return repository.findByCelular(celular);
    }

    public Contato atualizar(Long id, Contato contatoAtualizado) {
        return repository.findById(id)
                .map(contato -> {
                    contato.setNome(contatoAtualizado.getNome());
                    contato.setEmail(contatoAtualizado.getEmail());
                    contato.setCelular(contatoAtualizado.getCelular());
                    contato.setTelefone(contatoAtualizado.getTelefone());
                    contato.setFavorito(contatoAtualizado.getFavorito());
                    contato.setAtivo(contatoAtualizado.getAtivo());
                    return repository.save(contato);
                }).orElseThrow(() -> new ContatoNaoEncontradoException(id));
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new ContatoNaoEncontradoException(id);
        }
        repository.deleteById(id);
    }
}
