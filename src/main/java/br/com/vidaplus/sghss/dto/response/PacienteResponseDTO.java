package br.com.vidaplus.sghss.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) para representar a resposta de um paciente.
 * Contém informações básicas como ID, nome, CPF, data de nascimento,
 * histórico clínico e nome de usuário.
 */
@Getter
@Setter
public class PacienteResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String historicoClinico;
    private String username;
}
