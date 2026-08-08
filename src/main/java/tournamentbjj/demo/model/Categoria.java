package tournamentbjj.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity // Define la clase como una tabla en la BD
@Table(name = "categorias") // Nombre de la tabla en SQL
@Data // Genera getters, setters, toString, etc. automáticamente
@NoArgsConstructor // Constructor vacío obligatorio para JPA
@AllArgsConstructor // Constructor con todos los campos para desarrollo
public class Categoria {

    @Id // Marca el campo como Llave Primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincrementable en la BD
    private Long id;

    @Positive(message = "Edad mínima inválida") // Valida que el rango de edad inicie en un número positivo
    @Column(name = "edad_minima", nullable = false) // Mapea la edad mínima en BD
    private int edadMinima;

    @Positive(message = "Edad máxima inválida") // Valida que la edad máxima sea un número positivo
    @Column(name = "edad_maxima", nullable = false) // Mapea la edad máxima en BD
    private int edadMaxima;

    @Positive(message = "Peso máximo inválido") // Valida que el peso de la categoría sea mayor a cero
    @Column(name = "peso_maximo_kg", nullable = false) // Mapea el límite de peso en la BD (ej. 80.5 kg)
    private double pesoMaximo;

    @Enumerated(EnumType.STRING) // Guarda el color del cinturón como texto (ej. "AZUL") en BD
    @Column(name = "color_cinta", nullable = false) // Mapea la columna de la cinta en la BD
    private BeltColor colorCinta;

    @NotBlank(message = "Género obligatorio") // Evita que el género llegue vacío (Femenino/Masculino)
    @Column(name = "genero", nullable = false) // Mapea el género en la BD
    private String genero;
}
