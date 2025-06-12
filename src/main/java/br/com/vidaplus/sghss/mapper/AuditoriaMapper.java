package br.com.vidaplus.sghss.mapper;

import br.com.vidaplus.sghss.dto.response.AuditoriaResponseDTO;
import br.com.vidaplus.sghss.model.Auditoria;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AuditoriaMapper {

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


    private static String extrairEntidade(String acao) {
        if (acao == null) return "";
        String[] partes = acao.split("\\.");
        if (partes.length > 0) {
            return partes[0].replace("Service", "").replace("Controller", "").trim();
        }
        return acao;
    }

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

    private static String formatarUsuario(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) return "ANÔNIMO";
        if (usuario.equalsIgnoreCase("anonymousUser")) return "ANÔNIMO";
        return capitalize(usuario);
    }

    private static String capitalize(String texto) {
        if (texto.length() <= 1) return texto.toUpperCase();
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

}
