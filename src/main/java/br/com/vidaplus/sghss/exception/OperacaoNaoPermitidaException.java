package br.com.vidaplus.sghss.exception;

/**
 * Exceção personalizada para indicar que uma operação não é permitida.
 *
 * @author Patricky Lucas
 */
public class OperacaoNaoPermitidaException extends RuntimeException {
    public OperacaoNaoPermitidaException(String mensagem) {
        super(mensagem);
    }
}