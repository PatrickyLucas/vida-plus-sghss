package br.com.vidaplus.sghss.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filtro de autenticação JWT que intercepta requisições HTTP
 * para verificar e validar tokens JWT, configurando o contexto de segurança.
 *
 * @author Patricky Lucas
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Utilitário para manipulação de tokens JWT.
     */
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Método que executa a lógica de filtragem para autenticação JWT.
     * Verifica o cabeçalho Authorization, extrai o token, valida e configura
     * o contexto de segurança com as informações do usuário.
     *
     * @param request  A requisição HTTP recebida.
     * @param response A resposta HTTP a ser enviada.
     * @param chain    A cadeia de filtros para continuar o processamento da requisição.
     * @throws ServletException Se ocorrer um erro durante o processamento do filtro.
     * @throws IOException      Se ocorrer um erro de I/O durante o processamento do filtro.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            String username = jwtUtil.extractUsername(token);

            if (username != null && jwtUtil.validateToken(token, username) && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Carregar o usuário para garantir que ele existe e trazer detalhes adicionais
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Extrair claims do token para pegar as roles
                Claims claims = jwtUtil.obterClaims(token);

                // Extrair lista de roles (strings) do claim "roles"
                List<String> roles = claims.get("roles", List.class);

                // Mapear para GrantedAuthority adicionando prefixo "ROLE_"
                List<GrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

                // Criar token de autenticação com as authorities do token JWT
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                // Setar autenticação no contexto do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response);
    }
}
