package br.com.vidaplus.sghss.exception;

public class UsuarioJaExisteException extends RuntimeException {
    public UsuarioJaExisteException(String mensagem) {
        super(mensagem);
    }
}