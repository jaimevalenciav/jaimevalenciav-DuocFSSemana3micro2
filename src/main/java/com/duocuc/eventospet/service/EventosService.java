package com.duocuc.eventospet.service;
import com.duocuc.eventospet.model.Eventos;
import com.duocuc.eventospet.repository.EventosRepository;
import com.duocuc.eventospet.exception.EventosNotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventosService {
    @Autowired

    private EventosRepository eventos;

    public EventosService(EventosRepository eventos) {
        this.eventos = eventos;
    }
  
    public List<Eventos> allEventos() {
        return eventos.findAll(Sort.by("id"));
    }

    public Eventos buscarPorId(Long id){
        return eventos.findById(id)
        .orElseThrow(() -> new EventosNotFoundException(id));
    }

    public Eventos guardaEvento(Eventos evento){
        if(eventos.existsById(evento.getId())){
            throw new IllegalArgumentException("Ya existe el evento con el id: " + evento.getId());
        }
        return eventos.save(evento);       
    }

    public Eventos actualizar(Long id, Eventos eventosUpdated){
        Eventos existente = eventos.findById(id).orElseThrow(() -> new EventosNotFoundException(id));

        existente.setNombreEvento(eventosUpdated.getNombreEvento());
        existente.setCiudad(eventosUpdated.getCiudad());
        existente.setPais(eventosUpdated.getPais());
        existente.setFechaEvento(eventosUpdated.getFechaEvento());
        existente.setTipoEvento(eventosUpdated.getTipoEvento());
        existente.setParticipantes(eventosUpdated.getParticipantes());

        return eventos.save(existente);
    }

    public void deleted(Long id){
        Eventos existente = eventos.findById(id).orElseThrow(() -> new EventosNotFoundException(id));
        eventos.delete(existente);
    }
}
