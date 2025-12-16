package com.jm.bazar.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MayorVentaDTO {

    private Long codigoVenta;

    private double total;

    private int cantProductos;

    private String nombreCliente;

    private String apellidoCliente;
}
