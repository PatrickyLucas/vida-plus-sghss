package br.com.vidaplus.sghss.exception;

public class ProntuarioJaExisteException extends RuntimeException {
    public ProntuarioJaExisteException(String mensagem) {
        super(mensagem);
    }
}