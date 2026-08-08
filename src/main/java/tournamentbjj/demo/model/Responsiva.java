package tournamentbjj.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity                                                    // Define la clase como tabla en la BD
@Table(name = "responsivas")                               // Nombre de la tabla SQL
@Data                                                      // Genera getters, setters, toString, etc.
@NoArgsConstructor                                         // Constructor vacío para JPA
@AllArgsConstructor                                        // Constructor con todos los campos
public class Responsiva {

    @Id                                                    // Llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)    // ID autoincrementable
    private Long id;

    @AssertTrue(message = "Debe aceptar la exención de responsabilidad para competir") // Obliga a que sea true
    @Column(name = "acepta_riesgos", nullable = false)     // Guarda en BD si aceptó (true/false)
    private boolean aceptaRiesgos;

    @Column(name = "acepta_materia_muerte", nullable = false) // Confirmación explícita de riesgo de muerte/lesión grave
    private boolean aceptaRiesgoMuerte;

    @NotBlank(message = "Nombre del firmante obligatorio") // Quien firma (competidor o tutor si es menor)
    @Column(name = "nombre_firmante", nullable = false)    // Nombre completo legal del firmante
    private String nombreFirmante;

    @NotBlank(message = "Documento de identidad obligatorio") // DNI, CURP, Pasaporte o Identificación
    @Column(name = "documento_identidad", nullable = false) // Número de documento del firmante
    private String documentoIdentidad;

    @Column(name = "es_tutor_legal", nullable = false)    // Indica si quien firma es el tutor (para menores de 18)
    private boolean esTutorLegal;

    @Column(name = "fecha_firma", nullable = false)        // Fecha y hora exacta en que firmó
    private LocalDateTime fechaFirma;

    @Column(name = "direccion_ip")                         // IP desde donde aceptó (evidencia digital)
    private String direccionIp;

    @OneToOne                                              // Relación 1 a 1: Una responsiva pertenece a UN competidor
    @JoinColumn(name = "competidor_id", nullable = false)  // Crea la llave foránea (FK) apuntando a Competidor
    private Competidor competidor;
}
