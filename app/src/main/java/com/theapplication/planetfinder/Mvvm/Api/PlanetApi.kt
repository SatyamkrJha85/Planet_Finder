package com.theapplication.planetfinder.Mvvm.Api

import com.theapplication.planetfinder.Mvvm.Model.PlanetData
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanetApi {
    @GET("getPlanet?")
    suspend fun getplanet(
        @Query("name")name:String

    ):retrofit2.Response<PlanetData>
}