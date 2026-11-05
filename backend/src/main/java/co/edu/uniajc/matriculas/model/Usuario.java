package co.edu.uniajc.matriculas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(unique = true, nullable = false) private String username;
    /** Hash BCrypt. Nunca se guarda ni se devuelve la contrasena en texto plano. */
    @JsonIgnore @Column(nullable = false) private String passwordHash;
    private String nombre;
    private String email;
    @Enumerated(EnumType.STRING) private Estados.Rol rol;
    @Enumerated(EnumType.STRING) private Estados.Registro estado = Estados.Registro.ACTIVO;
}
