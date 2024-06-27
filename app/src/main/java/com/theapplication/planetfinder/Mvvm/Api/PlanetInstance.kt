package com.theapplication.planetfinder.Mvvm.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PlanetInstance {
    private const val Baseurl="https://planets-17f2.onrender.com/planets/"

    private fun getinstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(Baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val planetapi:PlanetApi= getinstance().create(PlanetApi::class.java)
}