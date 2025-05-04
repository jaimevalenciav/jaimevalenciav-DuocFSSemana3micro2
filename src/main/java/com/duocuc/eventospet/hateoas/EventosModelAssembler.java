package com.duocuc.eventospet.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.duocuc.eventospet.controller.EventosController;
import com.duocuc.eventospet.model.Eventos;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class EventosModelAssembler implements RepresentationModelAssembler<Eventos, EntityModel<Eventos>>{
    @Override
    public EntityModel<Eventos> toModel(Eventos eventos)
    {
        return EntityModel.of(
            eventos,

            linkTo(methodOn(EventosController.class)
                .buscarPorId(eventos.getId()))
                .withSelfRel(),

            linkTo(methodOn(EventosController.class)
                .eliminarEvento(eventos.getId()))
                .withRel("delete"),
            
            linkTo(methodOn(EventosController.class)
                .actualizar(eventos.getId(), new Eventos()))
                .withRel("update"),
            
            linkTo(methodOn(EventosController.class)
                .allEventos())
                .withRel("all")
        );
    }
}
