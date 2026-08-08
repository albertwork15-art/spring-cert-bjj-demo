package tournamentbjj.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tournamentbjj.demo.model.Inscripcion;

@Repository // Componente de acceso a datos para Inscripciones
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
}
