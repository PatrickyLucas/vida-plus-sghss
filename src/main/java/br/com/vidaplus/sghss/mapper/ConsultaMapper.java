package br.com.vidaplus.sghss.mapper;

import br.com.vidaplus.sghss.dto.request.ConsultaRequestDTO;
import br.com.vidaplus.sghss.dto.response.ConsultaResponseDTO;
import br.com.vidaplus.sghss.model.Consulta;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import org.springframework.stereotype.Component;

@Component
public class ConsultaMapper {

    // Converte uma entidade Consulta para um DTO de resposta
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

    // Converte um DTO de requisição, paciente e profissional em uma entidade Consulta
    public Consulta toEntity(ConsultaRequestDTO dto, Paciente paciente, ProfissionalSaude profissional) {
        Consulta consulta = new Consulta();
        consulta.setStatus(dto.getStatus());
        consulta.setData(dto.getData());
        consulta.setPaciente(paciente);
        consulta.setProfissional(profissional);
        return consulta;
    }

    // Converte apenas o DTO de requisição em uma entidade Consulta (sem paciente e profissional)
    public  Consulta toEntity(ConsultaRequestDTO dto) {
        Consulta consulta = new Consulta();
        consulta.setStatus(dto.getStatus());
        consulta.setData(dto.getData());
        return consulta;
    }
}
