package tournamentbjj.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tournamentbjj.demo.repository.CompetidorRepository;

@Component // Registra la clase como un componente ejecutable de Spring Boot
public class DataDemoRunner implements CommandLineRunner {

    @Autowired
    private CompetidorService competidorService;

    @Autowired
    private CompetidorRepository competidorRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n=== 🚀 INICIANDO DEMOSTRACIÓN DE TRANSACCIONES Y BD ===");

        // Limpiamos la base de datos para empezar desde cero
        competidorRepository.deleteAll();

        // 1. Caso Exitoso
        System.out.println("\n--- Caso 1: Registrar Competidor Exitoso ---");
        competidorService.registrarExitoso("Gordon Ryan", "Renzo Gracie", 99, 29, "Masculino");
        System.out.println("Competidores guardados en BD: " + competidorRepository.findAll().size()); // Debería ser 1

        // 2. Caso Fallo RuntimeException (Rollback esperado)
        System.out.println("\n--- Caso 2: Registrar con Fallo de RuntimeException ---");
        try {
            competidorService.registrarConFalloRuntime("Mikey Musumeci", "Cobrinha", 61, 28, "Masculino");
        } catch (RuntimeException e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }
        // Examen: Como hubo rollback, el total de competidores NO debe aumentar
        System.out.println("Competidores guardados en BD: " + competidorRepository.findAll().size()); // Debería seguir siendo 1

        // 3. Caso Fallo Checked Exception (NO hace Rollback por defecto)
        System.out.println("\n--- Caso 3: Registrar con Fallo de Checked Exception ---");
        try {
            competidorService.registrarConFalloChecked("Felipe Pena", "Alliance", 94, 32, "Masculino");
        } catch (Exception e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }
        // Examen: Como Spring NO hace rollback ante Exception por defecto, ¡el competidor se guardó a pesar del error!
        System.out.println("Competidores guardados en BD: " + competidorRepository.findAll().size()); // Debería ser 2

        // 4. Caso Fallo Checked Exception Corregido con rollbackFor (Rollback esperado)
        System.out.println("\n--- Caso 4: Registrar con Checked Exception Corregido ---");
        try {
            competidorService.registrarConFalloCheckedCorregido("Nicholas Meregali", "Dream Art", 98, 27, "Masculino");
        } catch (Exception e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }
        // Examen: Al usar rollbackFor=Exception.class, el rollback sí ocurrió, por lo que el total no cambia
        System.out.println("Competidores guardados en BD: " + competidorRepository.findAll().size()); // Debería seguir siendo 2

        System.out.println("\n=== 🛑 FIN DE LA DEMOSTRACIÓN DE TRANSACCIONES ===\n");
    }
}
