package co.edu.uniajc.matriculas.service;

public class CredencialesInvalidasException extends RuntimeException {
    public CredencialesInvalidasException() { super("Usuario o contrasena incorrectos"); }
}
