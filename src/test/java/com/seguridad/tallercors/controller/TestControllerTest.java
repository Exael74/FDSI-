package com.seguridad.tallercors.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void obtenerDatosSensibles_deberiaRetornarJsonEsperado() throws Exception {
        mockMvc.perform(get("/api/datos-sensibles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario").value("Stiven"))
                .andExpect(jsonPath("$.saldo").value(5000))
                .andExpect(jsonPath("$.estado").value("autenticado"));
    }
}
