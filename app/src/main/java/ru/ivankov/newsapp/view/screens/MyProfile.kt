
package ru.ivankov.newsapp.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.R
import ru.ivankov.newsapp.model.DataLoginResponse
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import ru.ivankov.newsapp.viewmodel.NewsViewModel

@Composable
fun MyProfileScreen(
    navController: NavHostController,
    vmNews: NewsViewModel = viewModel()
) {
  //  val vmNews = viewModel<NewsViewModel>()
    val context = LocalContext.current
   val profileState by vmNews.profileData.collectAsState()


    val userNewsState by vmNews.newsList.observeAsState() //Добавить позже фильтр на пользовательские новости
    //основной контейнер(похоже можно было не создавать)

    androidx.compose.material.Surface(color = Color.White) {
        //Задаём компановку
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (userCard,userNews, AllNewsButton) = createRefs()
//Карточка пользователя---------------------------------------------------
            Card(

                shape = RoundedCornerShape(15.dp),
                elevation = 5.dp,
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .border(2.dp, Color.Black, shape = RoundedCornerShape(15.dp))
//               .fillMaxSize()
                    .constrainAs(userCard) {
                        start.linkTo(parent.start, 4.dp)
                        end.linkTo(parent.end, 4.dp)
                        top.linkTo(parent.top, 4.dp)
                        bottom.linkTo(userNews.top, 4.dp)
                    }
            ) {
                //Содержимое пользовательской карточки
                Column() {
                    //-------------------------------------------
//            //Аватар
            Image(painter = painterResource(R.drawable.ic_default_avatar),
                contentDescription = "Аватар пользователя", )
////Имя пользователя--------------------------------------------------------------
            Text(
                text = "${profileState.name }",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.SemiBold,
                )

////Email пользователя------------------------------------------------------------
            Text(
                text = "$${profileState.email }",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.SemiBold,

            )
                }
            }
//Карточка новостей---------------------------------------------------
            Card(

                shape = RoundedCornerShape(15.dp),
                elevation = 5.dp,
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .border(2.dp, Color.Black, shape = RoundedCornerShape(15.dp))
//               .fillMaxSize()
                    .constrainAs(userNews) {
                        start.linkTo(parent.start, 4.dp)
                        end.linkTo(parent.end, 4.dp)
                        top.linkTo(userCard.bottom, 4.dp)
                        bottom.linkTo(AllNewsButton.top, 4.dp)
                    }
            ) {
                //Содержимое пользовательской карточки
            }
//Карточка с кнопками---------------------------------------------------
            Card(

                shape = RoundedCornerShape(15.dp),
                elevation = 5.dp,
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .border(2.dp, Color.Black, shape = RoundedCornerShape(15.dp))
//               .fillMaxSize()
                    .constrainAs(AllNewsButton) {
                        start.linkTo(parent.start, 4.dp)
                        end.linkTo(parent.end, 4.dp)
                        top.linkTo(userNews.bottom, 4.dp)
                        bottom.linkTo(parent.bottom, 4.dp)
                    }
            ) {
                //Содержимое пользовательской карточки
            }}

////Поле для новостей пользователя-------------------------------------------
//            Card (
//                shape = RoundedCornerShape(16.dp),
//                backgroundColor = Color.Cyan,
//                border = BorderStroke(2.dp, Color.Gray),
//                modifier = Modifier.constrainAs(userNews){
//                    top.linkTo(userAvatar.bottom,16.dp)
//                    bottom.linkTo(parent.bottom,16.dp)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//
//                ){
////Список новостей пользователя-------------------------------------------
//                //Атрибуты списка
////                LazyColumn(contentPadding = PaddingValues(
////                    top = 16.dp,
////                    start = 8.dp,
////                    end = 8.dp,
////                    bottom = 72.dp
////                ),
////                verticalArrangement = Arrangement.spacedBy(8.dp)
////                    )
////                //Содержание списка
////                {
////                    items(
////
////                    )
////                }
//            }
//        }
//    }
}}

@Preview(showBackground = true)
@Composable
fun prevMyProfileScreen(){
    NewsAppTheme {
        MyProfileScreen(
            navController = rememberNavController(),
            vmNews = NewsViewModel()
        )

    }}
