
package ru.ivankov.newsapp.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
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
    val newsState = viewModel.newsList.observeAsState(listOf())
    val userNewsState by viewModel.newsList.observeAsState() //Добавить позже фильтр на пользовательские новости
    //основной контейнер(похоже можно было не создавать)
    Column()
    {
        //Карточка пользователя
        Box(modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .weight(2f)){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp)
            ) {
//-------------  Аватар----------------------------------------------------------------------------

                AsyncImage(
                    model = profileState.value?.avatar,
                    contentDescription = "Аватар",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = 5.dp,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start  ,
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .wrapContentSize()
                            .background(Color.Gray)
                    ) {
////Имя пользователя-------------------------------------------------------------------------------
                        Text(
                            text = "${profileState.value?.name}",
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 10.dp, start = 20.dp, end = 20.dp)
                        )
////Email пользователя-----------------------------------------------------------------------------
                        Text(
                            text = "${profileState.value?.email}",
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 5.dp, start = 20.dp, end = 20.dp)
                        )
                    }
                }
            }
        }
        //Кнопки удаления и редактирования профиля-------------------------------------------------
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .weight(0.5f)){
            Row() {
                //Кнопка удаления
                TextButton(onClick = {
                    viewModel.deleteUser("${profileState.value?.token}")
                    navController.navigate(route = AppNavHost.News.route)
                }) {
                    Text(text = "УДАЛИТЬ ПРОФИЛЬ")
                }
                //Кнопка редактирования
                TextButton(onClick = { navController.navigate(route = AppNavHost.News.route) }) {
                    Text(text = "РЕДАКТИРОВАТЬ ПРОФИЛЬ")
                }
            }

        }
        //Новости пользователя---------------------------------------------------------------------
        Box(modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .weight(6f)){
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(4.dp)
            ) {
                items(newsState.value, key = {it.id}){
                    ItemNews(item = it)//it указывает на newsState.value
                }
            }
        }
        //Кнопка назад-----------------------------------------------------------------------------
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .weight(0.5f)){
            //Кнопки
            TextButton(onClick = { navController.navigate(route = AppNavHost.News.route) }) {
                Text(text = "ВСЕ НОВОСТИ")
            }
        }
    }}
@Preview(showBackground = true)
@Composable
fun prevMyProfileScreen() {
    NewsAppTheme {
        ProfileScreen(
            navController = rememberNavController(),
            viewModel = NewsViewModel()
        )
    }
}

