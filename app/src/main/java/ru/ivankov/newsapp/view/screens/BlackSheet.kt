package ru.ivankov.newsapp.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.viewmodel.NewsViewModel

//@Preview(showBackground = true)
@Composable
fun BlackSheet (
    navController: NavHostController,
    viewModel: NewsViewModel
) {
    // val vmNews = NewsViewModel by activityViewModels()
    val context = LocalContext.current
    val profileState = viewModel.profileData.observeAsState()
    Column()
    {
        //Выбор аватара
        Box(modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .weight(2f)) {


        }
        //Поля для правки пользователя
        Box(modifier = Modifier
            .background(Color.Yellow)
            .fillMaxWidth()
            .weight(5f)) {


        }
//Кнопки назад и сохранить--------------------------------------------------------------------------
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .weight(0.5f)
        ) {
            Row() {
                TextButton(onClick = { navController.navigate(route = AppNavHost.News.route) }) {
                    Text(text = "ОТМЕНИТЬ")
                }
                TextButton(onClick = { navController.navigate(route = AppNavHost.News.route) }) {
                    Text(text = "СОХРАНИТЬ")
                }
            }

        }
    }
}


@Preview
@Composable
fun PrevBlackSheet(){
    NewsAppTheme {
        BlackSheet (navController = rememberNavController(),
            viewModel = NewsViewModel())
    }
}

