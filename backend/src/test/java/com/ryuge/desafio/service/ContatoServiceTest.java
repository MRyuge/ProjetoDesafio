package com.ryuge.desafio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ryuge.desafio.model.Contato;
import com.ryuge.desafio.repository.ContatoRepository;

class ContatoServiceTest {

    @InjectMocks
    private ContatoService contatoService;

    @Mock
    private ContatoRepository contatoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarContato() {
        Contato contato = Contato.builder()
                .nome("Ryuge")
                .email("ryuge@email.com")
                .celular("99999999999")
                .favorito(true)
                .ativo(true)
                .build();

        when(contatoRepository.save(any(Contato.class))).thenReturn(contato);

        Contato salvo = contatoService.salvar(contato);

        assertNotNull(salvo);
        assertEquals("Ryuge", salvo.getNome());
        assertEquals("ryuge@email.com", salvo.getEmail());
        assertEquals("99999999999", salvo.getCelular());
        assertTrue(salvo.getFavorito());
    }

    @Test
    void deveListarTodosOsContatos() {
        List<Contato> contatos = Arrays.asList(
                new Contato(1L, "Ryuge", "ryuge@email.com", "99999999999", "3132323232", true, true, null),
                new Contato(2L, "João", "joao@email.com", "98888888888", "3145454545", false, true, null)
        );

        when(contatoRepository.findAll()).thenReturn(contatos);

        List<Contato> resultado = contatoService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Ryuge", resultado.get(0).getNome());
        assertEquals("João", resultado.get(1).getNome());
    }

    @Test
    void deveBuscarContatoPorIdExistente() {
        Contato contato = Contato.builder()
                .id(1L)
                .nome("Ryuge")
                .email("ryuge@email.com")
                .celular("99999999999")
                .build();

        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));

        Optional<Contato> resultado = contatoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Ryuge", resultado.get().getNome());
    }

    @Test
    void deveRetornarVazioAoBuscarPorIdInexistente() {
        when(contatoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Contato> resultado = contatoService.buscarPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void deveAtualizarContatoExistente() {
        Contato contatoOriginal = Contato.builder()
                .id(1L)
                .nome("Ryuge")
                .email("ryuge@email.com")
                .celular("99999999999")
                .build();

        Contato contatoAtualizado = Contato.builder()
                .id(1L)
                .nome("Ryuge Atualizado")
                .email("novo@email.com")
                .celular("99999999999")
                .build();

        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contatoOriginal));
        when(contatoRepository.save(any(Contato.class))).thenReturn(contatoAtualizado);

        Contato resultado = contatoService.atualizar(1L, contatoAtualizado);

        assertNotNull(resultado);
        assertEquals("Ryuge Atualizado", resultado.getNome());
        assertEquals("novo@email.com", resultado.getEmail());
    }

    @Test
    void deveLancarExcecaoAoTentarAtualizarContatoInexistente() {
        Contato contatoAtualizado = Contato.builder()
                .nome("Novo Nome")
                .email("novo@email.com")
                .celular("99999999999")
                .build();

        when(contatoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatoService.atualizar(99L, contatoAtualizado);
        });

        assertEquals("Contato não encontrado", exception.getMessage());
    }

    @Test
    void deveExcluirContatoExistente() {
        Long idContato = 1L;

        when(contatoRepository.existsById(idContato)).thenReturn(true);
        doNothing().when(contatoRepository).deleteById(idContato);

        contatoService.excluir(idContato);

        verify(contatoRepository, times(1)).deleteById(idContato);
    }

    @Test
    void deveRetornarContatoPorCelular() {
        Contato contato = Contato.builder()
                .id(1L)
                .nome("Ryuge")
                .email("ryuge@email.com")
                .celular("99999999999")
                .build();

        when(contatoRepository.findByCelular("99999999999")).thenReturn(Optional.of(contato));

        Optional<Contato> resultado = contatoService.buscarPorCelular("99999999999");

        assertTrue(resultado.isPresent());
        assertEquals("Ryuge", resultado.get().getNome());
    }

    @Test
    void deveRetornarVazioQuandoContatoNaoExistePorCelular() {
        when(contatoRepository.findByCelular("00000000000")).thenReturn(Optional.empty());

        Optional<Contato> resultado = contatoService.buscarPorCelular("00000000000");

        assertFalse(resultado.isPresent());
    }
}
