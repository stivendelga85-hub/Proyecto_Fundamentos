package co.edu.uniajc.matriculas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Grupo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) private Asignatura asignatura;
    @ManyToOne(optional = false) private Programa programa;
    @ManyToOne(optional = false) private Periodo periodo;
    @Min(1) private int numero;
    @Min(1) private int cupo;
    private String horario;
    private String docente;
    private boolean electivo = false;
    @Enumerated(EnumType.STRING) private Estados.Grupo estado = Estados.Grupo.ABIERTO;
}
