package co.edu.uniajc.matriculas;

import co.edu.uniajc.matriculas.model.*;
import co.edu.uniajc.matriculas.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/** Datos de demostracion. Solo se cargan si la base esta vacia. Clave de todos los usuarios: Demo2026 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final ProgramaRepository programas;
    private final AsignaturaRepository asignaturas;
    private final EstudianteRepository estudiantes;
    private final PeriodoRepository periodos;
    private final GrupoRepository grupos;
    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;

    public DataSeeder(ProgramaRepository p, AsignaturaRepository a, EstudianteRepository e,
                      PeriodoRepository pe, GrupoRepository g, UsuarioRepository u, PasswordEncoder enc) {
        programas = p; asignaturas = a; estudiantes = e; periodos = pe; grupos = g; usuarios = u; encoder = enc;
    }

    private Usuario usuario(String username, String nombre, Estados.Rol rol) {
        return usuarios.save(new Usuario(null, username, encoder.encode("Demo2026"), nombre,
                username + "@uniajc.edu.co", rol, Estados.Registro.ACTIVO));
    }

    @Override
    public void run(String... args) {
        if (programas.count() > 0) return;

        Programa sistemas = programas.save(new Programa(null, "ING-SIS", "Ingenieria de Sistemas", "Profesional", Estados.Registro.ACTIVO));
        Programa diseno = programas.save(new Programa(null, "DIS-GRA", "Diseno Grafico", "Profesional", Estados.Registro.ACTIVO));

        Asignatura web = asignaturas.save(new Asignatura(null, "FW-101", "Fundamentos Web", 3, Estados.Registro.ACTIVO));
        Asignatura bd = asignaturas.save(new Asignatura(null, "BD-201", "Bases de Datos", 3, Estados.Registro.ACTIVO));
        Asignatura ing = asignaturas.save(new Asignatura(null, "IN-100", "Ingles I", 2, Estados.Registro.ACTIVO));
        Asignatura color = asignaturas.save(new Asignatura(null, "DG-110", "Teoria del Color", 2, Estados.Registro.ACTIVO));

        Periodo p = periodos.save(new Periodo(null, "2026-2", LocalDate.of(2026, 8, 3), LocalDate.of(2026, 12, 5), Estados.Periodo.MATRICULA_ABIERTA));

        grupos.saveAll(List.of(
            new Grupo(null, web, sistemas, p, 1, 30, "Lun y Mie 7:00-9:00", "Por asignar", false, Estados.Grupo.ABIERTO),
            new Grupo(null, bd, sistemas, p, 1, 2, "Mar y Jue 9:00-11:00", "Por asignar", false, Estados.Grupo.ABIERTO),
            new Grupo(null, ing, sistemas, p, 1, 25, "Vie 8:00-12:00", "Por asignar", true, Estados.Grupo.ABIERTO),
            new Grupo(null, color, diseno, p, 1, 20, "Lun 14:00-18:00", "Por asignar", false, Estados.Grupo.ABIERTO)));

        usuario("admin", "Administrador Academico", Estados.Rol.ADMIN_ACADEMICO);
        usuario("coordinador", "Coordinador de Sistemas", Estados.Rol.COORDINADOR);
        Usuario ana = usuario("ana", "Ana Perez", Estados.Rol.ESTUDIANTE);
        Usuario luis = usuario("luis", "Luis Gomez", Estados.Rol.ESTUDIANTE);
        Usuario sofia = usuario("sofia", "Sofia Rios", Estados.Rol.ESTUDIANTE);

        estudiantes.save(new Estudiante(null, "2026001", "1000000001", "Ana Perez", "ana@uniajc.edu.co", sistemas, ana, Estados.Registro.ACTIVO));
        estudiantes.save(new Estudiante(null, "2026002", "1000000002", "Luis Gomez", "luis@uniajc.edu.co", diseno, luis, Estados.Registro.ACTIVO));
        estudiantes.save(new Estudiante(null, "2026003", "1000000003", "Sofia Rios", "sofia@uniajc.edu.co", sistemas, sofia, Estados.Registro.ACTIVO));
    }
}
