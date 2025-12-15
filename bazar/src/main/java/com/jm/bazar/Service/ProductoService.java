package com.jm.bazar.Service;

import com.jm.bazar.DTO.ProductoDTO;
import com.jm.bazar.Entity.Producto;
import com.jm.bazar.Mapper.Mapper;
import com.jm.bazar.Repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private IProductoRepository productoRepository;

    public List<ProductoDTO> obtenerProductos(){
        return productoRepository.findAll().stream()
                .map(Mapper::mapToDTO)
                .toList();
    }

    public ProductoDTO obtenerProductoPorId(Long id){
        return Mapper.mapToDTO(productoRepository.findById(id).orElseThrow( () -> new RuntimeException("Producto no encontrado")) );
    }

    public ProductoDTO crearProducto(ProductoDTO productoDTO){
        Producto producto = Producto.builder()
                .nombre(productoDTO.getNombre())
                .costo(productoDTO.getCosto())
                .marca(productoDTO.getNombre())
                .cantDisponible(productoDTO.getCantDisponible())
                .build();
        return Mapper.mapToDTO(productoRepository.save(producto));
    }

    public ProductoDTO modificarProducto(Long id,ProductoDTO productoDTO){
        Producto buscado = productoRepository.findById(id).orElseThrow( () -> new RuntimeException("Producto no encontrado"));
        buscado.setNombre(productoDTO.getNombre());
        buscado.setCosto(productoDTO.getCosto());
        buscado.setMarca(productoDTO.getMarca());
        buscado.setCantDisponible(productoDTO.getCantDisponible());
        return Mapper.mapToDTO(productoRepository.save(buscado));
    }

    public void eliminarProducto(Long id){
        Producto aEliminar = productoRepository.findById(id).orElseThrow( () -> new RuntimeException("Producto no encontrado"));
        productoRepository.delete(aEliminar);
    }
}
