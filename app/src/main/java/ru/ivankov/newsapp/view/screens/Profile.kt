package ru.ivankov.newsapp.view.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
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
    val profileState = viewModel.profileData.observeAsState()
    val newsState = viewModel.newsList.observeAsState(listOf())
    val userNewsState by viewModel.newsList.observeAsState() //Добавить позже фильтр на пользовательские новости
    //основной контейнер(похоже можно было не создавать)
    val openDeleteUserDialog = remember { mutableStateOf(false) }
    val openEditUserDialog = remember { mutableStateOf(false) }
    val editAvatarState = remember { mutableStateOf("") }
    val editNameState = remember { mutableStateOf("") }
    val editEmailState = remember { mutableStateOf("") }


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
                TextButton(onClick = {
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
                TextButton(onClick = {
                    openDeleteUserDialog.value = true
                    //viewModel.update_task("","bellator87@mail.ru","Valeriy Urich","${profileState.value?.token}")
                },
                    modifier = Modifier.weight(1f)

                ) {
                    Text(text = "ИЗМЕНИТЬ")
                }
                if (openDeleteUserDialog.value) {
                    //Вызываем диалог
//--------------------------------------Alert dialog при редактировании-----------------------------
                    AlertDialog(onDismissRequest = { openDeleteUserDialog.value = false },
                        title = { Text(text = "Редактировать профиль") },
                        text = {
                            Column() {
                                TextField(
                                    value = editNameState.value,
                                    onValueChange = { editNameState.value = it })
                                TextField(
                                    value = editEmailState.value,
                                    onValueChange = { editEmailState.value = it })
                            }

                        },

                        buttons = {
                            Row(
                                modifier = Modifier.padding(all = 8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
//Кнопка редактирования в диалоге-------------------------------------------------------------------------
                                TextButton(
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        viewModel.updateUser(
                                            "",
                                            "${editEmailState.value}",
                                            "${editNameState.value}",
                                            "${profileState.value?.token}")
                                        navController.navigate(route = AppNavHost.News.route)
                                        openDeleteUserDialog.value = false
                                    }
                                ) {
                                    Text("Редактировать")
                                }
//Кнопка отмены редактирования в диалоге------------------------------------------------------------------
                                TextButton(
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        openDeleteUserDialog.value = false
                                        Toast.makeText(context, "Отменить", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                ) {
                                    Text("Отмена")
                                }
                            }

                        }
                    )
//-----------------------Конец Alert Dialog---------------------------------------------------------
                }
//Кнопка выхода из учётной записи-------------------------------------------------------------------------
                TextButton(onClick = {
                    viewModel._profileData.value = null
                       navController.navigate(route = AppNavHost.News.route)
                },
                modifier = Modifier.weight(1f))
                {Text(text = "ВЫХОД") }

            }}
            //Новости пользователя---------------------------------------------------------------------
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .weight(6f)
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(4.dp)
                ) {
                    items(newsState.value, key = { it.id }) {
                        ItemNews(item = it)//it указывает на newsState.value
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
                TextButton(onClick = { navController.navigate(route = AppNavHost.News.route) }) {
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



