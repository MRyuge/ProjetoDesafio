package com.ryuge.desafio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Size(max = 255, message = "O e-mail deve ter no máximo 255 caracteres")
    private String email;

    @NotBlank(message = "O celular é obrigatório")
    @Pattern(regexp = "^[0-9]{11}$", message = "O celular deve conter 11 dígitos numéricos")
    private String celular;

    @Pattern(regexp = "^[0-9]{10}$", message = "O telefone deve conter 10 dígitos numéricos")
    private String telefone;

    private Boolean favorito;
}
