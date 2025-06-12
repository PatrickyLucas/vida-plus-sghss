package br.com.vidaplus.sghss.mapper;

import br.com.vidaplus.sghss.dto.response.AuditoriaResponseDTO;
import br.com.vidaplus.sghss.model.Auditoria;

/**
 * Mapper para converter entre Auditoria e AuditoriaResponseDTO.
 * Utilizado para transformar dados de auditoria em um formato adequado para resposta HTTP.
 *
 * @author Patricky Lucas
 */
public class AuditoriaMapper {

    /**
     * Converte uma entidade Auditoria para um DTO de resposta AuditoriaResponseDTO.
     *
     * @param auditoria a entidade Auditoria a ser convertida
     * @return um DTO de resposta contendo os dados da auditoria
     */
    public static AuditoriaResponseDTO toDTO(Auditoria auditoria) {
        String rawAcao = auditoria.getAcao();
        String entidade = extrairEntidade(rawAcao);
        String metodo = extrairMetodo(rawAcao);

        AuditoriaResponseDTO dto = new AuditoriaResponseDTO();
        dto.setId(auditoria.getId());
        dto.setUsuario(formatarUsuario(auditoria.getUsuario()));
        dto.setEntidade(entidade);
        dto.setAcao(metodo);
        dto.setDetalhes(limparDetalhes(auditoria.getDetalhes(), metodo));
        dto.setDataHora(auditoria.getDataHora()); // Aqui é o novo método

        return dto;
    }

    /**
     * Extrai a entidade e o método da ação registrada na auditoria.
     * A ação é esperada no formato "EntidadeService.metodo()".
     *
     * @param acao a ação registrada na auditoria
     * @return um DTO de resposta contendo os dados da auditoria
     */
    private static String extrairEntidade(String acao) {
        if (acao == null) return "";
        String[] partes = acao.split("\\.");
        if (partes.length > 0) {
            return partes[0].replace("Service", "").replace("Controller", "").trim();
        }
        return acao;
    }

    /**
     * Extrai o método da ação registrada na auditoria.
     * A ação é esperada no formato "EntidadeService.metodo()".
     *
     * @param acao a ação registrada na auditoria
     * @return o nome do método extraído da ação
     */
    private static String extrairMetodo(String acao) {
        if (acao == null) return "";
        String[] partes = acao.split("\\.");
        if (partes.length > 1) {
            return partes[1]
                    .replace("()", "")
                    .replace("..", "")
                    .replace("(", "")
                    .trim();
        }
        return acao;
    }

    /**
     * Limpa e formata os detalhes da auditoria, removendo informações sensíveis e normalizando o formato.
     *
     * @param detalhes os detalhes da auditoria a serem limpos
     * @param metodo o nome do método para aplicar regras específicas de limpeza
     * @return uma string formatada com os detalhes limpos
     */
    private static String limparDetalhes(String detalhes, String metodo) {
        if (detalhes == null || detalhes.trim().isEmpty()) return "[]";

        // Remove prefixo 'Args: ' e normaliza espaços
        String argumentos = detalhes
                .replace("Args: ", "")
                .replaceAll("\\s+", " ")
                .trim();

        // Remove pacotes e prefixos desnecessários
        argumentos = argumentos
                .replaceAll("br\\.com\\.vidaplus\\.sghss\\.\\w+\\.", "")
                .replaceAll("(request|response)\\.", "");

        // Remove sufixo @hashcode de objetos
        argumentos = argumentos.replaceAll("(\\w+)@\\w+", "$1");

        // Remove colchetes e separa os argumentos
        String[] args = argumentos
                .replace("[", "")
                .replace("]", "")
                .split("\\s*,\\s*");

        // Métodos que contêm senha em índice fixo
        if (metodo.equalsIgnoreCase("login") && args.length > 1) {
            args[1] = "\"****\""; // Senha em login
        }

        if (metodo.equalsIgnoreCase("criarUsuario") && args.length > 1) {
            args[1] = "\"****\""; // Senha em criarUsuario
        }

        if (metodo.toLowerCase().contains("criar") && metodo.toLowerCase().contains("usuario") && args.length > 1) {
            args[1] = "\"****\""; // Segurança extra para qualquer método de criação de usuário
        }

        return "[" + String.join(", ", args) + "]";
    }

    /**
     * Formata o nome do usuário para exibição.
     * Se o usuário for nulo ou vazio, retorna "ANÔNIMO".
     * Se o usuário for "anonymousUser", também retorna "ANÔNIMO".
     * Caso contrário, capitaliza o nome do usuário.
     *
     * @param usuario o nome do usuário a ser formatado
     * @return o nome do usuário formatado
     */
    private static String formatarUsuario(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) return "ANÔNIMO";
        if (usuario.equalsIgnoreCase("anonymousUser")) return "ANÔNIMO";
        return capitalize(usuario);
    }

    /**
     * Capitaliza a primeira letra de uma string e coloca o restante em minúsculas.
     * Se a string tiver apenas um caractere, retorna em maiúsculo.
     *
     * @param texto a string a ser capitalizada
     * @return a string com a primeira letra em maiúsculo e o restante em minúsculas
     */
    private static String capitalize(String texto) {
        if (texto.length() <= 1) return texto.toUpperCase();
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

}
