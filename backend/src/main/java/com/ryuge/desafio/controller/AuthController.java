package com.ryuge.desafio.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryuge.desafio.dto.UsuarioDTO;
import com.ryuge.desafio.exceptions.UsuarioJaExisteException;
import com.ryuge.desafio.model.Usuario;
import com.ryuge.desafio.security.JwtUtil;
import com.ryuge.desafio.service.UsuarioService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil; // 🔥 Adicionado para gerar tokens JWT

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setUsername(usuarioDTO.getUsername());
            novoUsuario.setPassword(usuarioDTO.getPassword()); // 🔥 Senha será criptografada no service

            Usuario usuarioSalvo = usuarioService.salvar(novoUsuario);
            return ResponseEntity.ok(usuarioSalvo);
        } catch (UsuarioJaExisteException e) {
            return ResponseEntity.status(400).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) {
        Optional<Usuario> user = usuarioService.buscarPorUsername(usuarioDTO.getUsername());

        if (user.isPresent()) {
            if (passwordEncoder.matches(usuarioDTO.getPassword(), user.get().getPassword())) {
                String token = jwtUtil.gerarToken(user.get().getUsername());
                return ResponseEntity.ok().body("{\"token\":\"" + token + "\"}");
            } else {
                return ResponseEntity.status(401).body("{\"error\":\"Credenciais inválidas\"}");
            }
        } else {
            return ResponseEntity.status(401).body("{\"error\":\"Usuário não encontrado\"}");
        }
    }

}
