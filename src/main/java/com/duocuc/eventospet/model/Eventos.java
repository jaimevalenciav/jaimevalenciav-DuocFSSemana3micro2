package com.duocuc.eventospet.model;
import org.springframework.hateoas.RepresentationModel;
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
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="eventos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Eventos extends RepresentationModel<Eventos> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    
    @NotBlank(message = "El nombre del evento es obligatorio.")
    @Size(min = 3, max = 100, message = "El nombre del evento debe tener entre 3 y 100 caracteres.")
    @Column(name = "NOMBRE_EVENTO")    
    private String nombreEvento;

    
    @NotBlank(message = "El nombre de la ciudad es obligatoria.")
    @Size(min = 2, max = 50, message = "El nombre de la ciudad debe tener entre 2 y 50 caracteres.")
    @Column(name = "CIUDAD")    
    private String ciudad;

    
    @NotBlank(message = "El nombre del país es obligatorio.")
    @Size(min = 2, max = 50, message = "El nombre del país debe tener entre 2 y 50 caracteres.")
    @Column(name = "PAIS")  
    private String pais;
    
    
    @Future(message = "La fecha a ingresar debe ser mayor a la de hoy.")
    @Column(name = "FECHA_EVENTO")    
    private LocalDate fechaEvento;

    
    @NotBlank(message = "El tipo de evento es obligatorio")
    @Size(min = 3, max = 50, message = "El tipo de evento debe tener entre 3 y 50 caracteres.")
    @Column(name = "TIPO_EVENTO")
    private String tipoEvento;

    
    @NotBlank(message = "Los participantes son obligatorios.")
    @Size(min = 3, max = 255, message = "Los participantes deben tener entre 3 y 255 caracteres.")    
    @Column(name = "PARTICIPANTES")
    private String participantes;

}
