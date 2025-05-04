package com.duocuc.eventospet;

import com.duocuc.eventospet.controller.EventosController;

import com.duocuc.eventospet.model.Eventos;
import com.duocuc.eventospet.service.EventosService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventosController.class)
public class EventosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventosService eventosService;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAllEventosReturnsOk() throws Exception {
        Eventos evento = new Eventos(
            1L,
            "Feria de Mascotas",
            "Santiago",
            "Chile",
            LocalDate.now().plusDays(10),
            "Exposición",
            "Veterinarios, Dueños de mascotas"
        );

        Mockito.when(eventosService.allEventos()).thenReturn(List.of(evento));

        mockMvc.perform(get("/eventos")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            // Ajusta los jsonPath para la estructura real:
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.cantidad").value(1))
            .andExpect(jsonPath("$.data[0].id").value(1)) // Accede a través de 'data'
            .andExpect(jsonPath("$.data[0].nombreEvento").value("Feria de Mascotas"));
    }

    @Test
    @WithMockUser(username = "jvalencia", roles = {"ADMIN"})
    public void testObtenerPorId() throws Exception {
        Eventos evento = new Eventos(
                1L,
                "Feria de serpientes",
                "Rancagua",
                "Chile",
                LocalDate.now().plusDays(66),
                "Feria",
                "Publico General"
        );

        Mockito.when(eventosService.buscarPorId(1L)).thenReturn(evento);

        mockMvc.perform(get("/eventos/1") // Ajusta la ruta según tu Controller
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreEvento").value("Feria de serpientes"));
    }
}