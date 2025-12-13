package com.jm.bazar.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO {

    private Long idCliente;

    private String nombre;

    private String apellido;

    private String dni;
}
