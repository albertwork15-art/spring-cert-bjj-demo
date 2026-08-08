package tournamentbjj.demo.boot;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// =========================================================================
// 🎯 CONCEPTOS CLAVE DEL EXAMEN (SPRING BOOT ACTUATOR & CUSTOM ENDPOINTS)
// 1. @Endpoint: Registra esta clase como un punto de acceso (endpoint) de Actuator.
//    - El parámetro 'id' define la ruta en la URI (ej. /actuator/torneo-status).
// 2. @Component: Es necesario registrar el endpoint como un Bean de Spring para que sea detectado.
// =========================================================================
@Component
@Endpoint(id = "torneo-status") // URL: http://localhost:8082/actuator/torneo-status
public class CustomActuatorEndpoint {

    private final Map<String, String> statusMap = new ConcurrentHashMap<>();

    public CustomActuatorEndpoint() {
        // Inicializamos algunos datos de prueba
        statusMap.put("registro", "ABIERTO");
        statusMap.put("brackets", "PREPARADOS");
        statusMap.put("sistema-arbitraje", "ONLINE");
    }

    // =========================================================================
    // 🎯 CONCEPTO DEL EXAMEN: @ReadOperation (Petición HTTP GET)
    // - Mapea a una petición GET en el endpoint /actuator/torneo-status.
    // - Debe devolver un objeto serializable a JSON (habitualmente un Map o DTO).
    // =========================================================================
    @ReadOperation
    public Map<String, String> obtenerEstadoGeneral() {
        return statusMap;
    }

    // =========================================================================
    // 🎯 CONCEPTO DEL EXAMEN: @ReadOperation con @Selector (GET con variable de ruta)
    // - @Selector permite capturar una variable dinámica en la URL (ej. /actuator/torneo-status/registro).
    // - Es equivalente al @PathVariable de Spring MVC.
    // =========================================================================
    @ReadOperation
    public String obtenerEstadoEspecifico(@Selector String clave) {
        return statusMap.getOrDefault(clave, "CLAVE_NO_ENCONTRADA");
    }

    // =========================================================================
    // 🎯 CONCEPTO DEL EXAMEN: @WriteOperation (Petición HTTP POST)
    // - Mapea a una petición POST para actualizar el estado del recurso.
    // - En Actuator, los parámetros del método POST se envían en el cuerpo JSON de la petición.
    // =========================================================================
    @WriteOperation
    public Map<String, String> actualizarEstado(String clave, String valor) {
        statusMap.put(clave, valor);
        return statusMap;
    }

    // =========================================================================
    // 🎯 CONCEPTO DEL EXAMEN: @DeleteOperation (Petición HTTP DELETE)
    // - Mapea a una petición DELETE para remover algún estado.
    // =========================================================================
    @DeleteOperation
    public Map<String, String> eliminarEstado(@Selector String clave) {
        statusMap.remove(clave);
        return statusMap;
    }
}
