package br.com.vidaplus.sghss.exception;

/**
 * Exceção personalizada para indicar que o usuário não possui permissão para realizar uma ação.
 */
public class ProntuarioJaExisteException extends RuntimeException {
    public ProntuarioJaExisteException(String mensagem) {
        super(mensagem);
    }
}