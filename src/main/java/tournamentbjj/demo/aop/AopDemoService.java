package tournamentbjj.demo.aop;

import org.springframework.stereotype.Service;

@Service
public class AopDemoService {

    public String performAction(String actionName) {
        System.out.println("   -> Ejecutando la lógica de negocio real para: " + actionName);
        return "Acción '" + actionName + "' completada con éxito";
    }

    public void throwDemoException() {
        System.out.println("   -> Ejecutando método que lanzará un error...");
        throw new IllegalArgumentException("¡Ups! Ha ocurrido un error simulado.");
    }
}
