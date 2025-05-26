package br.com.vidaplus.sghss.exception;

/**
 * Exceção personalizada para indicar que um recurso não foi encontrado.
 * Esta exceção estende a classe RuntimeException, permitindo que seja lançada
 * em qualquer lugar do código sem a necessidade de declaração em métodos.
 */
public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
