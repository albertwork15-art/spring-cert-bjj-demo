package tournamentbjj.demo.boot;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

// =========================================================================
// 🎯 CONCEPTOS CLAVE DEL EXAMEN (SPRING BOOT HEALTH INDICATORS)
// 1. HealthIndicator: Interfaz core de Actuator para añadir verificaciones de salud customizadas.
// 2. Component Scanning: Al anotar con @Component, Spring registra automáticamente este indicador.
//    - El nombre de la verificación en el JSON de salud se deriva del nombre de la clase 
//      (ej. "customHealth" o "sistemaInscripciones" si la clase se llama SistemaInscripcionesHealthIndicator).
// =========================================================================
@Component("sistemaInscripciones") // Nombre de la propiedad en el JSON de /actuator/health
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Simulamos la verificación de un servicio externo (ej. la pasarela de pagos)
        boolean servicioPagoOnline = verificarPasarelaPagos();

        if (servicioPagoOnline) {
            // =========================================================================
            // 🎯 CONCEPTO DEL EXAMEN: Health.up() y Detalles Adicionales
            // - Health.up() indica que el sistema está saludable (Status: UP).
            // - .withDetail() permite agregar pares clave-valor al JSON final de salud.
            // =========================================================================
            return Health.up()
                    .withDetail("pasarela-pagos", "Disponible - Conexión exitosa")
                    .withDetail("latencia-ms", 45)
                    .build();
        } else {
            // =========================================================================
            // 🎯 CONCEPTO DEL EXAMEN: Health.down() e Impacto en el Estado Global
            // - Health.down() indica que el sistema falló (Status: DOWN).
            // - Importante: Si UN SOLO HealthIndicator devuelve DOWN, el estado de salud 
            //   global de toda la aplicación (/actuator/health) pasará a ser DOWN automáticamente.
            // =========================================================================
            return Health.down()
                    .withDetail("pasarela-pagos", "Fuera de servicio - Sin respuesta de API externa")
                    .withDetail("error-code", "ERR_CONN_TIMEOUT")
                    .build();
        }
    }

    private boolean verificarPasarelaPagos() {
        // Simulación: asumimos que la pasarela está en línea para la demostración
        return true;
    }
}
