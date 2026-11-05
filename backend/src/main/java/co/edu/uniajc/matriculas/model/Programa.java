package co.edu.uniajc.matriculas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Programa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(unique = true, nullable = false) private String codigo;
    @NotBlank @Column(nullable = false) private String nombre;
    private String nivel;
    @Enumerated(EnumType.STRING) private Estados.Registro estado = Estados.Registro.ACTIVO;
}
