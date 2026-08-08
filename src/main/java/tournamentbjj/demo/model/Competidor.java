package tournamentbjj.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity // Define la clase como tabla de base de datos
@Table(name = "competidores") // Cambia el nombre de la tabla en SQL a "competidores"
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Constructor vacío (obligatorio para JPA)
@AllArgsConstructor // Constructor con todos los campos (útil para pruebas)
public class Competidor {

    @Id // Marca este campo como Llave Primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hace que el ID sea autoincrementable en la BD
    private Long id;

    @NotBlank(message = "Nombre obligatorio") // Evita cadenas nulas o con puros espacios
    @Column(name = "nombre", nullable = false) // Mapea la columna en BD y prohíbe nulos en SQL
    private String name;

    @Enumerated(EnumType.STRING) // Guarda el texto del Enum ("AZUL") y no su índice (1)
    @Column(name = "color_cinta", nullable = false) // Configura el nombre y restricción en BD
    private BeltColor beltColor;

    @NotBlank(message = "Equipo obligatorio") // Valida que el texto no llegue vacío
    @Column(name = "equipo", nullable = false) // Campo no nulo en base de datos
    private String equipo;

    @Positive(message = "Peso invalido") // Obliga a que sea un número mayor a cero
    @Column(name = "peso_kg", nullable = false) // Campo no nulo en base de datos
    private int peso;

    @Min(value = 14, message = "Edad mínima 14 años") // Define el valor mínimo permitido
    @Column(name = "edad", nullable = false) // Campo no nulo en base de datos
    private int edad;

    @NotBlank(message = "Genero obligatorio") // Valida que no esté vacío
    @Column(name = "genero", nullable = false) // Campo no nulo en base de datos
    private String genero;

    @Column(name = "categoria") // Mapea a la columna "categoria" (opcional)
    private String categoria;

    @OneToOne(mappedBy = "competidor", cascade = CascadeType.ALL) // Relación 1 a 1 enlazada con la propiedad 'competidor' de la otra clase
    private Responsiva responsiva;
}
