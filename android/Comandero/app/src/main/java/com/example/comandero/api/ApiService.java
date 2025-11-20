package com.example.comandero.api;

import com.example.comandero.model.Categoria;
import com.example.comandero.model.Mesa;
import com.example.comandero.model.MesaAbierta;
import com.example.comandero.model.Producto;
import com.example.comandero.model.dto.ComandaRequest;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {

    @GET("/api/mesas")
    Call<List<Mesa>> obtenerMesas();

    @GET("/api/mesas/{mesaId}/abierto")
    Call<MesaAbierta> obtenerMesaAbierta(@Path("mesaId") long mesaId);

    @GET("/api/categorias")
    Call<List<Categoria>> obtenerCategorias();

    @GET("/api/productos")
    Call<List<Producto>> obtenerProductos();

    @GET("/api/productos")
    Call<List<Producto>> obtenerProductosPorCategoria(@Query("categoriaId") long categoriaId);

    @POST("/api/comandas")
    Call<ComandaRequest.ComandaRespuesta> crearComanda(@Body ComandaRequest req);

    @POST
    Call<Object> rawPost(@Url String url, @Body RequestBody body);
}
