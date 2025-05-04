package com.duocuc.eventospet;
import com.duocuc.eventospet.exception.EventosNotFoundException;
import com.duocuc.eventospet.model.Eventos;
import com.duocuc.eventospet.repository.EventosRepository;
import com.duocuc.eventospet.service.EventosService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.Sort;

public class EventosServiceTest {
    private EventosRepository eventosRepository;
    private EventosService eventosService;

    @BeforeEach
    public void setUp(){
        eventosRepository = mock(EventosRepository.class);
        eventosService = new EventosService(eventosRepository);
    }

    @Test
    public void testObtenerTodos(){
        LocalDate fecha1 = LocalDate.of(2025, 6, 2);
        LocalDate fecha2 = LocalDate.of(2025, 9, 11);

        Eventos e1 = new Eventos(null, "Nombre Evento 1","Chillan",
        "Chile", fecha1,"Carrera","Criaderos");
        Eventos e2 = new Eventos(null, "Nombre Evento 2","Chillan",
        "Chile", fecha2,"Exhibición","Criaderos");

        when(eventosRepository.findAll(ArgumentMatchers.any(Sort.class))).thenReturn(Arrays.asList(e1, e2));
        when(eventosRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Eventos> resultado = eventosService.allEventos();

        assertEquals(2, resultado.size());
        assertEquals("Carrera", resultado.get(0).getTipoEvento());

    }

    @Test
    public void testObtenerPorId_existente(){
        LocalDate fecha3 = LocalDate.of(2025, 11, 21);

        Eventos eventos = new Eventos(16L, "Nombre Evento 3","Santiago",
        "Chile", fecha3,"Genetica","Veterinarios");
        when(eventosRepository.findById(16L)).thenReturn(Optional.of(eventos));

        Eventos resultado = eventosService.buscarPorId(16L);
        assertEquals("Genetica", resultado.getTipoEvento());
        assertEquals("Veterinarios", resultado.getParticipantes());
    }

    @Test
    public void testObtenerPorId_noExistente(){
        when(eventosRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EventosNotFoundException.class, () -> {
            eventosService.buscarPorId(99L);
        });
    }

}
