package com.duocuc.eventospet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.duocuc.eventospet.model.Eventos;

public interface EventosRepository extends JpaRepository<Eventos, Long>{
    Eventos findByNombreEvento(String nombreEvento);
}
