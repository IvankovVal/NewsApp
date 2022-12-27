
package ru.ivankov.newsapp.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.R
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

    androidx.compose.material.Surface(color = Color.White) {
        //Задаём компановку
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (userCard,userNews, AllNewsButton, btnRefresh) = createRefs()
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
                text = "${profileState?.value?.name }",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.SemiBold,
                )

////Email пользователя------------------------------------------------------------
            Text(
                text = "${profileState?.value?.email }",
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
                //Кнопки
                Button(
                    onClick = {
                        viewModel.postAutentification()
                    },
                    modifier = Modifier.padding(30.dp)
                ) { Text(text = "Refresh") }
            }

        }

}}

//@Preview(showBackground = true)
//@Composable
//fun prevMyProfileScreen(){
//    NewsAppTheme {
//        ProfileScreen(
//            navController = rememberNavController(),
//            vmNews = NewsViewModel(),
//            viewModel = mViewModel
//        )
//
//    }}
