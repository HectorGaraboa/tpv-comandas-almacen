package com.hector.tpv.tpv.desktop.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hector.tpv.tpv.desktop.model.Categoria;
import com.hector.tpv.tpv.desktop.model.CierreTurno;
import com.hector.tpv.tpv.desktop.model.ComandaRequest;
import com.hector.tpv.tpv.desktop.model.Mesa;
import com.hector.tpv.tpv.desktop.model.MesaAbierta;
import com.hector.tpv.tpv.desktop.model.Producto;
import com.hector.tpv.tpv.desktop.model.Ticket;
import com.hector.tpv.tpv.desktop.model.UsuarioSesion;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class ApiClient {

    private final HttpClient http = HttpClient.newHttpClient();

    private final ObjectMapper mapper = crearMapper();

    private static ObjectMapper crearMapper() {
        ObjectMapper m = new ObjectMapper();
        m.registerModule(new JavaTimeModule());
        m.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return m;
    }

    private final String baseUrl;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static ApiClient fromProps() {
        String def = "http://localhost:8080";
        try (var is = ApiClient.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (is != null) {
                Properties props = new Properties();
                props.load(is);
                String url = props.getProperty("api.baseUrl", def);
                return new ApiClient(url);
            }
        } catch (Exception ignored) {
        }
        return new ApiClient(def);
    }

    public List<Mesa> getMesas() throws IOException, InterruptedException {
        var req = HttpRequest.newBuilder(URI.create(baseUrl + "/api/mesas")).GET().build();
        var resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new IOException("HTTP " + resp.statusCode() + " => " + resp.body());
        }
        return mapper.readValue(resp.body(), new TypeReference<List<Mesa>>() {
        });
    }

    public List<Producto> getProductos() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder(URI.create(baseUrl + "/api/productos")).GET().build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new IOException("HTTP " + resp.statusCode());
        }
        return mapper.readValue(resp.body(), new TypeReference<List<Producto>>() {
        });
    }

    public Optional<Long> postComanda(ComandaRequest c) throws IOException, InterruptedException {
        String json = mapper.writeValueAsString(c);
        HttpRequest req = HttpRequest.newBuilder(URI.create(baseUrl + "/api/comandas"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() == 200 || resp.statusCode() == 201) {
            try {
                var n = mapper.readTree(resp.body());
                if (n.has("id")) {
                    return Optional.of(n.get("id").asLong());
                }
                if (n.has("comandaId")) {
                    return Optional.of(n.get("comandaId").asLong());
                }
            } catch (Exception ignored) {
            }
            return Optional.empty();
        }
        throw new IOException("HTTP " + resp.statusCode() + ": " + resp.body());
    }

    public MesaAbierta getMesaAbierta(Long mesaId) throws IOException, InterruptedException {
        var req = HttpRequest.newBuilder(URI.create(baseUrl + "/api/mesas/" + mesaId + "/abierto")).GET().build();
        var resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new IOException("HTTP " + resp.statusCode() + " => " + resp.body());
        }
        return mapper.readValue(resp.body(), MesaAbierta.class);
    }

    public List<Categoria> getCategorias() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder(URI.create(baseUrl + "/api/categorias"))
                .GET().build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new IOException("HTTP " + resp.statusCode() + " => " + resp.body());
        }
        return mapper.readValue(resp.body(), new TypeReference<List<Categoria>>() {
        });
    }

    public List<Producto> getProductosPorCategoria(Long categoriaId) throws IOException, InterruptedException {
        String url = baseUrl + "/api/productos?categoriaId=" + URLEncoder.encode(String.valueOf(categoriaId), StandardCharsets.UTF_8);
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new IOException("HTTP " + resp.statusCode() + " => " + resp.body());
        }
        return mapper.readValue(resp.body(), new TypeReference<List<Producto>>() {
        });
    }

    public Ticket cobrarMesa(Long mesaId, String metodoPago) throws IOException, InterruptedException {
        String url = baseUrl + "/api/mesas/" + mesaId + "/cobrar";
        String body = mapper.writeValueAsString(Map.of("metodoPago", metodoPago));
        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() < 200 || resp.statusCode() >= 300) {
            throw new IOException("HTTP " + resp.statusCode() + " => " + resp.body());
        }
        return mapper.readValue(resp.body(), Ticket.class);
    }

    public Path descargarTicketPdf(Long ticketId) throws IOException, InterruptedException {
        String url = baseUrl + "/api/tickets/" + ticketId + "/pdf";

        HttpRequest req = HttpRequest
                .newBuilder(URI.create(url))
                .GET()
                .build();

        HttpResponse<byte[]> resp = http.send(
                req,
                HttpResponse.BodyHandlers.ofByteArray()
        );

        if (resp.statusCode() < 200 || resp.statusCode() >= 300) {
            throw new IOException("HTTP " + resp.statusCode() + " => " + new String(resp.body()));
        }

        String home = System.getProperty("user.home");
        Path escritorio = Paths.get(home, "Desktop");
        Path carpeta = escritorio.resolve("TPV_Tickets");
        Files.createDirectories(carpeta);

        String nombre = "ticket_" + ticketId + ".pdf";
        Path destino = carpeta.resolve(nombre);

        Files.write(destino, resp.body());

        return destino;
    }

    public CierreTurno cerrarTurno() throws IOException, InterruptedException {
        String url = baseUrl + "/api/cierres/turno";

        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() == 409) {
            throw new IllegalStateException("No hay comandas cobradas para cerrar");
        }

        if (resp.statusCode() < 200 || resp.statusCode() >= 300) {
            throw new IOException("HTTP " + resp.statusCode() + " => " + resp.body());
        }

        return mapper.readValue(resp.body(), CierreTurno.class);
    }

    public Path descargarCierreTurnoPdf(Long cierreId) throws IOException, InterruptedException {
        String url = baseUrl + "/api/cierres/" + cierreId + "/pdf";

        HttpRequest req = HttpRequest
                .newBuilder(URI.create(url))
                .GET()
                .build();

        HttpResponse<byte[]> resp = http.send(
                req,
                HttpResponse.BodyHandlers.ofByteArray()
        );

        if (resp.statusCode() < 200 || resp.statusCode() >= 300) {
            throw new IOException("HTTP " + resp.statusCode() + " => " + new String(resp.body()));
        }

        String home = System.getProperty("user.home");
        Path escritorio = Paths.get(home, "Desktop");
        Path carpeta = escritorio.resolve("TPV_Cierres");
        Files.createDirectories(carpeta);

        String nombre = "cierre_" + cierreId + ".pdf";
        Path destino = carpeta.resolve(nombre);

        Files.write(destino, resp.body());

        return destino;
    }

    private static class LoginPayload {

        private String usuario;
        private String password;

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String v) {
            usuario = v;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String v) {
            password = v;
        }
    }

    public UsuarioSesion login(String usuario, String password) throws IOException, InterruptedException {
        String url = baseUrl + "/api/auth/login";

        LoginPayload payload = new LoginPayload();
        payload.setUsuario(usuario);
        payload.setPassword(password);

        String body = mapper.writeValueAsString(payload);

        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() == 401 || resp.statusCode() == 403) {
            throw new IOException("Credenciales inválidas");
        }

        if (resp.statusCode() < 200 || resp.statusCode() >= 300) {
            throw new IOException("HTTP " + resp.statusCode() + " => " + resp.body());
        }

        return mapper.readValue(resp.body(), UsuarioSesion.class);
    }

}
