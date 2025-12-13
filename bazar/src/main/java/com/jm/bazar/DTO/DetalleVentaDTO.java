package com.jm.bazar.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleVentaDTO {

    private Long idDetalleVenta;

    private double precioUnitario;

    private String nombreProducto;

    private int cantidadProducto;

    private double precioTotal;

}
