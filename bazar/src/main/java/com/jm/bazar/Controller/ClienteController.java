package com.jm.bazar.Controller;

import com.jm.bazar.DTO.ClienteDTO;
import com.jm.bazar.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteDTO>> obtenerClientes(){
        return ResponseEntity.ok(clienteService.obtenerClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerCliente(@PathVariable Long id){
        ClienteDTO clienteDTO = clienteService.obtenerCliente(id);
        return ResponseEntity.ok(clienteDTO);
    }

    @PostMapping("/crear")
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody ClienteDTO clienteDTO){
        ClienteDTO nuevo = clienteService.crearCliente(clienteDTO);
        return new  ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable Long id,@RequestBody ClienteDTO clienteDTO){
        ClienteDTO nuevo = clienteService.actualizarCliente(id,clienteDTO);
        return new  ResponseEntity<>(nuevo, HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id){
        clienteService.eliminarCliente(id);
        return new  ResponseEntity<>("Cliente eliminado correctamente",HttpStatus.NO_CONTENT);
    }



}
