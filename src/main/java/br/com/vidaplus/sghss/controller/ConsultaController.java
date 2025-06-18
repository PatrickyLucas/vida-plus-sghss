package br.com.vidaplus.sghss.controller;

import br.com.vidaplus.sghss.dto.request.ConsultaRequestDTO;
import br.com.vidaplus.sghss.dto.response.ApiResponseDTO;
import br.com.vidaplus.sghss.dto.response.ConsultaResponseDTO;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.exception.UsuarioSemPermissaoException;
import br.com.vidaplus.sghss.mapper.ConsultaMapper;
import br.com.vidaplus.sghss.model.Consulta;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import br.com.vidaplus.sghss.service.ConsultaService;
import br.com.vidaplus.sghss.service.PacienteService;
import br.com.vidaplus.sghss.service.ProfissionalSaudeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para operações relacionadas a Consultas.
 * Disponibiliza endpoints para listar, buscar, criar, atualizar e excluir consultas.
 *
 * @author Patricky Lucas
 */
@RestController
@RequestMapping("/api/consultas")
@Tag(name = "Consultas", description = "Operações relacionadas a Consultas")
public class ConsultaController {

    /**
     * Serviço de Consulta utilizado para as operações CRUD.
     */
    private final ConsultaService consultaService;
    private final PacienteService pacienteService;
    private final ProfissionalSaudeService profissionalService;
    private final ConsultaMapper consultaMapper;

    /**
     * Construtor do ConsultaController.
     *
     * @param consultaService serviço de consulta
     * @param pacienteService serviço de paciente
     * @param profissionalService serviço de profissional de saúde
     * @param consultaMapper mapeador para conversão entre entidades e DTOs
     */
    public ConsultaController(ConsultaService consultaService, PacienteService pacienteService, ProfissionalSaudeService profissionalService, ConsultaMapper consultaMapper) {
        this.consultaService = consultaService;
        this.pacienteService = pacienteService;
        this.profissionalService = profissionalService;
        this.consultaMapper = consultaMapper;
    }

    /**
     * Lista todas as consultas.
     *
     * @return lista de ConsultaResponseDTO
     */
    @GetMapping
    @Operation(
            summary = "Listar Consultas",
            description = "Lista todas as consultas registradas no sistema." +
                    " Apenas usuários com permissão ADMIN ou MEDICO podem acessar esta lista."
    )
    public ResponseEntity<List<ConsultaResponseDTO>> listarTodas() {
        // Verifica se o usuário é ADMIN ou MEDICO
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOuMedico = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MEDICO"));
        if (!isAdminOuMedico) {
            throw new UsuarioSemPermissaoException("Você não tem permissão para acessar esta lista.");
        }
        List<Consulta> consultas = consultaService.listarTodas();
        List<ConsultaResponseDTO> resposta = consultas.stream()
                .map(consultaMapper::toResponseDTO)
                .collect(Collectors.toList());
        // Lança uma exceção se não houver consultas
        if (resposta.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma consulta encontrada");
        }
        return ResponseEntity.ok(resposta);
    }

    /**
     * Busca uma consulta pelo ID.
     *
     * @param id ID da consulta a ser buscada
     * @return ConsultaResponseDTO da consulta encontrada
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar Consulta por ID",
            description = "Busca uma consulta pelo ID fornecido. " +
                    "Retorna uma exceção se a consulta não for encontrada." +
                    " Apenas usuários com permissão ADMIN, MEDICO ou o próprio paciente podem acessar esta consulta."
    )
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Consulta consulta = consultaService.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada"));

        boolean isAdminOuMedico = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MEDICO"));

        if (!isAdminOuMedico && !consulta.getPaciente().getUsuario().getUsername().equals(username)) {
            throw new UsuarioSemPermissaoException("Você não tem permissão para acessar esta consulta.");
        }
        return ResponseEntity.ok(consultaMapper.toResponseDTO(consulta));
    }

    /**
     * Cria uma nova consulta.
     *
     * @param requestDTO dados da consulta a serem criados
     * @return ConsultaResponseDTO da consulta criada
     */
    @PostMapping
    @Operation(
            summary = "Criar Consulta",
            description = "Cria uma nova consulta com os dados fornecidos. " +
                    "O paciente e o profissional de saúde devem existir no sistema." +
                    "Apenas usuários com permissão ADMIN ou MEDICO podem criar consultas."
    )
    public ResponseEntity<ConsultaResponseDTO> salvar(@Valid @RequestBody ConsultaRequestDTO requestDTO) {
        Paciente paciente = pacienteService.buscarPorId(requestDTO.getPacienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente com ID " + requestDTO.getPacienteId() + " não encontrado"));

        ProfissionalSaude profissional = profissionalService.buscarPorId(requestDTO.getProfissionalId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional com ID " + requestDTO.getProfissionalId() + " não encontrado"));

        Consulta novaConsulta = consultaMapper.toEntity(requestDTO, paciente, profissional);
        Consulta consultaSalva = consultaService.salvarConsulta(novaConsulta);
        ConsultaResponseDTO resposta = consultaMapper.toResponseDTO(consultaSalva);
        // Verifica se o usuário é ADMIN ou MEDICO
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOuMedico = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MEDICO"));
        if (!isAdminOuMedico) {
            throw new UsuarioSemPermissaoException("Você não tem permissão para criar uma consulta.");
        }
        // Lança uma exceção se a consulta não for criada corretamente
        if (resposta == null) {
            throw new RecursoNaoEncontradoException("Erro ao criar consulta. Verifique os dados fornecidos.");
        }
        return ResponseEntity.ok(resposta);
    }

    /**
     * Exclui uma consulta pelo ID.
     *
     * @param id ID da consulta a ser excluída
     * @return 204 No Content se excluído com sucesso
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir Consulta",
            description = "Exclui uma consulta pelo ID fornecido. " +
                    "Retorna 204 No Content se a exclusão for bem-sucedida."
    )
    public ResponseEntity<ApiResponseDTO> excluir(@PathVariable Long id) {
        // Verifica se o usuário é ADMIN ou MEDICO
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOuMedico = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MEDICO"));
        if (!isAdminOuMedico) {
            throw new UsuarioSemPermissaoException("Você não tem permissão para excluir uma consulta.");
        }
        // Verifica se a consulta existe antes de tentar excluir
        if (!consultaService.existePorId(id)) {
            throw new RecursoNaoEncontradoException("Consulta com ID " + id + " não encontrada.");
        }
        consultaService.excluirConsulta(id);

        ApiResponseDTO resposta = new ApiResponseDTO(
                HttpStatus.OK.value(),
                "Consulta com ID " + id + " excluída com sucesso."
        );

        return ResponseEntity.ok(resposta);
    }

    /**
     * Atualiza uma consulta existente.
     *
     * @param id ID da consulta a ser atualizada
     * @param requestDTO dados atualizados da consulta
     * @return ConsultaResponseDTO da consulta atualizada
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar Consulta",
            description = "Atualiza uma consulta existente com os dados fornecidos. " +
                    "O paciente e o profissional de saúde devem existir no sistema." +
                    "Apenas usuários com permissão ADMIN ou MEDICO podem atualizar consultas." +
                    "Retorna uma exceção se a consulta não for encontrada ou se ocorrer um erro ao atualizar."
    )
    public ResponseEntity<ConsultaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ConsultaRequestDTO requestDTO) {
        Paciente paciente = pacienteService.buscarPorId(requestDTO.getPacienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente com ID " + requestDTO.getPacienteId() + " não encontrado"));

        ProfissionalSaude profissional = profissionalService.buscarPorId(requestDTO.getProfissionalId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional com ID " + requestDTO.getProfissionalId() + " não encontrado"));

        Consulta consultaAtualizada = consultaService.atualizarConsulta(id, requestDTO, pacienteService);
        // Verifica se o usuário é ADMIN ou MEDICO
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOuMedico = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MEDICO"));
        if (!isAdminOuMedico) {
            throw new UsuarioSemPermissaoException("Você não tem permissão para atualizar uma consulta.");
        }
        // Lança uma exceção se a consulta não for atualizada corretamente
        if (consultaAtualizada == null) {
            throw new RecursoNaoEncontradoException("Erro ao atualizar consulta. Verifique os dados fornecidos.");
        }
        return ResponseEntity.ok(consultaMapper.toResponseDTO(consultaAtualizada));
    }
}
