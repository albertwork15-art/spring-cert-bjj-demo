package tournamentbjj.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tournamentbjj.demo.model.Competidor;
import tournamentbjj.demo.model.BeltColor;
import tournamentbjj.demo.repository.CompetidorRepository;

@Service // Indica que esta clase maneja la lógica de negocio
public class CompetidorService {

    @Autowired
    private CompetidorRepository competidorRepository;

    // Caso 1: Ejecución exitosa. La transacción hace Commit al finalizar sin errores.
    @Transactional // Abre una transacción de base de datos
    public Competidor registrarExitoso(String nombre, String equipo, int peso, int edad, String genero) {
        Competidor competidor = new Competidor(null, nombre, BeltColor.BLANCO, equipo, peso, edad, genero, null, null);
        return competidorRepository.save(competidor);
    }

    // Caso 2: Lanzamiento de RuntimeException. Spring hace ROLLBACK automático.
    // Examen de certificación: ¿Las RuntimeExceptions provocan rollback por defecto? SÍ.
    @Transactional
    public void registrarConFalloRuntime(String nombre, String equipo, int peso, int edad, String genero) {
        Competidor competidor = new Competidor(null, nombre, BeltColor.BLANCO, equipo, peso, edad, genero, null, null);
        competidorRepository.save(competidor);

        // Provocamos un error en tiempo de ejecución
        throw new RuntimeException("Error simulado: RuntimeException fuerza rollback automático");
    }

    // Caso 3: Lanzamiento de Exception Verificada (Checked Exception). Spring NO hace rollback.
    // Examen de certificación: ¿Las Checked Exceptions hacen rollback por defecto? NO.
    @Transactional
    public void registrarConFalloChecked(String nombre, String equipo, int peso, int edad, String genero) throws Exception {
        Competidor competidor = new Competidor(null, nombre, BeltColor.BLANCO, equipo, peso, edad, genero, null, null);
        competidorRepository.save(competidor);

        // Provocamos una Checked Exception común
        throw new Exception("Error simulado: Checked Exception NO hace rollback por defecto");
    }

    // Caso 4: Lanzamiento de Checked Exception con configuración rollbackFor.
    // Examen de certificación: ¿Cómo obligar a Spring a hacer rollback en Checked Exceptions? Con rollbackFor.
    @Transactional(rollbackFor = Exception.class) // Forzar rollback ante cualquier tipo de Exception
    public void registrarConFalloCheckedCorregido(String nombre, String equipo, int peso, int edad, String genero) throws Exception {
        Competidor competidor = new Competidor(null, nombre, BeltColor.BLANCO, equipo, peso, edad, genero, null, null);
        competidorRepository.save(competidor);

        throw new Exception("Error simulado: rollbackFor=Exception.class fuerza el rollback");
    }
}
