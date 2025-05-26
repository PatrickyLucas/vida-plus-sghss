package br.com.vidaplus.sghss.mapper;

import br.com.vidaplus.sghss.dto.request.ProfissionalSaudeRequestDTO;
import br.com.vidaplus.sghss.dto.response.ProfissionalSaudeResponseDTO;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import org.springframework.stereotype.Component;

@Component
public class ProfissionalSaudeMapper {

    public ProfissionalSaude toEntity(ProfissionalSaudeRequestDTO dto) {
        ProfissionalSaude profissional = new ProfissionalSaude();
        profissional.setNome(dto.getNome());
        profissional.setEspecialidade(dto.getEspecialidade());
        profissional.setRegistroProfissional(dto.getRegistroProfissional());
        return profissional;
    }

    public ProfissionalSaudeResponseDTO toResponseDTO(ProfissionalSaude profissional) {
        return new ProfissionalSaudeResponseDTO(
                profissional.getId(),
                profissional.getNome(),
                profissional.getEspecialidade(),
                profissional.getRegistroProfissional()
        );
    }
}
