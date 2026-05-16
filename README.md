# Taller CORS (OWASP A05 - Security Misconfiguration)

Proyecto educativo en Spring Boot para recrear, auditar y mitigar una mala configuración de CORS: **origen abierto + credenciales permitidas**.

## 1) Requisitos

- Java 17+
- Maven 3.9+

## 2) Compilar y ejecutar

En la raíz del proyecto:

```bash
mvn clean package
mvn spring-boot:run
```

La API quedará en `http://localhost:8080`.

## 3) Endpoint de prueba

- `GET /api/datos-sensibles`
- Respuesta esperada:

```json
{"usuario":"Stiven","saldo":5000,"estado":"autenticado"}
```

## 4) Validar vulnerabilidad CORS con cabecera Origin maliciosa

Con el backend ejecutándose, prueba preflight desde terminal:

```bash
curl -i -X OPTIONS "http://localhost:8080/api/datos-sensibles" \
  -H "Origin: http://evil.com" \
  -H "Access-Control-Request-Method: GET"
```

En estado vulnerable debes observar encabezados equivalentes a:

- `Access-Control-Allow-Origin: http://evil.com`
- `Access-Control-Allow-Credentials: true`

> Nota: Spring puede reflejar el origen recibido cuando se usa `allowedOriginPatterns("*")` con credenciales.

## 5) Simulador frontend de ataque

Archivo: `src/main/resources/static/attack-simulator.html`

1. Levanta el backend (`mvn spring-boot:run`).
2. Abre el simulador desde otro origen (por ejemplo, puerto 5500 con Live Server).
3. Haz clic en **"Lanzar ataque"**.
4. Si está vulnerable, el navegador permitirá leer los datos; si está mitigado, CORS bloqueará la lectura.

## 6) Cambiar a estado MITIGADO (seguro)

Edita `src/main/java/com/seguridad/tallercors/config/CorsConfig.java` y aplica estos cambios exactos:

1. **Comenta o elimina** el bloque vulnerable dentro de `addCorsMappings`:

```java
registry.addMapping("/api/**")
        .allowedOriginPatterns("*")
        .allowedMethods("GET", "POST", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
```

2. **Descomenta o agrega** el bloque seguro (el mismo que está documentado en el comentario del archivo):

```java
registry.addMapping("/api/**")
        .allowedOrigins("http://localhost:5500", "http://127.0.0.1:5500")
        .allowedMethods("GET", "POST", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
```

3. Reinicia la app.

## 7) Verificar mitigación

Repite la prueba con origen atacante:

```bash
curl -i -X OPTIONS "http://localhost:8080/api/datos-sensibles" \
  -H "Origin: http://evil.com" \
  -H "Access-Control-Request-Method: GET"
```

Resultado esperado en modo mitigado:

- El origen `http://evil.com` **no** es autorizado.
- El navegador bloquea la lectura de la respuesta CORS.
- Dependiendo de la configuración y filtro activo, puede verse rechazo CORS (incluyendo escenarios con 403) o ausencia de encabezados CORS válidos para ese origen.
