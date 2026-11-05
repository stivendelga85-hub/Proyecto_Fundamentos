package co.edu.uniajc.matriculas.web;

import co.edu.uniajc.matriculas.model.Estudiante;
import co.edu.uniajc.matriculas.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

/** HU-07: datos academicos basicos del estudiante. */
@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteRepository estudiantes;

    @GetMapping("/{id}")
    Estudiante porId(@PathVariable Long id) {
        return estudiantes.findById(id).orElseThrow(() -> new NoSuchElementException("Estudiante no encontrado"));
    }
}
