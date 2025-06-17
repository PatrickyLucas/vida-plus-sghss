package br.com.vidaplus.sghss.controller;

import br.com.vidaplus.sghss.dto.response.AuditoriaResponseDTO;
import br.com.vidaplus.sghss.exception.UsuarioSemPermissaoException;
import br.com.vidaplus.sghss.mapper.AuditoriaMapper;
import br.com.vidaplus.sghss.repository.AuditoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para operações relacionadas a Auditoria.
 * Disponibiliza endpoints para listar e buscar auditorias por usuário.
 *
 * @author Patricky Lucas
 */
@RestController
@RequestMapping("/api/auditoria")
@Tag(name = "Auditoria", description = "Operações relacionadas a Auditoria")
public class AuditoriaController {

    /**
     * Repositório de Auditoria utilizado para as operações de acesso aos dados.
     */
    private final AuditoriaRepository auditoriaRepository;

    /**
     * Construtor do AuditoriaController.
     *
     * @param auditoriaRepository repositório de auditoria
     */
    public AuditoriaController(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    /**
     * Lista todas as auditorias.
     *
     * @return lista de AuditoriaResponseDTO
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Listar Auditorias",
            description = "Lista todas as auditorias registradas no sistema. " +
                    "Apenas usuários com permissão ADMIN podem acessar este endpoint."
    )
    public List<AuditoriaResponseDTO> listar() {
        if (!temPermissaoAdmin()) {
            throw new UsuarioSemPermissaoException("Usuário sem permissão para acessar auditoria.");
        }
        return auditoriaRepository.findAll()
                .stream()
                .map(AuditoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca auditorias por usuário.
     *
     * @param usuario nome do usuário
     * @return lista de AuditoriaResponseDTO
     */
    @GetMapping("/usuario/{usuario}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Buscar Auditorias por Usuário",
            description = "Busca todas as auditorias registradas para um usuário específico. " +
                    "Apenas usuários com permissão ADMIN podem acessar este endpoint."
    )
    public List<AuditoriaResponseDTO> buscarPorUsuario(@PathVariable String usuario) {
        if (!temPermissaoAdmin()) {
            throw new UsuarioSemPermissaoException("Usuário sem permissão para acessar auditoria.");
        }
        return auditoriaRepository.findByUsuario(usuario)
                .stream()
                .map(AuditoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Verifica se o usuário autenticado tem permissão de ADMIN.
     *
     * @return true se o usuário for ADMIN, false caso contrário
     */
    private boolean temPermissaoAdmin() {
        // Implemente a lógica para verificar se o usuário é ADMIN
        // Exemplo:
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
