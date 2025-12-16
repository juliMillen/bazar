package com.jm.bazar.Repository;

import com.jm.bazar.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductoRepository extends JpaRepository<Producto,Long> {

    Optional<Producto> findByNombre(String nombreProducto);
}
