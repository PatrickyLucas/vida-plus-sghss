package br.com.vidaplus.sghss.mapper;

import br.com.vidaplus.sghss.dto.request.ProfissionalSaudeRequestDTO;
import br.com.vidaplus.sghss.dto.response.ProfissionalSaudeResponseDTO;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import org.springframework.stereotype.Component;

/**
 * Mapper para converter entre DTOs de profissional de saúde e entidades de profissional de saúde.
 * Utilizado para transformar dados entre a camada de apresentação e a camada de persistência.
 *
 * @author Patricky Lucas
 */
@Component
public class ProfissionalSaudeMapper {

    /**
     * Converte um DTO de requisição de profissional de saúde para uma entidade ProfissionalSaude.
     *
     * @param dto o DTO de requisição contendo os dados do profissional de saúde
     * @return uma instância de ProfissionalSaude com os dados do DTO
     */
    public ProfissionalSaude toEntity(ProfissionalSaudeRequestDTO dto) {
        ProfissionalSaude profissional = new ProfissionalSaude();
        profissional.setNome(dto.getNome());
        profissional.setEspecialidade(dto.getEspecialidade());
        profissional.setRegistroProfissional(dto.getRegistroProfissional());
        return profissional;
    }

    /**
     * Converte uma entidade ProfissionalSaude para um DTO de resposta de profissional de saúde.
     *
     * @param profissional a entidade ProfissionalSaude a ser convertida
     * @return um DTO de resposta contendo os dados do profissional de saúde
     */
    public ProfissionalSaudeResponseDTO toResponseDTO(ProfissionalSaude profissional) {
        return new ProfissionalSaudeResponseDTO(
                profissional.getId(),
                profissional.getNome(),
                profissional.getEspecialidade(),
                profissional.getRegistroProfissional()
        );
    }
}
