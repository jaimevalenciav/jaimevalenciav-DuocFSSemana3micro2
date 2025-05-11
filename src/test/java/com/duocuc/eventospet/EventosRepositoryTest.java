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
    
    @Test
    public void testBuscarPorNombre() {
        // Preparación
        Eventos evento = new Eventos();
        evento.setNombreEvento("Exposición Canina");
        evento.setCiudad("Valparaíso");
        evento.setPais("Chile");
        evento.setFechaEvento(LocalDate.now().plusDays(7));
        evento.setTipoEvento("Exposición");
        evento.setParticipantes("Pastor Alemán, Golden Retriever, Dálmata");
        
        eventosRepository.save(evento);
        
        // Ejecución
        Eventos eventoBuscado = eventosRepository.findByNombreEvento("Exposición Canina");
        
        // Verificación
        assertThat(eventoBuscado).isNotNull();
        assertThat(eventoBuscado.getCiudad()).isEqualTo("Valparaíso");
        assertThat(eventoBuscado.getParticipantes()).contains("Golden Retriever");
    }
    
    @Test
    public void testActualizarEvento() {
        // Preparación
        Eventos evento = new Eventos();
        evento.setNombreEvento("Carrera de Mascotas");
        evento.setCiudad("Concepción");
        evento.setPais("Chile");
        evento.setFechaEvento(LocalDate.now().plusDays(14));
        evento.setTipoEvento("Deportivo");
        evento.setParticipantes("Galgos, Labradores");
        
        eventosRepository.save(evento);
        
        // Ejecución - Modificar el evento guardado
        Eventos eventoGuardado = eventosRepository.findById(evento.getId()).orElse(null);
        assertThat(eventoGuardado).isNotNull();
        
        eventoGuardado.setCiudad("Viña del Mar");
        eventoGuardado.setParticipantes("Galgos, Labradores, Beagles");
        eventosRepository.save(eventoGuardado);
        
        // Verificación
        Eventos eventoActualizado = eventosRepository.findById(evento.getId()).orElse(null);
        assertThat(eventoActualizado).isNotNull();
        assertThat(eventoActualizado.getCiudad()).isEqualTo("Viña del Mar");
        assertThat(eventoActualizado.getParticipantes()).contains("Beagles");
    }
    
    @Test
    public void testEliminarEvento() {
        // Preparación
        Eventos evento = new Eventos();
        evento.setNombreEvento("Taller de Adiestramiento");
        evento.setCiudad("La Serena");
        evento.setPais("Chile");
        evento.setFechaEvento(LocalDate.now().plusDays(30));
        evento.setTipoEvento("Educativo");
        evento.setParticipantes("Todos los perros");
        
        eventosRepository.save(evento);
        Long eventoId = evento.getId();
        
        // Verificar que se haya guardado correctamente
        assertThat(eventosRepository.findById(eventoId)).isPresent();
        
        // Ejecución
        eventosRepository.deleteById(eventoId);
        
        // Verificación
        assertThat(eventosRepository.findById(eventoId)).isEmpty();
    }
}