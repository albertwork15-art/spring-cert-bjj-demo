package tournamentbjj.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import tournamentbjj.demo.model.Competidor;
import tournamentbjj.demo.model.BeltColor;
import tournamentbjj.demo.repository.CompetidorRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// =========================================================================
// 🎯 CONCEPTOS CLAVE DEL EXAMEN (SPRING BOOT TESTING - INTEGRATION TESTS)
// 1. @SpringBootTest: Carga el contexto de aplicación completo de Spring Boot.
//    - Se usa para pruebas de integración de punta a punta.
// 2. @AutoConfigureMockMvc: Configura automáticamente la instancia de MockMvc.
// =========================================================================
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    properties = "spring.main.web-application-type=servlet"
)
@AutoConfigureMockMvc
public class CompetidorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // =========================================================================
    // 🎯 CONCEPTO DEL EXAMEN: @MockitoBean (Mocking de dependencias del Contexto)
    // - En Spring Boot 3.4/4.0+, @MockBean fue reemplazado por @MockitoBean.
    // - Registra un Mock de la interfaz en el contexto de Spring. 
    // - Este Mock será inyectado automáticamente en el CompetidorController.
    // =========================================================================
    @MockitoBean
    private CompetidorRepository competidorRepository;

    // =========================================================================
    // 🎯 CONCEPTO DEL EXAMEN: Pruebas con Seguridad (@WithMockUser)
    // - @WithMockUser: Simula un usuario autenticado para pasar el filtro de seguridad.
    // - Si no lo pones, la prueba de seguridad fallará con HTTP 401 Unauthorized.
    // =========================================================================
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"}) // Simula un usuario autenticado común
    public void obtenerTodos_deberiaRetornarListaDeCompetidores() throws Exception {
        // Configuramos el Mockito para simular la respuesta del repositorio
        Competidor c1 = new Competidor(1L, "Gordon Ryan", BeltColor.NEGRO, "Renzo Gracie", 99, 29, "Masculino", null, null);
        when(competidorRepository.findAll()).thenReturn(Arrays.asList(c1));

        // Ejecutamos la petición GET simulada y validamos la respuesta JSON
        mockMvc.perform(get("/api/competidores")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Esperamos HTTP 200 OK
                .andExpect(jsonPath("$[0].name").value("Gordon Ryan")) // Validamos que el nombre en el JSON coincida
                .andExpect(jsonPath("$[0].beltColor").value("NEGRO"));
    }

    @Test
    @WithMockUser(username = "adminuser", roles = {"ADMIN"}) // Simula un usuario administrador
    public void crearCompetidor_deberiaGuardarCorrectamenteSiEsAdmin() throws Exception {
        Competidor nuevo = new Competidor(null, "Mikey Musumeci", BeltColor.NEGRO, "Cobrinha", 61, 28, "Masculino", null, null);
        Competidor guardado = new Competidor(2L, "Mikey Musumeci", BeltColor.NEGRO, "Cobrinha", 61, 28, "Masculino", null, null);

        when(competidorRepository.save(nuevo)).thenReturn(guardado);

        // Simulamos un POST para crear el competidor enviando un cuerpo JSON
        mockMvc.perform(post("/api/competidores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Mikey Musumeci\",\"beltColor\":\"NEGRO\",\"equipo\":\"Cobrinha\",\"peso\":61,\"edad\":28,\"genero\":\"Masculino\"}"))
                .andExpect(status().isCreated()); // Esperamos HTTP 201 Created (porque lo crea el Admin)
    }

    @Test
    @WithMockUser(username = "regularuser", roles = {"USER"}) // Simula un usuario común sin rol ADMIN
    public void crearCompetidor_deberiaDarErrorForbiddenSiNoEsAdmin() throws Exception {
        // Ejecutamos un POST intentando crear, pero al no ser ADMIN, Spring Security debe bloquearlo
        mockMvc.perform(post("/api/competidores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Mikey Musumeci\",\"beltColor\":\"NEGRO\",\"equipo\":\"Cobrinha\",\"peso\":61,\"edad\":28,\"genero\":\"Masculino\"}"))
                .andExpect(status().isForbidden()); // Esperamos HTTP 403 Forbidden (Acceso denegado)
    }
}
