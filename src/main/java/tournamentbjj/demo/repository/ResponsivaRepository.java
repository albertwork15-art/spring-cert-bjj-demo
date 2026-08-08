package tournamentbjj.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tournamentbjj.demo.model.Responsiva;

@Repository // Componente de acceso a datos para Responsivas
public interface ResponsivaRepository extends JpaRepository<Responsiva, Long> {
}
