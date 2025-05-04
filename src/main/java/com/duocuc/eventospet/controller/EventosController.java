package com.duocuc.eventospet.controller;

import com.duocuc.eventospet.model.Eventos;
import com.duocuc.eventospet.model.ResponseWrapper;
import com.duocuc.eventospet.service.EventosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/eventos")
public class EventosController {
    
    @Autowired
    private EventosService eventoService;
    
    @GetMapping
    public ResponseEntity<?> allEventos(){        
        List<Eventos> eventos = eventoService.allEventos();
        if(eventos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("En este momento no hay eventos registrados en el sistema.");           
        }

        // Agrega todos los links HATEOAS a cada evento
        eventos.forEach(evento -> {
            Long id = evento.getId();
            // Self link
            Link selfLink = linkTo(methodOn(EventosController.class).buscarPorId(id)).withSelfRel();
            // All eventos link
            Link allEventosLink = linkTo(methodOn(EventosController.class).allEventos()).withRel("todos-eventos");
            // Update link
            Link updateLink = linkTo(methodOn(EventosController.class).actualizar(id, null)).withRel("actualizar");
            // Delete link
            Link deleteLink = linkTo(methodOn(EventosController.class).eliminarEvento(id)).withRel("eliminar");
            
            evento.add(selfLink);
            evento.add(allEventosLink);
            evento.add(updateLink);
            evento.add(deleteLink);
        });

        ResponseWrapper<Eventos> respuesta = new ResponseWrapper<>(
            "OK",
            eventos.size(),
            eventos);
            
        // Agrega links al wrapper
        respuesta.add(linkTo(methodOn(EventosController.class).allEventos()).withSelfRel());
        respuesta.add(linkTo(methodOn(EventosController.class).guardarEvento(null)).withRel("crear-nuevo"));
        
        return ResponseEntity.ok(respuesta);        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eventos> buscarPorId(@PathVariable Long id){
        Eventos evento = eventoService.buscarPorId(id);
        
        // Agrega todos los links relevantes
        Link selfLink = linkTo(methodOn(EventosController.class).buscarPorId(id)).withSelfRel();
        Link allEventosLink = linkTo(methodOn(EventosController.class).allEventos()).withRel("todos-eventos");
        Link updateLink = linkTo(methodOn(EventosController.class).actualizar(id, null)).withRel("actualizar");
        Link deleteLink = linkTo(methodOn(EventosController.class).eliminarEvento(id)).withRel("eliminar");
        
        evento.add(selfLink);
        evento.add(allEventosLink);
        evento.add(updateLink);
        evento.add(deleteLink);
        
        return ResponseEntity.ok(evento);
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Eventos>> guardarEvento(@Valid @RequestBody Eventos eventos){
        Eventos insertada = eventoService.guardaEvento(eventos);
        
        // Agrega todos los links al nuevo evento
        Long id = insertada.getId();
        Link selfLink = linkTo(methodOn(EventosController.class).buscarPorId(id)).withSelfRel();
        Link allEventosLink = linkTo(methodOn(EventosController.class).allEventos()).withRel("todos-eventos");
        Link updateLink = linkTo(methodOn(EventosController.class).actualizar(id, null)).withRel("actualizar");
        Link deleteLink = linkTo(methodOn(EventosController.class).eliminarEvento(id)).withRel("eliminar");
        
        insertada.add(selfLink);
        insertada.add(allEventosLink);
        insertada.add(updateLink);
        insertada.add(deleteLink);

        // Crea la respuesta con links
        ResponseWrapper<Eventos> respuesta = new ResponseWrapper<>(
            "Evento guardado satisfactoriamente.", 
            1, 
            List.of(insertada));
            
        respuesta.add(linkTo(methodOn(EventosController.class).allEventos()).withRel("todos-eventos"));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);        
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Eventos>> actualizar(@PathVariable Long id,
        @Valid @RequestBody Eventos eventosUpated){
            Eventos updated = eventoService.actualizar(id, eventosUpated);
            
            // Agrega todos los links al evento actualizado
            Link selfLink = linkTo(methodOn(EventosController.class).buscarPorId(id)).withSelfRel();
            Link allEventosLink = linkTo(methodOn(EventosController.class).allEventos()).withRel("todos-eventos");
            Link updateLink = linkTo(methodOn(EventosController.class).actualizar(id, null)).withRel("actualizar");
            Link deleteLink = linkTo(methodOn(EventosController.class).eliminarEvento(id)).withRel("eliminar");
            
            updated.add(selfLink);
            updated.add(allEventosLink);
            updated.add(updateLink);
            updated.add(deleteLink);

            // Crea la respuesta con links
            ResponseWrapper<Eventos> respuesta = new ResponseWrapper<>(
                "Evento actualizado satisfactoriamente.", 
                1, 
                List.of(updated));
                
            respuesta.add(linkTo(methodOn(EventosController.class).allEventos()).withRel("todos-eventos"));
            
            return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Eventos>> eliminarEvento(@PathVariable Long id){
        eventoService.deleted(id);
        
        // Crea un objeto de respuesta con links
        ResponseWrapper<Eventos> respuesta = new ResponseWrapper<>(
            "Evento eliminado satisfactoriamente.", 
            1, 
            null);
            
        respuesta.add(linkTo(methodOn(EventosController.class).allEventos()).withSelfRel());
        respuesta.add(linkTo(methodOn(EventosController.class).guardarEvento(null)).withRel("crear-nuevo"));
        
        return ResponseEntity.ok(respuesta);
    }
}