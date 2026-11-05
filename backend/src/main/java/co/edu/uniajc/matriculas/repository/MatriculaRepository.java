package co.edu.uniajc.matriculas.repository;

import co.edu.uniajc.matriculas.model.Estados;
import co.edu.uniajc.matriculas.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    long countByGrupoIdAndEstado(Long grupoId, Estados.Matricula estado);
    boolean existsByEstudianteIdAndGrupoIdAndEstado(Long estudianteId, Long grupoId, Estados.Matricula estado);
    List<Matricula> findByEstudianteIdAndEstado(Long estudianteId, Estados.Matricula estado);
}
