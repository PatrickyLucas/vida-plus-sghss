package br.com.vidaplus.sghss.logging;

import br.com.vidaplus.sghss.model.Auditoria;
import br.com.vidaplus.sghss.repository.AuditoriaRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditoriaAspect {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaAspect(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    @AfterReturning("execution(* br.com.vidaplus.sghss.service.*.*(..)) && !execution(* br.com.vidaplus.sghss.service.CustomUserDetailsService.loadUserByUsername(..))")
    public void auditar(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usuario = (authentication != null && authentication.getName() != null) ? authentication.getName() : "ANÔNIMO";
        String acao = joinPoint.getSignature().toShortString();
        String detalhes = "Args: " + java.util.Arrays.toString(joinPoint.getArgs());

        Auditoria auditoria = new Auditoria();
        auditoria.setUsuario(usuario);
        auditoria.setAcao(acao);
        auditoria.setDetalhes(detalhes);
        auditoria.setDataHora(LocalDateTime.now());

        auditoriaRepository.save(auditoria);
    }
}