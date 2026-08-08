package tournamentbjj.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity                                                    // Define la clase como una tabla en la base de datos (BD)
@Table(name = "torneos")                                   // Nombre específico de la tabla en SQL
@Data                                                      // Genera automáticamente getters, setters, toString, equals, etc.
@NoArgsConstructor                                         // Genera el constructor vacío (requerido por JPA)
@AllArgsConstructor                                        // Genera el constructor con todos los campos (útil para pruebas)
public class Torneo {

    @Id                                                    // Marca este campo como la Llave Primaria (PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY)    // ID autoincrementable en la base de datos
    private Long id;

    @NotBlank(message = "El nombre del torneo es obligatorio") // Valida que el texto no esté vacío ni tenga solo espacios
    @Column(name = "nombre", nullable = false)             // Nombre de la columna en BD y restringe nulos en SQL
    private String nombre;

    @NotNull(message = "La fecha del torneo es obligatoria")   // Valida que el campo fecha no llegue nulo
    @Column(name = "fecha_inicio", nullable = false)       // Mapea la columna de la fecha en BD
    private LocalDate fechaInicio;

    @Positive(message = "El límite de inscritos debe ser mayor a cero") // Valida que la cantidad de inscritos permitida sea positiva
    @Column(name = "limite_inscritos", nullable = false)   // Mapea la columna del límite en BD
    private int limiteInscritos;

    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL) // Relación 1 a Muchos: Un torneo tiene muchas inscripciones
    private List<Inscripcion> inscripciones;
}
