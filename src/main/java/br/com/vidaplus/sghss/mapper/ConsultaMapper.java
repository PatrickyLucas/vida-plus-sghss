package br.com.vidaplus.sghss.mapper;

import br.com.vidaplus.sghss.dto.request.ConsultaRequestDTO;
import br.com.vidaplus.sghss.dto.response.ConsultaResponseDTO;
import br.com.vidaplus.sghss.model.Consulta;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import org.springframework.stereotype.Component;

/**
 * Mapper para converter entre DTOs de consulta e entidades de consulta.
 * Utilizado para transformar dados entre a camada de apresentação e a camada de persistência.
 *
 * @author Patricky Lucas
 */
@Component
public class ConsultaMapper {

    /**
     * Converte uma entidade Consulta para um DTO de resposta ConsultaResponseDTO.
     *
     * @param consulta a entidade Consulta a ser convertida
     * @return um DTO de resposta contendo os dados da consulta
     */
    public ConsultaResponseDTO toResponseDTO(Consulta consulta) {
        return new ConsultaResponseDTO(
                consulta.getId(),
                consulta.getStatus(),
                consulta.getData(),
                consulta.getPaciente().getId(),
                consulta.getPaciente().getNome(),
                consulta.getProfissional().getId(),
                consulta.getProfissional().getNome()
        );
    }

    /**
     * Converte um DTO de requisição ConsultaRequestDTO para uma entidade Consulta.
     *
     * @param dto         o DTO de requisição contendo os dados da consulta
     * @param paciente    o paciente associado à consulta
     * @param profissional o profissional de saúde associado à consulta
     * @return uma instância de Consulta com os dados do DTO, paciente e profissional
     */
    public Consulta toEntity(ConsultaRequestDTO dto, Paciente paciente, ProfissionalSaude profissional) {
        Consulta consulta = new Consulta();
        consulta.setStatus(dto.getStatus());
        consulta.setData(dto.getData());
        consulta.setPaciente(paciente);
        consulta.setProfissional(profissional);
        return consulta;
    }

    /**
     * Converte um DTO de requisição ConsultaRequestDTO para uma entidade Consulta sem associar paciente e profissional.
     *
     * @param dto o DTO de requisição contendo os dados da consulta
     * @return uma instância de Consulta com os dados do DTO
     */
    public  Consulta toEntity(ConsultaRequestDTO dto) {
        Consulta consulta = new Consulta();
        consulta.setStatus(dto.getStatus());
        consulta.setData(dto.getData());
        return consulta;
    }
}
