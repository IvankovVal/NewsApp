package ru.ivankov.newsapp.view.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ivankov.newsapp.view.removeSpace
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import ru.ivankov.newsapp.viewmodel.NewsViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: NewsViewModel,
) {
    val context = LocalContext.current
    val profileState = viewModel.profileData.observeAsState()
    val newsState = viewModel.newsList.observeAsState(listOf())
    val pageState = viewModel.newsList.value!!.size / 15   //pageAmount.observeAsState()
    //основной контейнер(похоже можно было не создавать)
    val openDeleteUserDialog = remember { mutableStateOf(false) }
//-------------------------------------------------------------
    Column()
    {
        //Карточка пользователя
        Box( modifier = Modifier.background(Color.White).fillMaxWidth().weight(2f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp)) {
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
        //Кнопки удаления и редактирования профиля и выхода-------------------------------------------------
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .weight(0.5f)
        ) {
            Row() {
//Кнопка удаления в screen--------------------------------------------------------------------------
                TextButton(
                    onClick = {
                        //Тут должен быть вызов диалога
                        openDeleteUserDialog.value = true
                    },
                    modifier = Modifier.weight(1f)

                ) {
                    Text(text = "УДАЛИТЬ")
                }
                if (openDeleteUserDialog.value) {
                    //Вызываем диалог
//--------------------------------------Alert dialog при удалении-----------------------------------------------
                    AlertDialog(onDismissRequest = { openDeleteUserDialog.value = false },
                        title = { Text(text = "Вы действительно хотите удалить профиль?") },
                        buttons = {
                            Row(
                                modifier = Modifier.padding(all = 8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
//Кнопка удаления в диалоге-------------------------------------------------------------------------
                                TextButton(
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        viewModel.deleteUser("${profileState.value?.token}")
                                        navController.navigate(route = AppNavHost.News.route)
                                        openDeleteUserDialog.value = false
                                    }
                                ) {
                                    Text("Удалить")
                                }
//Кнопка отмены удаления в диалоге------------------------------------------------------------------
                                TextButton(
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        openDeleteUserDialog.value = false
                                        Toast.makeText(
                                            context,
                                            "Удаление отменено",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                ) {
                                    Text("Отмена")
                                }
                            }

                        }
                    )
//-----------------------Конец Alert Dialog---------------------------------------------------------
                }
//Кнопка редактирования в screen--------------------------------------------------------------------
                TextButton(
                    onClick = { navController.navigate(route = AppNavHost.EditScreen.route)
                    },
                    modifier = Modifier.weight(1f)

                ) {
                    Text(text = "ИЗМЕНИТЬ")
                }
                if (openDeleteUserDialog.value) {
                    //Вызываем диалог

                }
//Кнопка выхода из учётной записи-------------------------------------------------------------------------
                TextButton(
                    onClick = {
                        viewModel._profileData.value = null
                        viewModel.getNewsList(1)
                        navController.navigate(route = AppNavHost.News.route)
                    },
                    modifier = Modifier.weight(1f)
                )
                { Text(text = "ВЫХОД") }

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
            items(count = Math.ceil(pageState.toDouble()).toInt() + 1)
            {
                Text(
                    text = "стр ${it + 1}",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable {
                            viewModel.searchNews(
                                it + 1,
                                15,
                                "${viewModel.profileData.value?.name}",
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
                reverseLayout = true,//Для обратного порядка отображения списка новостей
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(4.dp)
            ) {
                items(newsState.value, key = { it.id }) {
                    ItemNewsProfile(item = it, viewModel, navController)//it указывает на newsState.value
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
                    navController.navigate(route = AppNavHost.News.route)
                }
            }) {
                Text(text = "ВСЕ НОВОСТИ")
            }
        }
    }
}
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



