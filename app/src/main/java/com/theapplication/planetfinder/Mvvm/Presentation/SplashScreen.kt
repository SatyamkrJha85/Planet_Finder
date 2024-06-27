package com.theapplication.planetfinder.Mvvm.Presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.theapplication.planetfinder.NavGraph.NavGraph
import com.theapplication.planetfinder.R
import com.theapplication.planetfinder.ui.theme.buttoncolor
import com.theapplication.planetfinder.ui.theme.myprimarycolor
import com.theapplication.planetfinder.ui.theme.ubuntu

@Composable
fun SplashScreen(navController: NavController,modifier: Modifier = Modifier) {

    Box(modifier = modifier
        .fillMaxSize()
        .background(myprimarycolor)){
        Image(painter = painterResource(id = R.drawable.planetbg) , contentDescription = "")

        Box(modifier = modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 20.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .width(330.dp)
            .height(200.dp)
            .background(Color.White),
            contentAlignment = Alignment.Center
        ){

            Column (
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Hello !", fontWeight = FontWeight.Bold, fontSize = 20.sp, fontFamily = ubuntu, color = Color.Black)

                Spacer(modifier = modifier.height(10.dp))

                Text(text = "Want to know and explore all things \n " +
                        "about the planets in the Milky Way \n Galaxy?", color = Color.Black, textAlign = TextAlign.Center)

                Spacer(modifier = modifier.height(10.dp))

                ElevatedButton(onClick = {
                    navController.navigate(NavGraph.Home.route){
                        popUpTo(0)
                    }
                }, colors = ButtonDefaults.buttonColors(
                    buttoncolor), modifier = modifier.width(230.dp).height(50.dp)) {
                    Text(text = "Explore Now", color = Color.White)
                }
            }

        }
    }
}


