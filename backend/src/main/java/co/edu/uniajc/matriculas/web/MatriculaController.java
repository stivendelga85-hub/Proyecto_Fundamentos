package co.edu.uniajc.matriculas.web;

import co.edu.uniajc.matriculas.model.Estados;
import co.edu.uniajc.matriculas.model.Matricula;
import co.edu.uniajc.matriculas.repository.MatriculaRepository;
import co.edu.uniajc.matriculas.service.MatriculaService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** HU-12 (matricular) y HU-14 (carga academica). */
@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    public record MatriculaRequest(@NotNull Long estudianteId, @NotNull Long grupoId) {}

    private final MatriculaService service;
    private final MatriculaRepository repo;

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    Matricula matricular(@RequestBody MatriculaRequest r) { return service.matricular(r.estudianteId(), r.grupoId()); }

    @GetMapping
    List<Matricula> cargaAcademica(@RequestParam Long estudianteId) {
        return repo.findByEstudianteIdAndEstado(estudianteId, Estados.Matricula.ACTIVA);
    }
}
