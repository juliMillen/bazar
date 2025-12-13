package com.jm.bazar.Service;

import com.jm.bazar.Repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService {

    @Autowired
    private IVentaRepository ventaRepository;
}
