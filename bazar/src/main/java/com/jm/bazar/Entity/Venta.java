package com.jm.bazar.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoVenta;

    private LocalDate fechaVenta;

    private double total;

    @ManyToOne
    @JoinColumn(name= "id_cliente")
    private Cliente unCliente;


    @OneToMany(mappedBy = "venta",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private List<DetalleVenta> listaDetalles = new ArrayList<>();
}
