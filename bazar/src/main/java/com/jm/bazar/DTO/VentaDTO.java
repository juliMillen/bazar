package com.jm.bazar.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaDTO {

    private Long codigoVent;

    private LocalDate fechaVenta;

    //datos del cliente
    private Long idCliente;

    //lista de detalles
    private List<DetalleVentaDTO> listaDTO;

    //total
    private double total;
}
