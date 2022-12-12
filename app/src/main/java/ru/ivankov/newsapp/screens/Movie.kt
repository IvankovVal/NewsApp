package ru.ivankov.newsapp.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun MovieBookingScreen(){
    //основной контейнер(похоже можно было не создавать)
    androidx.compose.material.Surface(color = Color.White) {
        //Задаём компановку
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (menuButton,coverImage, titleText, genreText, ratingText, castText,
            castContainer, castImage1, castImage2, castImage3,castImage4, descText) = createRefs()

            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier.constrainAs(menuButton){
                    top.linkTo(parent.top,16.dp)
                    start.linkTo(parent.start,16.dp)
                }

                )
            
        }
        
    }
}
@Preview(showSystemUi = true)
@Composable
fun PrevMovieScreen(){
    MovieBookingScreen()
}