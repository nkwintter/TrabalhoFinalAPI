package org.serratec.trabalho.exception;


public class ClienteNaoAutorizadoException extends RuntimeException {
    public ClienteNaoAutorizadoException(String message) {
        super(message);
    }
}