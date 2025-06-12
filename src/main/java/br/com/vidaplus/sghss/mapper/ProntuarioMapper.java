package br.com.vidaplus.sghss.mapper;

import br.com.vidaplus.sghss.dto.request.ProntuarioRequestDTO;
import br.com.vidaplus.sghss.dto.response.ProntuarioResponseDTO;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.Prontuario;
import org.springframework.stereotype.Component;

/**
 * Mapper para converter entre DTOs de prontuário e entidades de prontuário.
 * Utilizado para transformar dados entre a camada de apresentação e a camada de persistência.
 *
 * @author Patricky Lucas
 */
@Component
public class ProntuarioMapper {

    /**
     * Converte um DTO de requisição de prontuário para uma entidade Prontuario.
     *
     * @param dto      o DTO de requisição contendo os dados do prontuário
     * @param paciente o paciente associado ao prontuário
     * @return uma instância de Prontuario com os dados do DTO e do paciente
     */
    public Prontuario toEntity(ProntuarioRequestDTO dto, Paciente paciente) {
        Prontuario prontuario = new Prontuario();
        prontuario.setRegistros(dto.getRegistros());
        prontuario.setPaciente(paciente);
        return prontuario;
    }

    /**
     * Converte uma entidade Prontuario para um DTO de resposta de prontuário.
     *
     * @param prontuario a entidade Prontuario a ser convertida
     * @return um DTO de resposta contendo os dados do prontuário
     */
    public ProntuarioResponseDTO toResponseDTO(Prontuario prontuario) {
        return new ProntuarioResponseDTO(
                prontuario.getId(),
                prontuario.getRegistros(),
                prontuario.getPaciente().getId(),
                prontuario.getPaciente().getNome()
        );
    }
}
