package com.theapplication.planetfinder.Mvvm.Presentation

import android.icu.text.CaseMap.Title
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.theapplication.planetfinder.Mvvm.Api.PlanetResponse
import com.theapplication.planetfinder.Mvvm.Model.PlanetData
import com.theapplication.planetfinder.Mvvm.ViewModel.PlanetViewModel
import com.theapplication.planetfinder.R
import com.theapplication.planetfinder.ui.theme.LightBlue
import com.theapplication.planetfinder.ui.theme.buttoncolor
import com.theapplication.planetfinder.ui.theme.searchbackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDetailed(
    planetViewModel: PlanetViewModel,
    title11: MutableState<String>,
    modifier: Modifier = Modifier
) {

    LazyColumn {
        item {
            planetViewModel.getdata(title11.value)
            searchbyname(title11, planetViewModel)
            Spacer(modifier = modifier.height(20.dp))
            detailedresult(planetViewModel)
        }
    }




}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchbyname(
    thetitle: MutableState<String>,
    planetViewModel: PlanetViewModel,
    modifier: Modifier = Modifier
) {

    var finaltitle = remember {
        mutableStateOf(thetitle)
    }

    Column {

        Row(
            modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextField(
                value = thetitle.value, onValueChange = { thetitle.value = it },
                modifier
                    .width(280.dp)
                    .height(50.dp)
                    .clip(shape = RoundedCornerShape(12.dp)),
                placeholder = { Text(text = "Search Your Favorite Planet ....") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = searchbackground,
                    focusedTextColor = Color.White,
                    disabledTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.White,
                    disabledPlaceholderColor = Color.White,
                    unfocusedPlaceholderColor = Color.White,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )


            Box(
                modifier
                    .size(50.dp)
                    .clip(shape = CircleShape)
                    .background(searchbackground)
            ) {
                IconButton(onClick = {
                    planetViewModel.getdata(thetitle.value)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

        }


    }
}

@Composable
fun planetdetailedscreen(planetData: PlanetData, modifier: Modifier = Modifier) {
    val maxChars = 100 // maximum number of characters to show initially
    val isExpanded = remember { mutableStateOf(false) }
    val showMore = remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition()
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box {
        Column {


            Row(
                modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = planetData.picture,
                    contentDescription = null,
                    modifier
                        .size(200.dp)
                        .rotate(rotationAngle)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop

                )
            }

            Column {
                Row(
                    modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = planetData.name, color = Color.White, fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier
                            .padding(start = 10.dp, end = 10.dp),
                    )

                    Text(
                        text = "${planetData.tagline}", color = Color.White, fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = modifier
                            .padding(start = 10.dp, end = 10.dp),
                    )
                }

                Divider(color = Color.White, modifier = modifier.padding(10.dp))


                val description = planetData.description
                if (description.length > maxChars) {
                    showMore.value = true
                }

                Text(
                    text = if (isExpanded.value || !showMore.value) description else description.take(
                        maxChars
                    ) + " .....",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = modifier
                        .padding(start = 10.dp, end = 10.dp),
                )

                if (showMore.value) {
                    Text(
                        text = if (isExpanded.value) "Show Less" else "Show More",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .clickable {
                                isExpanded.value = !isExpanded.value
                            }
                    )
                }

                Divider(color = Color.White, modifier = modifier.padding(10.dp))


                Spacer(modifier = modifier.height(10.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {


                    Box(
                        modifier = modifier
                            .size(180.dp)
                            .padding(10.dp)
                            .background(
                                searchbackground,
                                RoundedCornerShape(12.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.planet7),
                                contentDescription = null,
                                modifier.size(70.dp)
                            )
                            Text(
                                text = "Distance from suns", color = Color.White, fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                            )

                            Text(
                                text = "${planetData.distanceFromSun}",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = modifier
                                    .padding(start = 10.dp, end = 10.dp),
                            )

                        }
                    }

                    Box(
                        modifier = modifier
                            .size(180.dp)
                            .padding(10.dp)
                            .background(
                                searchbackground,
                                RoundedCornerShape(12.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.moon),
                                contentDescription = null,
                                modifier.size(70.dp)
                            )
                            Text(
                                text = "Numbers of Moons", color = Color.White, fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                            )

                            Text(
                                text = "${planetData.numberOfMoons}",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = modifier
                                    .padding(start = 10.dp, end = 10.dp),
                            )

                        }
                    }
                }

                Spacer(modifier = modifier.height(30.dp))

            }
        }
    }
}

@Composable
fun detailedresult(planetViewModel: PlanetViewModel, modifier: Modifier = Modifier) {

    val planetresult = planetViewModel.palnetResult.observeAsState()
    when (val result = planetresult.value) {
        is PlanetResponse.Errors -> {
            Column(
                modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sadplanet),
                    contentDescription = null
                )
                Text(text = result.message, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

            }
        }

        is PlanetResponse.Sucess -> {


            planetdetailedscreen(planetData = result.data)


        }

        PlanetResponse.loading -> {
            Column(
                modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Color.White, modifier = modifier.size(36.dp))
            }
        }

        null -> {}
    }
}


