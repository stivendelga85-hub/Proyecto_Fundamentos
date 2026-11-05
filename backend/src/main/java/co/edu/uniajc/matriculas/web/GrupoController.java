package co.edu.uniajc.matriculas.web;

import co.edu.uniajc.matriculas.model.Estados;
import co.edu.uniajc.matriculas.model.Grupo;
import co.edu.uniajc.matriculas.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** HU-10: grupos disponibles para el programa del estudiante. */
@RestController
@RequestMapping("/api/grupos")
@RequiredArgsConstructor
public class GrupoController {

    public record GrupoDto(Long id, String asignatura, int creditos, int numero, String horario,
                           Estados.Grupo estado, int cupo, long cupoDisponible) {}

    private final MatriculaService service;

    private GrupoDto dto(Grupo g) {
        return new GrupoDto(g.getId(), g.getAsignatura().getNombre(), g.getAsignatura().getCreditos(),
                g.getNumero(), g.getHorario(), g.getEstado(), g.getCupo(), service.cuposDisponibles(g));
    }

    @GetMapping("/disponibles")
    List<GrupoDto> disponibles(@RequestParam Long estudianteId) {
        return service.ofertaPara(estudianteId).stream().map(this::dto).toList();
    }
}
