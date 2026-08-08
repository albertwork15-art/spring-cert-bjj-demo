# Simulador de Torneo BJJ - Guia de Estudio Spring Professional

Este repositorio contiene una implementacion practica disenada para cubrir los conceptos fundamentales evaluados en el examen de certificacion Spring Professional. El proyecto simula el flujo de registro, disponibilidad y transacciones en un torneo de Jiu-Jitsu Brasileño (BJJ).

---

## Temas de Certificacion Implementados

### 1. Inversion de Control (IoC) y Contenedor de Beans
*   Definicion y registro de Beans usando las anotaciones `@Configuration`, `@Bean` y `@Component`.
*   Inyeccion de propiedades externas con `@Value`.
*   Control del ciclo de vida del Bean a traves de los metodos anotados con `@PostConstruct` y `@PreDestroy`.
*   Diferencias de comportamiento entre los scopes **Singleton** y **Prototype**.

### 2. Programacion Orientada a Aspectos (AOP)
*   Implementacion de aspectos transversales utilizando la anotacion `@Aspect`.
*   Definicion de Pointcuts y ejecucion de Advices de tipo `@Before`, `@AfterReturning`, `@AfterThrowing` y `@Around`.
*   Medicion de tiempos de ejecucion y trazabilidad de logs sin alterar la logica de negocio.

### 3. Gestion de Transacciones y Capa de Persistencia
*   Operaciones CRUD mapeadas a una base de datos H2 en memoria mediante Spring Data JPA.
*   Control de transacciones utilizando la anotacion `@Transactional`.
*   Verificacion del comportamiento de rollbacks:
    *   Rollback automatico ante excepciones de tipo `RuntimeException` (Unchecked).
    *   Persistencia de datos por defecto ante excepciones verificadas (`Exception` / Checked).
    *   Uso del atributo `rollbackFor` para forzar el rollback en excepciones verificadas.

### 4. Capa Web REST (Spring MVC)
*   Controladores de entrada basados en la anotacion `@RestController`.
*   Mapeo de peticiones y variables con `@GetMapping`, `@PostMapping` y `@PathVariable`.
*   Validaciones de entrada utilizando anotaciones de Jakarta Bean Validation (`@NotBlank`, `@Min`, `@NotNull`) con el validador `@Valid`.
*   Captura global de excepciones mediante `@RestControllerAdvice` y `@ExceptionHandler`.

### 5. Seguridad Web (Spring Security)
*   Filtro de seguridad estructurado en `SecurityFilterChain` utilizando autenticacion HTTP Basic.
*   Reglas de autorizacion por roles (acceso publico en consultas y restringido a rol `ADMIN` en escrituras).
*   Configuracion de seguridad a nivel de metodo con `@EnableMethodSecurity`.
*   Codificacion obligatoria de contraseñas mediante el algoritmo `BCryptPasswordEncoder`.

### 6. Monitoreo (Spring Boot Actuator)
*   Exposicion de endpoints personalizados utilizando la anotacion `@Endpoint`.
*   Implementacion de operaciones del ciclo REST: `@ReadOperation`, `@WriteOperation` y `@DeleteOperation`.
*   Monitoreo del estado del sistema mediante la implementacion de la interfaz `HealthIndicator`.

### 7. Estrategia de Pruebas (Testing)
*   Pruebas de integracion utilizando `@SpringBootTest` con anulacion de propiedades y simulacion de servidor web (`WebEnvironment.MOCK`).
*   Configuracion y arranque del entorno de pruebas web mediante `@AutoConfigureMockMvc` y `MockMvc`.
*   Uso de `@MockitoBean` para aislar dependencias del repositorio.
*   Simulacion de contextos de seguridad autenticados mediante `@WithMockUser`.

---

## Ejecucion del Proyecto

### Iniciar la Aplicacion
```bash
./mvnw spring-boot:run
```

### Ejecutar Tests
```bash
./mvnw test
```
