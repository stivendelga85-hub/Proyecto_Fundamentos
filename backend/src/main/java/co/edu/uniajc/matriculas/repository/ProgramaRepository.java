package co.edu.uniajc.matriculas.repository;

import co.edu.uniajc.matriculas.model.Programa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramaRepository extends JpaRepository<Programa, Long> {
}
