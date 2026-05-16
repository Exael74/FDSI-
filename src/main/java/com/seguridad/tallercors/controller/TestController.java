package com.seguridad.tallercors.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/datos-sensibles")
    public Map<String, Object> obtenerDatosSensibles() {
        return Map.of(
                "usuario", "Stiven",
                "saldo", 5000,
                "estado", "autenticado");
    }
}
