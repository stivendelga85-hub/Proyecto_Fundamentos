package co.edu.uniajc.matriculas.service;

/** Se lanza cuando una regla de negocio (RN-01 a RN-07) impide la operacion. */
public class ReglaNegocioException extends RuntimeException {
    public ReglaNegocioException(String mensaje) { super(mensaje); }
}
