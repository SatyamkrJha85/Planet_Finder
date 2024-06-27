package com.theapplication.planetfinder.Mvvm.Presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.theapplication.planetfinder.Mvvm.Api.PlanetResponse
import com.theapplication.planetfinder.Mvvm.Model.PlanetData
import com.theapplication.planetfinder.Mvvm.ViewModel.PlanetViewModel
import com.theapplication.planetfinder.NavGraph.NavGraph
import com.theapplication.planetfinder.R
import com.theapplication.planetfinder.ui.theme.LightBlue
import com.theapplication.planetfinder.ui.theme.LightCyan
import com.theapplication.planetfinder.ui.theme.LightMagenta
import com.theapplication.planetfinder.ui.theme.LightOrange
import com.theapplication.planetfinder.ui.theme.LightPurple
import com.theapplication.planetfinder.ui.theme.LightRed
import com.theapplication.planetfinder.ui.theme.myprimarycolor
import com.theapplication.planetfinder.ui.theme.searchbackground

@Composable
fun PlanetHome(planetViewModel: PlanetViewModel, modifier: Modifier = Modifier,navController: NavController) {
    var planetname = remember {
        mutableStateOf("")
    }

    planetname.value="earth"

    planetViewModel.getdata(planetname.value)


    LazyColumn(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ResultData(planetViewModel = planetViewModel, title2 = planetname, navController = navController)

        }
    }

}



@Composable
fun ResultData(modifier: Modifier = Modifier, planetViewModel: PlanetViewModel,title2: MutableState<String>,navController: NavController) {

    val planetresult = planetViewModel.palnetResult.observeAsState()
    when (val result = planetresult.value) {
        is PlanetResponse.Errors -> {

            Column(
                modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.sadplanet), contentDescription = null)
                Text(text = result.message, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

            }

        }

        is PlanetResponse.Sucess -> {

                Mainscreen(planetData = result.data, title1 = title2, navController = navController)

        }

        PlanetResponse.loading -> {
            Column(
                modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Color.White, modifier=modifier.size(36.dp))
            }
        }

        null -> {}
    }
}

@Composable
fun Mainscreen(title1:MutableState<String>,planetData: PlanetData,modifier: Modifier = Modifier,navController: NavController) {
    Column(
        modifier
            .fillMaxSize()
            .background(myprimarycolor),

    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Let's Explore",
                color = Color.White,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier
                    .size(50.dp)
                    .border(2.dp, Color.White, CircleShape)
                    .clip(
                        CircleShape
                    )

            )
        }
        Text(
            text = "The Milky Way Galaxy", color = Color.White, fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .padding(start = 10.dp, end = 10.dp),
        )

        Spacer(modifier = modifier.height(20.dp))


        Row(
            modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

        Row(
            modifier
                .width(330.dp)
                .height(50.dp)
                .clickable {
                    title1.value = "Earth"
                    navController.navigate("${NavGraph.Search.route}/${title1.value}")

                }
                .clip(shape = RoundedCornerShape(12.dp))
                .background(searchbackground),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null, tint = Color.Gray)
            Spacer(modifier = modifier.width(10.dp))
            Text(
                text = "Search for your favroit planet...",
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )
        }

    }

        Spacer(modifier = modifier.height(20.dp))

        Row {


            Text(
                text = "Most Popular", color = Color.White, fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier
                    .padding(start = 10.dp, end = 10.dp),
            )

            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null, tint = Color.White)

        }
        Spacer(modifier = modifier.height(20.dp))

        Row(
            modifier.horizontalScroll(rememberScrollState())
        ) {
            popularplanerbox(navController, title12 = title1, img = R.drawable.planet1, title ="Saturn" , dis ="The Rulling planet", color = LightOrange )
            popularplanerbox(navController,title12 = title1,img = R.drawable.planet2, title ="Jupiter" , dis ="The Oldest palnet", color = LightRed )
            popularplanerbox(navController,title12 = title1,img = R.drawable.planet3, title ="Earth" , dis ="The Living palnet", color = LightCyan )

        }

        Spacer(modifier = modifier.height(20.dp))

        Row {
            Text(
                text = "You may also like", color = Color.White, fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier
                    .padding(start = 10.dp, end = 10.dp),
            )

            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null, tint = Color.White)

        }
        Spacer(modifier = modifier.height(20.dp))

        Row (
            modifier.horizontalScroll(rememberScrollState())

        ){
            likeplanerbox(navController,title1,img = R.drawable.planet4, title ="Pluto" , dis ="The kuiper belt", color = LightBlue )
            likeplanerbox(navController,title1,img = R.drawable.planet5mars, title ="mars" , dis ="Venus big palnet", color = LightMagenta )
            likeplanerbox(navController,title1,img = R.drawable.planet6urenus, title ="urenus" , dis ="Very big palnet", color = LightPurple )
            likeplanerbox(navController,title1,img = R.drawable.planet7, title ="Sun" , dis ="The Hottest palnet", color = LightOrange )

        }

    }
}



@Composable
fun popularplanerbox(navController: NavController,title12:MutableState<String>,modifier: Modifier = Modifier,img:Int,title:String,dis:String,color: Color) {



    val infiniteTransition = rememberInfiniteTransition()
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier
            .width(250.dp)
            .clickable {
                title12.value = ""
                title12.value = title.toString()
                navController.navigate("${NavGraph.Search.route}/${title12.value}")
            }
            .height(300.dp)
            .padding(10.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color), contentAlignment = Alignment.Center){
        Image(painter = painterResource(id =img ), contentDescription = null,modifier.rotate(rotationAngle))
        Column (
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .align(Alignment.BottomStart),
        ){
            Text(
                text = title, color = Color.Black, fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier
                    .padding(start = 10.dp, end = 10.dp),
            )
            Text(
                text = dis, color = Color.Black, fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                modifier = modifier
                    .padding(start = 10.dp, end = 10.dp),
            )
        }
    }
}

@Composable
fun likeplanerbox(navController: NavController,title12:MutableState<String>,modifier: Modifier = Modifier,img:Int,title:String,dis:String,color: Color) {

    val infiniteTransition = rememberInfiniteTransition()
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    Box(
        modifier
            .width(150.dp)
            .height(160.dp)
            .clickable {
                title12.value = ""
                title12.value = title.toString()
                navController.navigate("${NavGraph.Search.route}/${title12.value}")
            }
            .padding(10.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color), contentAlignment = Alignment.Center){
        Image(painter = painterResource(id =img ), contentDescription = null,modifier.rotate(rotationAngle))
        Column (
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
        ){
            Text(
                text = title, color = Color.Black, fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier
                    .padding(start = 10.dp, end = 10.dp),
            )

        }
    }
}