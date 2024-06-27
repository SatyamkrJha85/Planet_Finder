package com.theapplication.planetfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.theapplication.planetfinder.Mvvm.Model.PlanetData
import com.theapplication.planetfinder.Mvvm.Presentation.PlanetDetailed
import com.theapplication.planetfinder.Mvvm.Presentation.PlanetHome
import com.theapplication.planetfinder.Mvvm.Presentation.SplashScreen
import com.theapplication.planetfinder.Mvvm.ViewModel.PlanetViewModel
import com.theapplication.planetfinder.ui.theme.PlanetFinderTheme
import com.theapplication.planetfinder.ui.theme.myprimarycolor
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val planerviewmodel = ViewModelProvider(this).get(PlanetViewModel::class.java)

        enableEdgeToEdge(
        )
        setContent {
            PlanetFinderTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = com.theapplication.planetfinder.NavGraph.NavGraph.Splash.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(com.theapplication.planetfinder.NavGraph.NavGraph.Splash.route) {
                            SplashScreen(navController)
                        }
                        composable(com.theapplication.planetfinder.NavGraph.NavGraph.Home.route) {
                            PlanetHome(planerviewmodel, navController = navController)
                        }


                        composable(
                            route = "${com.theapplication.planetfinder.NavGraph.NavGraph.Search.route}/{planetName}"
                        ) { backStackEntry ->
                            val planetName = backStackEntry.arguments?.getString("planetName")?:""
                            val title11 = remember { mutableStateOf(planetName) }
                            PlanetDetailed(
                                planetViewModel = planerviewmodel,
                                title11 = title11
                            )
                        }

                    }

                }

            }


        }
    }
}



