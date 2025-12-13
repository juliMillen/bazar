package com.jm.bazar.Service;

import com.jm.bazar.DTO.ProductoDTO;
import com.jm.bazar.Repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private IProductoRepository productoRepository;

    public List<ProductoDTO> obtenerProductos(){
        return null;
    }
}
