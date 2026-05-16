package com.seguridad.tallercors.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);

        /*
         * ===================== VERSIÓN SEGURA (MITIGADA) =====================
         * Reemplaza el bloque vulnerable anterior por este:
         *
         * registry.addMapping("/api/**")
         *         .allowedOrigins("http://localhost:5500", "http://127.0.0.1:5500")
         *         .allowedMethods("GET", "POST", "OPTIONS")
         *         .allowedHeaders("*")
         *         .allowCredentials(true);
         *
         * ¿Por qué es crítico?
         * Si se combina origen abierto ("*" o reflexión de cualquier Origin) con
         * credenciales habilitadas, un sitio atacante puede hacer peticiones desde
         * el navegador de la víctima y leer respuestas autenticadas (cookies/sesión).
         * Restringir orígenes reduce esa superficie y evita exfiltración de datos.
         * =====================================================================
         */
    }
}
