package tournamentbjj.demo.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AopDemoRunner implements CommandLineRunner {

    @Autowired
    private AopDemoService service;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n=== 🚀 INICIANDO DEMOSTRACIÓN DE AOP ===");

        System.out.println("\n--- Caso 1: Ejecución normal ---");
        service.performAction("Registrar competidor");

        System.out.println("\n--- Caso 2: Lanzamiento de excepción ---");
        try {
            service.throwDemoException();
        } catch (Exception e) {
            System.out.println("[Main] Excepción capturada en el flujo principal: " + e.getMessage());
        }

        System.out.println("\n=== 🛑 FIN DE LA DEMOSTRACIÓN DE AOP ===\n");
    }
}
