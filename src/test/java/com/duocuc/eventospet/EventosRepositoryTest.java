package com.duocuc.eventospet;

import com.duocuc.eventospet.model.Eventos;
import com.duocuc.eventospet.repository.EventosRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test") 
public class EventosRepositoryTest {

    @Autowired
    private EventosRepository eventosRepository;

    @Test
    public void testGuardarYBuscar() {
        Eventos evento = new Eventos();
        evento.setNombreEvento("Concierto de Perros");
        evento.setCiudad("Santiago");
        evento.setPais("Chile");
        evento.setFechaEvento(LocalDate.now().plusDays(1));
        evento.setTipoEvento("Concierto");
        evento.setParticipantes("Bulldog, Beagle");

        eventosRepository.save(evento);

        assertThat(eventosRepository.findById(evento.getId())).isPresent();
    }
}
