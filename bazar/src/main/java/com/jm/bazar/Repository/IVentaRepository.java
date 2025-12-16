package com.jm.bazar.Repository;

import com.jm.bazar.Entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IVentaRepository extends JpaRepository<Venta,Long> {

    List<Venta> findByFechaVenta(LocalDate fecha);
}
