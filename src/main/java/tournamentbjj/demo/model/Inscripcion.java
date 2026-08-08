package tournamentbjj.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity                                                    // Define la clase como una tabla en la BD
@Table(name = "inscripciones")                             // Nombre de la tabla en SQL
@Data                                                      // Genera getters, setters, toString, etc. automáticamente
@NoArgsConstructor                                         // Constructor vacío obligatorio para JPA
@AllArgsConstructor                                        // Constructor con todos los campos para desarrollo
public class Inscripcion {

    @Id                                                    // Llave Primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)    // ID autoincrementable
    private Long id;

    @NotNull(message = "El competidor es obligatorio")     // Valida que la relación no esté vacía
    @ManyToOne                                             // Relación Muchos a Uno: Muchas inscripciones pertenecen a UN competidor
    @JoinColumn(name = "competidor_id", nullable = false)  // Clave foránea (FK) apuntando a la tabla competidores
    private Competidor competidor;

    @NotNull(message = "El torneo es obligatorio")         // Valida que el torneo esté presente
    @ManyToOne                                             // Relación Muchos a Uno: Muchas inscripciones pertenecen a UN torneo
    @JoinColumn(name = "torneo_id", nullable = false)      // Clave foránea (FK) apuntando a la tabla torneos
    private Torneo torneo;

    @NotNull(message = "La categoría es obligatoria")      // Valida que la categoría esté presente
    @ManyToOne                                             // Relación Muchos a Uno: Muchas inscripciones pertenecen a UNA categoría
    @JoinColumn(name = "categoria_id", nullable = false)   // Clave foránea (FK) apuntando a la tabla categorias
    private Categoria categoria;

    @NotBlank(message = "El estado de pago es obligatorio") // Valida que el estado del pago no esté vacío
    @Column(name = "estado_pago", nullable = false)        // Mapea la columna de pago (PENDIENTE, APROBADO, etc.)
    private String estadoPago; 

    @Column(name = "fecha_inscripcion", nullable = false)  // Guarda la fecha y hora en que el competidor se registró
    private LocalDateTime fechaInscripcion;
}
