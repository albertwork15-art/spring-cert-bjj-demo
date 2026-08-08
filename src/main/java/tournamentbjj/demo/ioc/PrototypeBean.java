package tournamentbjj.demo.ioc;

import java.util.UUID;

public class PrototypeBean {

    // Genera un ID aleatorio único para esta instancia
    private final String id = UUID.randomUUID().toString();

    // Método para poder leer el ID desde fuera de la clase
    public String getId() {
        return id;
    }
}
