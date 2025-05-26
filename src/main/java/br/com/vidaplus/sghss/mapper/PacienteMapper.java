package br.com.vidaplus.sghss.mapper;

import br.com.vidaplus.sghss.dto.request.PacienteRequestDTO;
import br.com.vidaplus.sghss.dto.response.PacienteResponseDTO;
import br.com.vidaplus.sghss.model.Paciente;

public class PacienteMapper {

    public static PacienteResponseDTO toResponseDTO(Paciente paciente) {
        PacienteResponseDTO dto = new PacienteResponseDTO();
        dto.setId(paciente.getId());
        dto.setNome(paciente.getNome());
        dto.setCpf(paciente.getCpf());
        dto.setDataNascimento(paciente.getDataNascimento());
        dto.setHistoricoClinico(paciente.getHistoricoClinico());
        return dto;
    }

    public static Paciente toEntity(PacienteRequestDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setHistoricoClinico(dto.getHistoricoClinico());
        return paciente;
    }
}
