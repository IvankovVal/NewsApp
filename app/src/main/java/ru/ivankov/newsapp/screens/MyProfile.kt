
package ru.ivankov.newsapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.R
import ru.ivankov.newsapp.navigation.AppNavHost
import ru.ivankov.newsapp.ui.theme.NewsAppTheme

@Composable
fun MyProfileScreen(navController: NavHostController) {
    //основной контейнер(похоже можно было не создавать)
    androidx.compose.material.Surface(color = Color.White) {
        //Задаём компановку
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (userAvatar,userName, userNews,) = createRefs()

            //Аватар
            Image(painter = painterResource(R.drawable.ic_default_avatar),
                contentDescription = "Аватар пользователя",
            modifier = Modifier.constrainAs(userAvatar){
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start,16.dp)
            })
            //Имя пользователя
            Text(
                text = "Имя пользователя",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.constrainAs(userName){
                    top.linkTo(userAvatar.bottom, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                }
                )
            //Поле для новостей пользователя
            Card (
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.Cyan,
                border = BorderStroke(2.dp, Color.Gray),
                modifier = Modifier.constrainAs(userNews){
                    top.linkTo(userAvatar.bottom,16.dp)
                    bottom.linkTo(parent.bottom,16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

                ){
//                LazyColumn(content = )//Эта штука должна работать как RecyclerView

            }



        }

    }
}

@Preview(showBackground = true)
@Composable
fun prevMyProfileScreen(){
    NewsAppTheme {
        MyProfileScreen(navController = rememberNavController())

    }
}