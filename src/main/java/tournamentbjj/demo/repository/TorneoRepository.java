package tournamentbjj.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tournamentbjj.demo.model.Torneo;

@Repository // Componente de acceso a datos para Torneos
public interface TorneoRepository extends JpaRepository<Torneo, Long> {
}
