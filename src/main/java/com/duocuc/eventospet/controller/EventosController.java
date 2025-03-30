package com.duocuc.eventospet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.duocuc.eventospet.EventospetApplication;
import com.duocuc.eventospet.model.Eventos;
import com.duocuc.eventospet.service.EventosService;

import jakarta.validation.Valid;

import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/eventos")
public class EventosController {

    
    private final EventosService eventoService;
    
    public EventosController(EventosService eventoService, EventospetApplication eventospetApplication){
        this.eventoService = eventoService;
        
    } 

    @GetMapping
    public List<Eventos> allEvents(){
        return eventoService.allEventos();
    }

    @GetMapping("/{id}")
    public Eventos eventoPorId(@PathVariable Long id){
        return eventoService.eventoPorId(id)
        .orElseThrow(()-> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "El numero de evento: "+ id +" no existe en la base de datos"
        ));
    }

    @PostMapping
    public Eventos nuevoEvento(@Valid @RequestBody Eventos evento){
        return eventoService.guardaEvento(evento);
    }

    @PutMapping("/evento/{id}")
    public Eventos actualizaEvento(@Valid @PathVariable Long id, @RequestBody Eventos evento) {
        return eventoService.actualizarEvento(id, evento);
       
    }
    
    
}
