package co.edu.uniajc.matriculas.model;

public final class Estados {
    private Estados() {}
    public enum Rol { ADMIN_ACADEMICO, COORDINADOR, ESTUDIANTE }
    public enum Registro { ACTIVO, INACTIVO }
    public enum Periodo { PLANIFICACION, MATRICULA_ABIERTA, MATRICULA_CERRADA, FINALIZADO }
    public enum Grupo { ABIERTO, CERRADO, CANCELADO }
    public enum Matricula { ACTIVA, CANCELADA }
}
