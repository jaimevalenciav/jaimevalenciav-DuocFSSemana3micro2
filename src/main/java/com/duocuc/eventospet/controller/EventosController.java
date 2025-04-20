package com.duocuc.eventospet.controller;

import com.duocuc.eventospet.model.Eventos;
import com.duocuc.eventospet.model.ResponseWrapper;
import com.duocuc.eventospet.service.EventosService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventosController {
    
    private final EventosService eventoService;
    
    public EventosController(EventosService eventoService){
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<?> allEventos(){        
        List<Eventos> eventos = eventoService.allEventos();
        if(eventos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("En este momento no hay eventos registradas en el sistema.");           
        }

        ResponseWrapper<Eventos> respuesta = new ResponseWrapper<>(
            "OK",
            eventos.size(),
            eventos);
        return ResponseEntity.ok(respuesta);        
    }

    @GetMapping("/{id}")
    public Eventos buscarPorId(@PathVariable Long id){
        return eventoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Eventos>> guardarEvento(@Valid @RequestBody Eventos eventos){
        Eventos insertada = eventoService.guardaEvento(eventos);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>("Evento guardado satisfactoriamente.", 1, List.of(insertada)));        
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Eventos>> actualizar(@PathVariable Long id,
        @Valid @RequestBody Eventos eventosUpated){
            Eventos updated = eventoService.actualizar(id, eventosUpated);
            return ResponseEntity.ok(
                new ResponseWrapper<>("Evento se ha actualizado satisfactoriamente.", 1, List.of(updated)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Eventos>> eliminarEvento(@PathVariable Long id){
        eventoService.deleted(id);
        return ResponseEntity.ok(
            new ResponseWrapper<>("Pelicula eliminada satisfactoriamente.", 1, null)
        );
    }
    
    
}
