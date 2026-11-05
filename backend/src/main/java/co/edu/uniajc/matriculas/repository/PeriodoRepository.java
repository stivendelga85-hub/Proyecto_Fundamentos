package co.edu.uniajc.matriculas.repository;

import co.edu.uniajc.matriculas.model.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodoRepository extends JpaRepository<Periodo, Long> {
}
