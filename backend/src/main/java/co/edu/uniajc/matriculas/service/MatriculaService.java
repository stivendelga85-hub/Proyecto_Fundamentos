package co.edu.uniajc.matriculas.service;

import co.edu.uniajc.matriculas.model.*;
import co.edu.uniajc.matriculas.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    private final MatriculaRepository matriculas;
    private final GrupoRepository grupos;
    private final EstudianteRepository estudiantes;

    /** HU-12 / HU-13: matricula validando RN-01 a RN-05 dentro de una sola transaccion. */
    @Transactional
    public Matricula matricular(Long estudianteId, Long grupoId) {
        Estudiante est = estudiantes.findById(estudianteId)
                .orElseThrow(() -> new NoSuchElementException("Estudiante no encontrado"));
        Grupo grupo = grupos.findByIdForUpdate(grupoId)
                .orElseThrow(() -> new NoSuchElementException("Grupo no encontrado"));

        if (est.getEstado() != Estados.Registro.ACTIVO)
            throw new ReglaNegocioException("El estudiante no esta activo");
        if (grupo.getPeriodo().getEstado() != Estados.Periodo.MATRICULA_ABIERTA)          // RN-01
            throw new ReglaNegocioException("El periodo no tiene la matricula abierta");
        if (grupo.getEstado() != Estados.Grupo.ABIERTO)                                    // RN-02
            throw new ReglaNegocioException("El grupo esta " + grupo.getEstado());
        boolean permitido = grupo.isElectivo()
                || grupo.getPrograma().getId().equals(est.getPrograma().getId());          // RN-05
        if (!permitido)
            throw new ReglaNegocioException("El grupo no pertenece al programa del estudiante");
        if (matriculas.existsByEstudianteIdAndGrupoIdAndEstado(estudianteId, grupoId,
                Estados.Matricula.ACTIVA))                                                 // RN-04
            throw new ReglaNegocioException("El estudiante ya esta matriculado en este grupo");
        long ocupados = matriculas.countByGrupoIdAndEstado(grupoId, Estados.Matricula.ACTIVA);
        if (ocupados >= grupo.getCupo())                                                   // RN-03
            throw new ReglaNegocioException("El grupo no tiene cupos disponibles");

        Matricula m = new Matricula();
        m.setEstudiante(est);
        m.setGrupo(grupo);
        return matriculas.save(m);
    }

    public long cuposDisponibles(Grupo g) {
        return g.getCupo() - matriculas.countByGrupoIdAndEstado(g.getId(), Estados.Matricula.ACTIVA);
    }

    /** HU-10: oferta que el estudiante puede matricular. */
    public List<Grupo> ofertaPara(Long estudianteId) {
        Estudiante est = estudiantes.findById(estudianteId)
                .orElseThrow(() -> new NoSuchElementException("Estudiante no encontrado"));
        return grupos.findOfertaDisponible(est.getPrograma().getId(),
                Estados.Periodo.MATRICULA_ABIERTA, Estados.Grupo.ABIERTO);
    }
}
