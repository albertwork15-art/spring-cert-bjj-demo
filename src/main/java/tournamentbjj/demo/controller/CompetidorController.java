package tournamentbjj.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tournamentbjj.demo.model.Competidor;
import tournamentbjj.demo.repository.CompetidorRepository;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

// =========================================================================
// 🎯 CONCEPTOS CLAVE DEL EXAMEN (SPRING MVC & REST CONTROLLERS)
// 1. @RestController es una meta-anotación que combina @Controller y @ResponseBody.
//    - @Controller: Registra la clase como un controlador web en Spring MVC.
//    - @ResponseBody: Indica que el valor de retorno de los métodos se debe escribir 
//      directamente en el cuerpo de la respuesta HTTP (habitualmente en formato JSON),
//      en lugar de buscar una plantilla HTML (como Thymeleaf o JSP).
// =========================================================================
@RestController
@RequestMapping("/api/competidores") // Define la ruta base para todos los endpoints de esta clase
public class CompetidorController {

    @Autowired
    private CompetidorRepository competidorRepository;

    // =========================================================================
    // 🎯 CONCEPTO DEL EXAMEN: @GetMapping y Lectura de Datos
    // - Mapea peticiones HTTP GET a esta ruta.
    // - Retorna una lista de competidores que Spring Boot (vía Jackson) convierte a JSON.
    // =========================================================================
    @GetMapping
    public List<Competidor> obtenerTodos() {
        return competidorRepository.findAll();
    }

    // =========================================================================
    // 🎯 CONCEPTO DEL EXAMEN: @PathVariable (Variables de Ruta)
    // - /{id} define una variable dinámica en la URI.
    // - @PathVariable vincula la variable de la URI con el parámetro del método Java.
    // - Si el nombre coincide (ej. "id"), se vincula automáticamente.
    // =========================================================================
    @GetMapping("/{id}")
    public ResponseEntity<Competidor> obtenerPorId(@PathVariable Long id) {
        return competidorRepository.findById(id)
                .map(competidor -> ResponseEntity.ok(competidor)) // Si existe, retorna Status 200 OK y el JSON
                .orElse(ResponseEntity.notFound().build());        // Si no, retorna Status 404 Not Found
    }

    // =========================================================================
    // 🎯 CONCEPTO DEL EXAMEN: @PostMapping, @RequestBody y Validación con @Valid
    // - @PostMapping indica que este método procesará peticiones HTTP POST (crear recursos).
    // - @RequestBody indica a Spring que tome el cuerpo JSON del HTTP request y lo 
    //   deserialice en un objeto de tipo Competidor.
    // - @Valid es CRÍTICO: Indica a Spring que ejecute las anotaciones de validación 
    //   (como @NotBlank, @Min, @Positive) que pusimos en la clase Competidor.java. 
    //   Si las reglas fallan, Spring lanza una excepción y no entra al método.
    // =========================================================================
    @PostMapping
    public ResponseEntity<Competidor> crearCompetidor(@Valid @RequestBody Competidor competidor) {
        Competidor guardado = competidorRepository.save(competidor);
        // Retorna status 201 Created y el competidor recién creado
        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }

    // =========================================================================
    // 🎯 CONCEPTO DEL EXAMEN: Manejo Global de Excepciones y Códigos de Estado
    // - @RestControllerAdvice: Captura excepciones lanzadas por cualquier controlador.
    // - @ExceptionHandler: Especifica qué tipo de excepción atrapar.
    // - @ResponseStatus: Configura el código HTTP de respuesta para este error.
    // =========================================================================
    @RestControllerAdvice // 👈 Clase de asesoramiento global para controladores REST
    public static class GlobalExceptionHandler {

        // Atrapa fallas de validación de campos (cuando falla @Valid / @NotBlank / @Min, etc.)
        @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST) // Retorna HTTP 400 Bad Request
        public Map<String, String> manejarErroresValidacion(
                org.springframework.web.bind.MethodArgumentNotValidException ex) {
            
            Map<String, String> errores = new HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(error -> {
                errores.put(error.getField(), error.getDefaultMessage());
            });
            return errores; // Retorna un JSON con los campos fallidos y sus mensajes de error
        }

        // Atrapa cualquier otro error genérico de la aplicación
        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Retorna HTTP 500 Server Error
        public Map<String, String> manejarOtrosErrores(Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getMessage());
            return error;
        }
    }
}
