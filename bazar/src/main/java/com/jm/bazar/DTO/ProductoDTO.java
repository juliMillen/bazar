package com.jm.bazar.DTO;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDTO {

    private Long codigoProd;

    private String nombre;

    private String marca;

    private double costo;

    private int cantDisponible;
}
