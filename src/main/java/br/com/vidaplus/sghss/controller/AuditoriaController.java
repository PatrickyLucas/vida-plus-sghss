package br.com.vidaplus.sghss.controller;

import br.com.vidaplus.sghss.dto.response.AuditoriaResponseDTO;
import br.com.vidaplus.sghss.exception.UsuarioSemPermissaoException;
import br.com.vidaplus.sghss.mapper.AuditoriaMapper;
import br.com.vidaplus.sghss.model.Auditoria;
import br.com.vidaplus.sghss.repository.AuditoriaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaController {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaController(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AuditoriaResponseDTO> listar() {
        if (!temPermissaoAdmin()) {
            throw new UsuarioSemPermissaoException("Usuário sem permissão para acessar auditoria.");
        }
        return auditoriaRepository.findAll()
                .stream()
                .map(AuditoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    //metodo para buscar auditorias por nome de usuario
    @GetMapping("/usuario/{usuario}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AuditoriaResponseDTO> buscarPorUsuario(@PathVariable String usuario) {
        if (!temPermissaoAdmin()) {
            throw new UsuarioSemPermissaoException("Usuário sem permissão para acessar auditoria.");
        }
        return auditoriaRepository.findByUsuario(usuario)
                .stream()
                .map(AuditoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    private boolean temPermissaoAdmin() {
        // Implemente a lógica para verificar se o usuário é ADMIN
        // Exemplo:
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
