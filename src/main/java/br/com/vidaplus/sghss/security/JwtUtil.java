package br.com.vidaplus.sghss.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe utilitária para manipulação de tokens JWT.
 * Fornece métodos para gerar, extrair informações e validar tokens JWT.
 *
 * @author Patricky Lucas
 */
@Component
public class JwtUtil {

    /**
     * Chave secreta usada para assinar os tokens JWT.
     * Deve ser mantida em segredo e ter pelo menos 32 caracteres.
     */
    private static final String SECRET_KEY = "umaChaveSuperSeguraDePeloMenos32Caracteres!";

    /**
     * Gera um token JWT para o usuário fornecido.
     * O token inclui o nome de usuário e as roles do usuário como claims,
     * e é assinado com a chave secreta definida.
     *
     * @param userDetails Detalhes do usuário para quem o token será gerado.
     * @return Um token JWT assinado.
     */
    public String generateToken(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.replace("ROLE_", "")) // remove prefixo para armazenar limpo
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles)  // adiciona roles como claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrai o nome de usuário do token JWT fornecido.
     *
     * @param token O token JWT do qual o nome de usuário será extraído.
     * @return O nome de usuário contido no token.
     */
    public String extractUsername(String token) {
        return obterClaims(token).getSubject();
    }

    /**
     * Valida o token JWT fornecido, verificando se o nome de usuário corresponde
     * e se o token não está expirado.
     *
     * @param token O token JWT a ser validado.
     * @param username O nome de usuário a ser comparado com o token.
     * @return true se o token for válido, false caso contrário.
     */
    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

/**
     * Obtém as claims do token JWT fornecido.
     *
     * @param token O token JWT do qual as claims serão extraídas.
     * @return As claims contidas no token.
     */
    public Claims obterClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Verifica se o token JWT fornecido está expirado.
     *
     * @param token O token JWT a ser verificado.
     * @return true se o token estiver expirado, false caso contrário.
     */
    private boolean isTokenExpired(String token) {
        return obterClaims(token).getExpiration().before(new Date());
    }
}
