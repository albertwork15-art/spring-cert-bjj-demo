package tournamentbjj.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tournamentbjj.demo.model.Competidor;

@Repository // Le dice a Spring que esta interfaz es un componente para interactuar con la Base de Datos
public interface CompetidorRepository extends JpaRepository<Competidor, Long> {
    // JpaRepository<Competidor, Long> le provee a esta interfaz métodos listos como:
    // .save(), .findAll(), .findById(), .deleteById() sin necesidad de escribir código SQL.
}
