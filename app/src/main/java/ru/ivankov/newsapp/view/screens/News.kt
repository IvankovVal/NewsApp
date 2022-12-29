package ru.ivankov.newsapp.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import ru.ivankov.newsapp.viewmodel.NewsViewModel

@Composable
fun NewsScreen(
    navController: NavHostController,
    viewModel: NewsViewModel
) {
    val context = LocalContext.current
    val newsState = viewModel.newsList.observeAsState(listOf())
    val profileState = viewModel._profileData.observeAsState()

    //Расположим карточки с помощью ConstrainLayout
    Column(modifier = Modifier.fillMaxSize()) {//основная колонка содержащая все элементы
// Карточка для  отображения новостей---------------------------------------------------------------
        Card(

            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp,
            modifier = Modifier
                .weight(8.5f)
                .padding(start = 5.dp, end = 5.dp)
                .border(2.dp, Color.Black, shape = RoundedCornerShape(15.dp))
//               .fillMaxSize()

        ) {
// ----------------------Список новостей-----------------------------
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Gray)
                    .padding(4.dp)
            ) {
                items(newsState.value, key = {it.id}){
                    ItemNews(item = it)//it указывает на newsState.value
                }
            }
// ---------------------------------------------------
        }
// -------------------------Карточка со строкой кнопок_______________________________________________
        Card(

            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 5.dp, end = 5.dp)
                .border(2.dp, Color.Black, shape = RoundedCornerShape(20.dp))
                .fillMaxWidth()

        ) {
//----------------------Строка с кнопками--------------------------------------------------
            Row {

//----------------------------Кнопка профиль-------------------------------------------
                IconButton(
                    //добавить выбор при условии если token не null переходить сразу в профиль
                    onClick = {
                        if(  profileState.value == null ){
                        navController.navigate(route = AppNavHost.Login.route)}
                        else navController.navigate(route = AppNavHost.MyProfile.route)
                              },
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Профиль",

                        )

                }


//Кнопка добавить запись
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Добавить запись",

                        )

                }

                //Кнопка поиск
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Поиск",

                        )

                }
            }
        }
        }
//-------------------------------------------------------------------------------------------------         }
    }



@Preview(showBackground = true)
@Composable
fun prevNewsScreen() {
    NewsAppTheme {
        NewsScreen(
            navController = rememberNavController(),
            viewModel = NewsViewModel()
        )

    }
}

