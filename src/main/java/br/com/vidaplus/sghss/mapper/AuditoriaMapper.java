package br.com.vidaplus.sghss.mapper;

import br.com.vidaplus.sghss.dto.response.AuditoriaResponseDTO;
import br.com.vidaplus.sghss.model.Auditoria;

public class AuditoriaMapper {

    public static AuditoriaResponseDTO toDTO(Auditoria auditoria) {
        String rawAcao = auditoria.getAcao(); // Ex: "PacienteService.buscarPorId(..)"
        String[] partes = rawAcao.split("\\.");
        String entidade = partes.length > 0 ? partes[0].replace("Service", "") : "";
        String metodo = partes.length > 1 ? partes[1].replace("()", "").replace("..", "") : rawAcao;

        AuditoriaResponseDTO dto = new AuditoriaResponseDTO();
        dto.setId(auditoria.getId());
        dto.setUsuario(auditoria.getUsuario());
        dto.setEntidade(entidade);
        dto.setAcao(metodo);
        dto.setDetalhes(limparDetalhes(auditoria.getDetalhes()));
        dto.setDataHora(auditoria.getDataHora());

        return dto;
    }

    private static String limparDetalhes(String detalhes) {
        if (detalhes == null) return "";
        return detalhes
                .replaceAll("br\\.com\\.vidaplus\\.sghss\\.dto\\.[^@]+@", "")
                .replaceAll("string", "")
                .replace("Args: ", "")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
