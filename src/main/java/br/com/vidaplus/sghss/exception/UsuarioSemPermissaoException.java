package br.com.vidaplus.sghss.exception;

/**
 * Exceção personalizada para indicar que o usuário não possui permissão para realizar uma ação.
 *
 * @author Patricky Lucas
 */
public class UsuarioSemPermissaoException extends RuntimeException {
    public UsuarioSemPermissaoException(String mensagem) {
        super(mensagem);
    }
}