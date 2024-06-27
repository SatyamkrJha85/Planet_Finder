package com.theapplication.planetfinder.Mvvm.Api

sealed class PlanetResponse<out T> {
    data class Sucess <out T>(val data: T):PlanetResponse<T>()
    data class Errors(val message:String):PlanetResponse<Nothing>()
    object loading:PlanetResponse<Nothing>()
}