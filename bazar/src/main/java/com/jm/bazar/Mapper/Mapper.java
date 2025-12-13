package com.jm.bazar.Mapper;

import com.jm.bazar.DTO.ClienteDTO;
import com.jm.bazar.DTO.DetalleVentaDTO;
import com.jm.bazar.DTO.ProductoDTO;
import com.jm.bazar.DTO.VentaDTO;
import com.jm.bazar.Entity.Cliente;
import com.jm.bazar.Entity.Producto;
import com.jm.bazar.Entity.Venta;

import java.util.stream.Collectors;

public class Mapper {

    //mapeo de Producto a ProductoDTO

    static public ProductoDTO mapToDTO(Producto p) {
        if (p == null) {
            throw new IllegalArgumentException("El producto es null");
        }
        return ProductoDTO.builder()
                .codigoProd(p.getCodigoProducto())
                .nombre(p.getNombre())
                .marca(p.getMarca())
                .costo(p.getCosto())
                .cantDisponible(p.getCantDisponible())
                .build();
    }


    //mapeo de Venta a VentaDTO

    static public VentaDTO mapToDTO(Venta v) {
        if (v == null) {
            throw new IllegalArgumentException("La venta es null");
        }

        var detalle = v.getListaDetalles().stream().map(det ->
                DetalleVentaDTO.builder()
                        .idDetalleVenta(det.getProducto().getCodigoProducto())
                        .nombreProducto(det.getProducto().getNombre())
                        .precioUnitario(det.getProducto().getCosto())
                        .cantidadProducto(det.getProducto().getCantDisponible())
                        .precioTotal(det.getPrecioUnitario() * det.getCantidad())
                        .build()
        ).collect(Collectors.toList());

        var total = detalle.stream()
                .map(DetalleVentaDTO::getPrecioTotal)
                .reduce(0.0, Double::sum);

        return VentaDTO.builder()
                .codigoVent(v.getCodigoVenta())
                .fechaVenta(v.getFechaVenta())
                .idCliente(v.getUnCliente().getIdCliente())
                .listaDTO(detalle)
                .total(total)
                .build();
    }


    //mapeo de Cliente a ClienteDTO

    static public ClienteDTO mapToDTO(Cliente c) {
        if (c == null) {
            throw new IllegalArgumentException("El cliente es null");
        }
        return ClienteDTO.builder()
                .idCliente(c.getIdCliente())
                .nombre(c.getNombre())
                .apellido(c.getApellido())
                .dni(c.getDni())
                .build();
    }
}
