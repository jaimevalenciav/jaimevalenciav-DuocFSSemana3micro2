package com.duocuc.eventospet.service;
import com.duocuc.eventospet.model.Eventos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventosService {
    private final List<Eventos> eventos = new ArrayList<>();   

    public EventosService() {
        eventos.add(new Eventos(1L, "Competencia Raza Canina", "Rancagua", "Chile", LocalDate.of(2025, 11, 21), "Raza", "Jaime Valencia, Teresa Sánchez"));
        eventos.add(new Eventos(2L, "Exposición Felina Internacional", "Santiago", "Chile", LocalDate.of(2026, 3, 15), "Gatos", "María López, Carlos Pérez"));
        eventos.add(new Eventos(3L, "Concurso Agility Canino", "Valparaíso", "Chile", LocalDate.of(2025, 7, 10), "Agility", "Rodrigo Gómez, Ana Martínez"));
        eventos.add(new Eventos(4L, "Feria de Mascotas y Adopción", "Concepción", "Chile", LocalDate.of(2025, 9, 5), "General", "Felipe Rojas, Camila Silva"));
        eventos.add(new Eventos(5L, "Jornada de Entrenamiento Canino", "La Serena", "Chile", LocalDate.of(2026, 6, 12), "Entrenamiento", "José Torres, Daniela Fernández"));
        eventos.add(new Eventos(6L, "Competencia Nacional de Obediencia", "Antofagasta", "Chile", LocalDate.of(2025, 8, 20), "Obediencia", "Andrea Muñoz, Ignacio Vera"));
        eventos.add(new Eventos(7L, "Encuentro de Criadores de Perros", "Puerto Montt", "Chile", LocalDate.of(2026, 4, 18), "Criadores", "Eduardo Castro, Sonia Herrera"));
        eventos.add(new Eventos(8L, "Desfile de Moda Canina", "Viña del Mar", "Chile", LocalDate.of(2025, 10, 22), "Moda", "Francisca Álvarez, Pablo Méndez"));
        eventos.add(new Eventos(9L, "Seminario de Salud Animal", "Temuco", "Chile", LocalDate.of(2025, 11, 30), "Salud", "Martín Navarro, Isabel Soto"));
        eventos.add(new Eventos(10L, "Exhibición de Perros de Trabajo", "Iquique", "Chile", LocalDate.of(2026, 5, 8), "Trabajo", "Héctor Ramírez, Valentina Ortiz"));
    }

    public List<Eventos> allEventos() {
        return eventos;
    }

    public Optional<Eventos> eventoPorId(Long id){
        return eventos.stream()
        .filter(p->p.getId().equals(id))
        .findFirst();
    }

    public Eventos guardaEvento(Eventos evento){
        eventos.add(evento);
        return evento;
    }

    public Eventos actualizarEvento(Long id, Eventos eventoActualizado){
        Optional<Eventos> optionalEvento = eventoPorId(id);

        if (optionalEvento.isPresent()){
            Eventos eventoExistente = optionalEvento.get();

            eventoExistente.setNombreEvento(eventoActualizado.getNombreEvento());
            eventoExistente.setCiudad(eventoActualizado.getCiudad());
            eventoExistente.setPais(eventoActualizado.getPais());
            eventoExistente.setFechaEvento(eventoActualizado.getFechaEvento());
            eventoExistente.setTipoEvento(eventoActualizado.getTipoEvento());
            eventoExistente.setParticipantes(eventoActualizado.getParticipantes());
            
            return eventoExistente;

        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hubo un problema con el set de datos del evento.");
        }
    }
}
