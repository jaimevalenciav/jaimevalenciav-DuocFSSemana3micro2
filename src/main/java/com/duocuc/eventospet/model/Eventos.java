package com.duocuc.eventospet.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="eventos")
public class Eventos {

    @Id
    @NotNull(message="El Id del evento no puede ser nulo.")
    private Long id;

    @NotBlank(message = "El nombre del evento es obligatorio.")
    @Size(min = 3, max = 100, message = "El nombre del evento debe tener entre 3 y 100 caracteres.")    
    private String nombreEvento;

    @NotBlank(message = "El nombre de la ciudad es obligatoria.")
    @Size(min = 2, max = 50, message = "El nombre de la ciudad debe tener entre 2 y 50 caracteres.")    
    private String ciudad;

    @NotBlank(message = "El nombre del país es obligatorio.")
    @Size(min = 2, max = 50, message = "El nombre del país debe tener entre 2 y 50 caracteres.")  
    private String pais;
    
    
    @Future(message = "La fecha a ingresar debe ser mayor a la de hoy.")    
    private LocalDate fechaEvento;

    @NotBlank(message = "El tipo de evento es obligatorio")
    @Size(min = 3, max = 50, message = "El tipo de evento debe tener entre 3 y 50 caracteres.")
    private String tipoEvento;

    @NotBlank(message = "Los participantes son obligatorios.")
    @Size(min = 3, max = 255, message = "Los participantes deben tener entre 3 y 255 caracteres.")    
    private String participantes;

}
