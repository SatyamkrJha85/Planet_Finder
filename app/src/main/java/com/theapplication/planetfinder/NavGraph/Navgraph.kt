package com.theapplication.planetfinder.NavGraph

import androidx.navigation.NavGraph
import com.google.gson.Gson
import com.theapplication.planetfinder.Mvvm.Model.PlanetData
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class NavGraph(val route:String){
    object Splash: com.theapplication.planetfinder.NavGraph.NavGraph("Splash")
    object Home: com.theapplication.planetfinder.NavGraph.NavGraph("Home")
    object Search : com.theapplication.planetfinder.NavGraph.NavGraph("Search/{title}")


}
