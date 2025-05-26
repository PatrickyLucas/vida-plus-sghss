package br.com.vidaplus.sghss.mapper;

import br.com.vidaplus.sghss.dto.request.ProntuarioRequestDTO;
import br.com.vidaplus.sghss.dto.response.ProntuarioResponseDTO;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.Prontuario;

public class ProntuarioMapper {

    public static Prontuario toEntity(ProntuarioRequestDTO dto, Paciente paciente) {
        Prontuario prontuario = new Prontuario();
        prontuario.setRegistros(dto.getRegistros());
        prontuario.setPaciente(paciente);
        return prontuario;
    }

    public static ProntuarioResponseDTO toResponseDTO(Prontuario prontuario) {
        return new ProntuarioResponseDTO(
                prontuario.getId(),
                prontuario.getRegistros(),
                prontuario.getPaciente().getId(),
                prontuario.getPaciente().getNome()
        );
    }
}
