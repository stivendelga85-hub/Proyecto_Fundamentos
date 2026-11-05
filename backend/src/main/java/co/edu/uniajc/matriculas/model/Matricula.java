package co.edu.uniajc.matriculas.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Matricula {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) private Estudiante estudiante;
    @ManyToOne(optional = false) private Grupo grupo;
    private LocalDateTime fecha = LocalDateTime.now();
    @Enumerated(EnumType.STRING) private Estados.Matricula estado = Estados.Matricula.ACTIVA;
}
