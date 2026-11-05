package co.edu.uniajc.matriculas.web;

import co.edu.uniajc.matriculas.model.*;
import co.edu.uniajc.matriculas.repository.*;
import co.edu.uniajc.matriculas.service.CredencialesInvalidasException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/** HU-07 / RF-06: inicio de sesion local. El frontend guarda la sesion; la autorizacion por rol (RF-18) queda para el siguiente avance. */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    public record LoginRequest(@NotBlank String username, @NotBlank String password) {}
    public record SesionDto(Long usuarioId, String username, String nombre, Estados.Rol rol,
                            Long estudianteId, String codigoEstudiante, String programa) {}

    private final UsuarioRepository usuarios;
    private final EstudianteRepository estudiantes;
    private final PasswordEncoder encoder;

    @PostMapping("/login")
    SesionDto login(@Valid @RequestBody LoginRequest r) {
        Usuario u = usuarios.findByUsername(r.username())
                .filter(x -> x.getEstado() == Estados.Registro.ACTIVO)
                .filter(x -> encoder.matches(r.password(), x.getPasswordHash()))
                .orElseThrow(CredencialesInvalidasException::new);
        Estudiante e = estudiantes.findByUsuarioId(u.getId()).orElse(null);
        return new SesionDto(u.getId(), u.getUsername(), u.getNombre(), u.getRol(),
                e == null ? null : e.getId(),
                e == null ? null : e.getCodigo(),
                e == null ? null : e.getPrograma().getNombre());
    }
}
