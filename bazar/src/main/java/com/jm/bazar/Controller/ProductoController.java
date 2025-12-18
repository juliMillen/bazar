package com.jm.bazar.Controller;

import com.jm.bazar.DTO.ProductoDTO;
import com.jm.bazar.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public ResponseEntity<List<ProductoDTO>> listaProductos(){
        List<ProductoDTO> listaDTO = productoService.obtenerProductos();
        return ResponseEntity.ok().body(listaDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProductoPorId(@PathVariable Long id){

        ProductoDTO productoDTO = productoService.obtenerProductoPorId(id);
        return new ResponseEntity<>(productoDTO, HttpStatus.OK);
    }

    @GetMapping("/stock/{stockDisponible}")
    public List<ProductoDTO> listarProductosXStock(@PathVariable int stockDisponible){
        List<ProductoDTO> listaDTO = productoService.obtenerProductoPorStock(stockDisponible);
        if(listaDTO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return listaDTO;
    }

    @PostMapping("/crear")
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO){
        return  new ResponseEntity<>(productoService.crearProducto(productoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id,@RequestBody ProductoDTO productoDTO){
        ProductoDTO modificado = productoService.modificarProducto(id, productoDTO);
        return new ResponseEntity<>(modificado, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id){
        productoService.eliminarProducto(id);
        return new ResponseEntity<>("Producto eliminado correctamente", HttpStatus.OK);
    }
}
