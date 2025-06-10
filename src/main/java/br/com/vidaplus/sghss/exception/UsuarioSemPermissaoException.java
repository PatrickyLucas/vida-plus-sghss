package br.com.vidaplus.sghss.exception;

public class UsuarioSemPermissaoException extends RuntimeException {
    public UsuarioSemPermissaoException(String mensagem) {
        super(mensagem);
    }
}