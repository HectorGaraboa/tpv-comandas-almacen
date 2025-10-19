package com.hector.tpv.tpv.desktop.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hector.tpv.tpv.desktop.model.ComandaRequest;
import com.hector.tpv.tpv.desktop.model.Mesa;
import com.hector.tpv.tpv.desktop.model.MesaAbierta;
import com.hector.tpv.tpv.desktop.model.Producto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ApiClient {

    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
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
}
