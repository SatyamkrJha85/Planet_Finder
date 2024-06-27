package com.theapplication.planetfinder.Mvvm.Model

data class PlanetData(
    val description: String,
    val distanceFromSun: String,
    val name: String,
    val namesake: String,
    val numberOfMoons: Int,
    val picture: String,
    val rings: Rings,
    val spaceTexture_url: String,
    val tagline: String,
    val tagline_icon: String,
    val textureUrl: String,
    val yearLength: String
)