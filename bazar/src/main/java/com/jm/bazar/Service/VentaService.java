package com.jm.bazar.Service;

import com.jm.bazar.DTO.*;
import com.jm.bazar.Entity.Cliente;
import com.jm.bazar.Entity.DetalleVenta;
import com.jm.bazar.Entity.Producto;
import com.jm.bazar.Entity.Venta;
import com.jm.bazar.Mapper.Mapper;
import com.jm.bazar.Repository.IClienteRepository;
import com.jm.bazar.Repository.IProductoRepository;
import com.jm.bazar.Repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private IVentaRepository ventaRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    public List<VentaDTO> listarVentas(){
        List<Venta> listaVentas = ventaRepository.findAll();
        List<VentaDTO> listaVentaDTO = new ArrayList<>();
        VentaDTO ventaDTO;
        for(Venta vent : listaVentas){
            ventaDTO = Mapper.mapToDTO(vent);
            listaVentaDTO.add(ventaDTO);
        }
        return listaVentaDTO;
    }

    public VentaDTO obtenerVentaPorId(Long id){
        VentaDTO ventaDTO = Mapper.mapToDTO(ventaRepository.findById(id).orElseThrow( () -> new RuntimeException( "Venta no encontrada" ) ) );
        return ventaDTO;
    }

    public List<ProductoDTO> listarProductosXVenta(Long id){
        Venta venta = ventaRepository.findById(id).orElseThrow( () -> new RuntimeException( "Venta no encontrada" ) );

        return venta.getListaDetalles().stream()
                .map(detalle -> Mapper.mapToDTO(detalle.getProducto()))
                .toList();
    }

    public ResumenVentasXDiaDTO obtenerDatosPorDia(LocalDate fecha){
        List<Venta> ventas = ventaRepository.findByFechaVenta(fecha);

        if(ventas.isEmpty()){
            throw new RuntimeException("No hay ventas registradas en esa fecha");
        }

        double montoTotal = 0;
        int cantVentas = 0;
        for(Venta vent : ventas){
            montoTotal += vent.getTotal();
            cantVentas++;
        }
        ResumenVentasXDiaDTO resumen = new ResumenVentasXDiaDTO();
        resumen.setFecha(fecha);
        resumen.setMontoTotal(montoTotal);
        resumen.setCantVentas(cantVentas);
        return resumen;
    }

    public MayorVentaDTO obtenerMayorVenta(){
        List<Venta> ventas = ventaRepository.findAll();
        if(ventas.isEmpty()){
            throw new RuntimeException("No hay ventas registradas");
        }

        Venta mayorVenta = ventas.get(0);

        for(Venta vent : ventas){
            if(vent.getTotal() > mayorVenta.getTotal()){
                mayorVenta = vent;
            }
        }
        MayorVentaDTO mayorVentaDTO = new MayorVentaDTO();
        mayorVentaDTO.setCodigoVenta( mayorVenta.getCodigoVenta() );
        mayorVentaDTO.setTotal( mayorVenta.getTotal() );
        mayorVentaDTO.setCantProductos(mayorVenta.getListaDetalles().size());
        mayorVentaDTO.setNombreCliente(mayorVenta.getUnCliente().getNombre());
        mayorVentaDTO.setApellidoCliente(mayorVenta.getUnCliente().getApellido() );
        return mayorVentaDTO;
    }

    public VentaDTO crearVenta(VentaDTO ventaDTO){
        if(ventaDTO == null){
            throw new RuntimeException( "Venta es null" );
        }

        if(ventaDTO.getIdCliente() == null){
            throw new RuntimeException( "Identificacion no valida" );
        }

        if(ventaDTO.getListaDTO() == null || ventaDTO.getListaDTO().isEmpty()){
            throw new RuntimeException( "Lista no valida o vacia" );
        }

        //Buscamos el cliente

        Cliente cliente= clienteRepository.findById(ventaDTO.getIdCliente()).orElseThrow( () -> new RuntimeException( "Cliente no encontrado" ) );
        cliente.setIdCliente(ventaDTO.getIdCliente());

        //crear la venta
        Venta nuevaVenta = new Venta();
        nuevaVenta.setFechaVenta(ventaDTO.getFechaVenta());
        nuevaVenta.setTotal(ventaDTO.getTotal());
        nuevaVenta.setUnCliente(cliente);

        //listado de detalles

        List<DetalleVenta> detalles = new ArrayList<>();
        Double total = 0.0;

        for(DetalleVentaDTO detDTO : ventaDTO.getListaDTO()){

            Producto prod = productoRepository.findByNombre(detDTO.getNombreProducto()).orElseThrow( () -> new RuntimeException("Producto no Encontrado"));

            if(detDTO.getCantidadProducto() > prod.getCantDisponible()){
                throw new RuntimeException( "Cantidad no disponible" );
            }
            DetalleVenta det = new DetalleVenta();
            det.setProducto(prod);
            det.setCantidad(detDTO.getCantidadProducto());
            det.setPrecioUnitario(detDTO.getPrecioUnitario());
            det.setVenta(nuevaVenta);
            detalles.add(det);

            //calcular total

            total = total + (detDTO.getPrecioUnitario() * detDTO.getCantidadProducto());
        }

        //setear lista de detalles
        nuevaVenta.setListaDetalles(detalles);

        //guardo en la BD

        nuevaVenta = ventaRepository.save(nuevaVenta);

        //mapper para mostrar la salida

        VentaDTO nuevaVentaDTO = Mapper.mapToDTO(nuevaVenta);
        return nuevaVentaDTO;
    }

    public VentaDTO actualizarVenta(Long id,VentaDTO ventaDTO){
        Venta buscada = ventaRepository.findById(id).orElseThrow( () -> new RuntimeException( "Venta no encontrada" ) );

        if(ventaDTO.getFechaVenta() != null && ventaDTO.getTotal()>0 && ventaDTO.getIdCliente() != null){
            buscada.setFechaVenta(ventaDTO.getFechaVenta());
            buscada.setTotal(ventaDTO.getTotal());


            Cliente cliente = clienteRepository.findById(ventaDTO.getIdCliente()).orElseThrow( () -> new RuntimeException( "Cliente no encontrado" ) );
            buscada.setUnCliente(cliente);
        }

        //guardo en la BD
        ventaRepository.save(buscada);

        //Mapeo para mostrar la salida
        VentaDTO nuevaVentaDTO = Mapper.mapToDTO(buscada);
        return nuevaVentaDTO;
    }

    public void eliminarVenta(Long id){
        Venta aEliminar = ventaRepository.findById(id).orElseThrow( () -> new RuntimeException( "Venta no encontrada" ) );
        ventaRepository.delete(aEliminar);
    }
}
