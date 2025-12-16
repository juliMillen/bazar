package com.jm.bazar.Controller;

import com.jm.bazar.DTO.MayorVentaDTO;
import com.jm.bazar.DTO.ProductoDTO;
import com.jm.bazar.DTO.ResumenVentasXDiaDTO;
import com.jm.bazar.DTO.VentaDTO;
import com.jm.bazar.Service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping("")
    public ResponseEntity<List<VentaDTO>> listarVentas(){
        List<VentaDTO> listaDTO = ventaService.listarVentas();
        return new ResponseEntity<>(listaDTO,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> obtenerVentaPorId(@PathVariable Long id){
        return new ResponseEntity<>(ventaService.obtenerVentaPorId(id),HttpStatus.OK);
    }

    @GetMapping("/{id}/productos")
    public ResponseEntity<List<ProductoDTO>> listarProductosPorVenta(@PathVariable Long id){
        return new ResponseEntity<>(ventaService.listarProductosXVenta(id),HttpStatus.OK);
    }

    @GetMapping("/resumen/{fecha}")
    public ResponseEntity<ResumenVentasXDiaDTO> obtenerResumenVentasXDia(@PathVariable LocalDate fecha){
        return new ResponseEntity<>(ventaService.obtenerDatosPorDia(fecha),HttpStatus.OK);
    }

    @GetMapping("/mayorVenta")
    public ResponseEntity<MayorVentaDTO> obtenerMayorVenta(){
        return new ResponseEntity<>(ventaService.obtenerMayorVenta(),HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<VentaDTO> crearVenta(@RequestBody VentaDTO v){
        return new ResponseEntity<>(ventaService.crearVenta(v),HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<VentaDTO> actualizarVenta(@PathVariable Long id,@RequestBody VentaDTO v){
        VentaDTO aActualizar = ventaService.actualizarVenta(id,v);
        return new ResponseEntity<>(aActualizar,HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<String> eliminarVenta(@PathVariable Long id){
        ventaService.eliminarVenta(id);
        return new ResponseEntity<>("Venta eliminada correctamente", HttpStatus.NO_CONTENT);
    }
}
