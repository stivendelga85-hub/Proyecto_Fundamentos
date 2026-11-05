package co.edu.uniajc.matriculas.repository;

import co.edu.uniajc.matriculas.model.Estados;
import co.edu.uniajc.matriculas.model.Grupo;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    /** Bloquea la fila del grupo para que dos matriculas simultaneas no tomen el ultimo cupo. */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select g from Grupo g where g.id = :id")
    Optional<Grupo> findByIdForUpdate(@Param("id") Long id);

    @Query("""
           select g from Grupo g
           where g.periodo.estado = :estadoPeriodo
             and g.estado = :estadoGrupo
             and (g.programa.id = :programaId or g.electivo = true)
           """)
    List<Grupo> findOfertaDisponible(@Param("programaId") Long programaId,
                                     @Param("estadoPeriodo") Estados.Periodo estadoPeriodo,
                                     @Param("estadoGrupo") Estados.Grupo estadoGrupo);
}
