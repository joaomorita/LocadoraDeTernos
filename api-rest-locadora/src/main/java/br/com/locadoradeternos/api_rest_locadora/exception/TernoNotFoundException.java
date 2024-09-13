package br.com.locadoradeternos.api_rest_locadora.exception;

public class TernoNotFoundException extends RuntimeException {
    public TernoNotFoundException(Long id) {
        super("Terno com ID " + id + " não foi encontrado.");
    }
}