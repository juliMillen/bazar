package com.jm.bazar.Service;

import com.jm.bazar.DTO.ClienteDTO;
import com.jm.bazar.Entity.Cliente;
import com.jm.bazar.Mapper.Mapper;
import com.jm.bazar.Repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    public List<ClienteDTO> obtenerClientes(){
        return clienteRepository.findAll().stream()
                .map(Mapper::mapToDTO)
                .toList();
    }

    public ClienteDTO obtenerCliente(Long id){
        return Mapper.mapToDTO(clienteRepository.findById(id).orElseThrow( () -> new RuntimeException("Cliente no encontrado") ));
    }


    public ClienteDTO crearCliente(ClienteDTO clienteDTO){
        Cliente nuevo = Cliente.builder()
                .nombre(clienteDTO.getNombre())
                .apellido(clienteDTO.getApellido())
                .dni(clienteDTO.getDni())
                .build();
        return  Mapper.mapToDTO(clienteRepository.save(nuevo));
    }

    public ClienteDTO actualizarCliente(Long id,ClienteDTO clienteDTO){
        Cliente aModificar = clienteRepository.findById(id).orElseThrow( () -> new RuntimeException("Cliente no encontrado") );
        aModificar.setNombre(clienteDTO.getNombre());
        aModificar.setApellido(clienteDTO.getApellido());
        aModificar.setDni(clienteDTO.getDni());
        return Mapper.mapToDTO(clienteRepository.save(aModificar));
    }

    public void eliminarCliente(Long id){
        Cliente aEliminar = clienteRepository.findById(id).orElseThrow( () -> new RuntimeException("Cliente no encontrado") );
        clienteRepository.delete(aEliminar);
    }
}
