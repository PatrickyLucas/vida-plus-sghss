package br.com.vidaplus.sghss.exception;

/**
 * Exceção personalizada para indicar que uma operação não é permitida.
 */
public class OperacaoNaoPermitidaException extends RuntimeException {
    public OperacaoNaoPermitidaException(String mensagem) {
        super(mensagem);
    }
}