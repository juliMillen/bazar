package com.jm.bazar.DTO;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumenVentasXDiaDTO {
    private LocalDate fecha;

    private int cantVentas;

    private double montoTotal;
}
