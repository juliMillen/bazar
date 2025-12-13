package com.jm.bazar.Controller;

import com.jm.bazar.Service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VentaController {

    @Autowired
    private VentaService ventaService;
}
