package com.ryuge.desafio.exceptions;

public class ContatoNaoEncontradoException extends RuntimeException {
    public ContatoNaoEncontradoException(Long id) {
        super("Contato não encontrado com o ID: " + id);
    }
}
