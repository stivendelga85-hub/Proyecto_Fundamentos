package co.edu.uniajc.matriculas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Estudiante {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(unique = true, nullable = false) private String codigo;
    @NotBlank private String documento;
    @NotBlank private String nombre;
    @Email private String correo;
    @ManyToOne(optional = false) private Programa programa;
    /** Cuenta de acceso del estudiante (ER: USUARIO 1:0..1 ESTUDIANTE). */
    @OneToOne private Usuario usuario;
    @Enumerated(EnumType.STRING) private Estados.Registro estado = Estados.Registro.ACTIVO;
}
