
package ru.ivankov.newsapp.view.screens

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.R
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import ru.ivankov.newsapp.viewmodel.NewsViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: NewsViewModel
) {
   // val vmNews = NewsViewModel by activityViewModels()
    val context = LocalContext.current
   val profileState = viewModel._profileData.observeAsState()


    val userNewsState by viewModel.newsList.observeAsState() //Добавить позже фильтр на пользовательские новости
    //основной контейнер(похоже можно было не создавать)

    Column()
    {
        Box(modifier = Modifier
            .background(Color.Red)
            .fillMaxWidth()
            .weight(2f))
        {
//Карточка пользователя---------------------------------------------------
                Column() {
                    //-------------------------------------------
//            //Аватар
                    Image(
                        painter = painterResource(R.drawable.ic_default_avatar),
                        contentDescription = "Аватар пользователя",
                    )
////Имя пользователя--------------------------------------------------------------
                Text(
                    text = "${profileState?.value?.name}",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.SemiBold,
                )

////Email пользователя------------------------------------------------------------
                Text(
                    text = "${profileState?.value?.email}",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.SemiBold,

                    )
        }}
//Карточка новостей---------------------------------------------------
        Box(modifier = Modifier
            .background(Color.Yellow)
            .fillMaxWidth()
            .weight(5f)) {
            Card(

                shape = RoundedCornerShape(15.dp),
                elevation = 5.dp,
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .border(2.dp, Color.Black, shape = RoundedCornerShape(15.dp))
            ) {
                //Содержимое пользовательской карточки
            }
        }
//Карточка с кнопками---------------------------------------------------
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .weight(0.5f)
            ) {

                //Кнопки
                TextButton(onClick = { navController.navigate(route = AppNavHost.News.route) }) {
                   Text(text = "ВСЕ НОВОСТИ")
               
            }

        }

    }}

@Preview(showBackground = true)
@Composable
fun prevMyProfileScreen(){
    NewsAppTheme {
        ProfileScreen(
            navController = rememberNavController(),
            viewModel = NewsViewModel()
        )

    }}
