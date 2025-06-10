package br.com.vidaplus.sghss.controller;

import br.com.vidaplus.sghss.dto.response.AuditoriaResponseDTO;
import br.com.vidaplus.sghss.mapper.AuditoriaMapper;
import br.com.vidaplus.sghss.model.Auditoria;
import br.com.vidaplus.sghss.repository.AuditoriaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auditoria")
public class AuditoriaController {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaController(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AuditoriaResponseDTO> listar() {
        return auditoriaRepository.findAll()
                .stream()
                .map(AuditoriaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
