package tournamentbjj.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity                                                    // Define la clase como una tabla en la BD
@Table(name = "peleas")                                    // Nombre de la tabla en SQL
@Data                                                      // Genera getters, setters, toString, etc. automáticamente
@NoArgsConstructor                                         // Constructor vacío obligatorio para JPA
@AllArgsConstructor                                        // Constructor con todos los campos para desarrollo
public class Pelea {

    @Id                                                    // Llave Primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)    // ID autoincrementable
    private Long id;

    @NotNull(message = "El torneo es obligatorio")         // Valida que la pelea esté vinculada a un torneo
    @ManyToOne                                             // Relación Muchos a Uno: Muchas peleas pertenecen a UN torneo
    @JoinColumn(name = "torneo_id", nullable = false)      // Clave foránea (FK) apuntando a la tabla torneos
    private Torneo torneo;

    @NotNull(message = "La categoría es obligatoria")      // Valida que la pelea corresponda a una categoría
    @ManyToOne                                             // Relación Muchos a Uno: Muchas peleas pertenecen a UNA categoría
    @JoinColumn(name = "categoria_id", nullable = false)   // Clave foránea (FK) apuntando a la tabla categorias
    private Categoria categoria;

    @ManyToOne                                             // Relación Muchos a Uno: Muchas peleas pueden tener al mismo Competidor 1
    @JoinColumn(name = "competidor_1_id")                  // Clave foránea (FK) apuntando a Competidores para el participante 1
    private Competidor competidor1;

    @ManyToOne                                             // Relación Muchos a Uno: Muchas peleas pueden tener al mismo Competidor 2
    @JoinColumn(name = "competidor_2_id")                  // Clave foránea (FK) apuntando a Competidores para el participante 2
    private Competidor competidor2;

    @ManyToOne                                             // Relación Muchos a Uno: Muchos combates pueden tener un ganador común
    @JoinColumn(name = "ganador_id")                       // Clave foránea (FK) apuntando al Competidor que ganó la pelea
    private Competidor ganador;

    @PositiveOrZero(message = "La ronda debe ser positiva o cero") // Valida que la ronda sea 0 (final), 1, 2, etc.
    @Column(name = "ronda", nullable = false)              // Mapea la ronda en la BD
    private int ronda;
}
