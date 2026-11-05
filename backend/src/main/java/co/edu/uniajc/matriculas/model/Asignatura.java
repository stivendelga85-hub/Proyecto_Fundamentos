package co.edu.uniajc.matriculas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Asignatura {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(unique = true, nullable = false) private String codigo;
    @NotBlank @Column(nullable = false) private String nombre;
    @Min(1) private int creditos;
    @Enumerated(EnumType.STRING) private Estados.Registro estado = Estados.Registro.ACTIVO;
}
