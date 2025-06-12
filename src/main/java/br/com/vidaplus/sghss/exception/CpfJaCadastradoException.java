package br.com.vidaplus.sghss.exception;

/**
 * Exceção personalizada para indicar que o usuário não possui permissão para realizar uma ação.
 *
 * @author Patricky Lucas
 */
public class CpfJaCadastradoException extends RuntimeException {
    public CpfJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}