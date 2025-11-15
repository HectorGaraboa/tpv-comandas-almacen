package com.example.comandero;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {


    @GET("api/mesas")
    Call<List<Mesa>> obtenerMesas();

    @GET("api/mesas/{id}/abierto")
    Call<MesaAbiertaDTO> obtenerMesaAbierta(@Path("id") long idMesa);
    
}
