package ru.ivankov.newsapp.view.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ivankov.newsapp.view.removeSpace
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
    val profileState = viewModel.profileData.observeAsState()
    val pageState = viewModel.pageAmount.observeAsState()
    //состояние диалогов
    val openFindNewsDialog = remember { mutableStateOf(false) }
    val openCreateNewsDialog = remember { mutableStateOf(false) }
    //состояние полей при поиске
    val editAuthorState = remember { mutableStateOf("") }
    val editWordState = remember { mutableStateOf("") }
    val editTagState = remember { mutableStateOf("") }
    //состояние полей при создании новости
    val editTitleState = remember { mutableStateOf("") }
    val editDescriptionState = remember { mutableStateOf("") }
    val editTagsOnCreateState = remember { mutableStateOf("") }

    //Расположим карточки с помощью ConstrainLayout
    Column(modifier = Modifier.fillMaxSize()) {//основная колонка содержащая все элементы
        // -------------------------Строкав страниц_______________________________________________


        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth()

        ) {
            items(count = pageState.value!!.toInt())
            {
                Text(
                    text = "стр ${it + 1}",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable { viewModel.getNewsList(it + 1) }
                        .padding(8.dp)
                )
            }
        }


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
                items(newsState.value, key = { it.id }) {
                    ItemNews(item = it,viewModel, navController)//it указывает на newsState.value
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
                        if (profileState.value == null) {
                            navController.navigate(route = AppNavHost.Login.route)
                        } else{
                            GlobalScope.launch(Dispatchers.Main) {
                                delay(1000)
                                viewModel.searchNews(
                                    1,
                                    15,
                                    "${viewModel.profileData.value?.name}",//"${viewModel.profileData.value?.name}",//дать автора
                                    "",
                                    emptyList()
                                )
                                navController.navigate(route = AppNavHost.MyProfile.route)}
                            }
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
//Кнопка добавить запись---------------------------------------
                IconButton(
                    onClick = {
                        if(profileState.value != null)
                            navController.navigate(route = AppNavHost.AddNewsScreen.route)
                        else
                    Toast.makeText(context, "Войдите в профиль", Toast.LENGTH_LONG).show()
                    },
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Добавить запись",
                        )
                }
//----------------------Кнопка поиск---------------------------------------
                IconButton(
                    onClick = { openFindNewsDialog.value = true },
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Поиск",
                        )
                }
                if (openFindNewsDialog.value) {
                    //Вызываем диалог
//--------------------------------------Alert dialog при поиске новости-----------------------------
                    AlertDialog(onDismissRequest = { openFindNewsDialog.value = false },
                        title = { Text(text = "Найти новость") },
                        text = {
                            Column() {
                                TextField(
                                    value = editAuthorState.value,
                                    onValueChange = { editAuthorState.value = removeSpace(it) },
                                    label = { Text("Автор") })
                                TextField(
                                    value = editWordState.value,
                                    onValueChange = { editWordState.value = removeSpace(it) },
                                    label = { Text("Ключевые слова") })
                                TextField(
                                    value = editTagState.value,
                                    onValueChange = { editTagState.value = removeSpace(it) },
                                    label = { Text("tags") })
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
                                        viewModel.searchNews(
                                            1,
                                            15,
                                            editAuthorState.value,
                                            editWordState.value,
                                            emptyList()
                                        )
                                        navController.navigate(route = AppNavHost.News.route)
                                        openFindNewsDialog.value = false
                                    }
                                ) {
                                    Text("Искать")
                                }
//Кнопка отмены редактирования в диалоге------------------------------------------------------------------
                                TextButton(
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        openFindNewsDialog.value = false
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
            }
        }
//-------------------------------------------------------------------------------------------------         }
    }
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

