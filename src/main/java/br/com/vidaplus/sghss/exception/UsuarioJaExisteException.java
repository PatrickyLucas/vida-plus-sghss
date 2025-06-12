package br.com.vidaplus.sghss.exception;

/**
 * Exceção personalizada para indicar que o usuário não possui permissão para realizar uma ação.
 */
public class UsuarioJaExisteException extends RuntimeException {
    public UsuarioJaExisteException(String mensagem) {
        super(mensagem);
    }
}