package ru.ivankov.newsapp.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.viewmodel.NewsViewModel

//@Preview(showBackground = true)
@Composable
fun FriendScreen (
    navController: NavHostController,
    viewModel: NewsViewModel,
) {
    val profileState = viewModel.newsAuthor.observeAsState()
    val newsState = viewModel.newsList.observeAsState(listOf())
    val pageState = viewModel.newsList.value!!.size/15   //pageAmount.observeAsState()
//-------------------------------------------------------------
    Column()
    {
        //Карточка пользователя
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .weight(2f)
        ) {
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
                        horizontalAlignment = Alignment.Start,
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
        // -------------------------Строка страниц_______________________________________________
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth()

        ) {
            items(count = Math.ceil(pageState.toDouble()).toInt() + 1 )
            {
                Text(
                    text = "стр ${it + 1}",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable {
                            viewModel.searchNews(
                                it + 1,
                                15,
                                "${viewModel.newsAuthor.value?.name}",
                                "",
                                emptyList()

                            )
                        }
                        .padding(8.dp)
                )
            }
        }
        //Новости пользователя------------------------------------------------------------------
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .weight(5f)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(4.dp)
            ) {
                items(newsState.value, key = { it.id }) {
                    ItemNews(item = it,viewModel, navController)//it указывает на newsState.value
                }
            }
        }
        //Кнопка назад-----------------------------------------------------------------------------
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .weight(0.5f)
        ) {
            //Кнопки
            TextButton(onClick = {
                GlobalScope.launch(Dispatchers.Main) {
                    delay(1000)
                    viewModel.getNewsList(1)
                    navController.navigate(route = AppNavHost.News.route) }
            }) {
                Text(text = "ВСЕ НОВОСТИ")
            }
        }
    }
}
@Preview
@Composable
fun PrevBlackSheet(){
    NewsAppTheme {
        FriendScreen (navController = rememberNavController(),
            viewModel = NewsViewModel())
    }
}

