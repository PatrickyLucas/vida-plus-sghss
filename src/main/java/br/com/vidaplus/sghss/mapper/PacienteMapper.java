package br.com.vidaplus.sghss.mapper;

import br.com.vidaplus.sghss.dto.request.PacienteRequestDTO;
import br.com.vidaplus.sghss.dto.response.PacienteResponseDTO;
import br.com.vidaplus.sghss.model.Paciente;
import org.springframework.stereotype.Component;

/**
 * Mapper para converter entre DTOs de paciente e entidades de paciente.
 * Utilizado para transformar dados entre a camada de apresentação e a camada de persistência.
 */
@Component
public class PacienteMapper {

    /**
     * Converte uma entidade Paciente para um DTO de resposta PacienteResponseDTO.
     *
     * @param paciente a entidade Paciente a ser convertida
     * @return um DTO de resposta contendo os dados do paciente
     */
    public PacienteResponseDTO toResponseDTO(Paciente paciente) {
        PacienteResponseDTO dto = new PacienteResponseDTO();
        dto.setId(paciente.getId());
        dto.setNome(paciente.getNome());
        dto.setCpf(paciente.getCpf());
        dto.setDataNascimento(paciente.getDataNascimento());
        dto.setHistoricoClinico(paciente.getHistoricoClinico());
        if (paciente.getUsuario() != null) {
            dto.setUsername(paciente.getUsuario().getUsername());
        }
        return dto;
    }

    /**
     * Converte um DTO de requisição PacienteRequestDTO para uma entidade Paciente.
     *
     * @param dto o DTO de requisição contendo os dados do paciente
     * @return uma instância de Paciente com os dados do DTO
     */
    public Paciente toEntity(PacienteRequestDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setHistoricoClinico(dto.getHistoricoClinico());
        return paciente;
    }
}
