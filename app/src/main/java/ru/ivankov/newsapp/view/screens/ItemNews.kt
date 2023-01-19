package ru.ivankov.newsapp.view.screens

import android.graphics.fonts.Font
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ivankov.newsapp.model.NewsContent
import ru.ivankov.newsapp.model.NewsContentTags
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.ui.theme.BadInput
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import ru.ivankov.newsapp.viewmodel.NewsViewModel

@Composable
fun ItemNews(
    item: NewsContent,
    viewModel: NewsViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val isDeleted = remember { mutableStateOf(false)}
// -----------------------------------------------------------------------------------
// Карточка пункта списка__________________________________________________________________________________
    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
        modifier = Modifier
            .background(
                color = if (isDeleted.value) BadInput
                else Color.White
            )
            .clickable(enabled = !isDeleted.value) {
                if (viewModel.profileData.value != null) {
                    //взять из новости userId, добавить в качесве параметра в фукцию поиска поль-ля по Id
                    //Полученые данные положить в VM.user и перейдя в Профиль отобразить их
                    viewModel.getUserInfoById(item.userId)
                    // тоже что в login
                    GlobalScope.launch(Dispatchers.Main) {
                        delay(1000)
                        viewModel.searchNews(
                            1,
                            15,
                            "${item.username}",//"${viewModel.profileData.value?.name}",//дать автора
                            "",
                            emptyList()
                        )
                        navController.navigate(route = AppNavHost.Friend.route)
                    }
                } else Toast
                    .makeText(context, "Войдите в профиль", Toast.LENGTH_LONG)
                    .show()
            }
            .padding(horizontal = 5.dp, vertical = 3.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = if (isDeleted.value) BadInput
                else Color.White)
        ) {
            AsyncImage(
                model = "${item.image}",//profileState.value?.avatar,
                contentDescription = "Картиночка",
                contentScale = ContentScale.Crop,
                modifier = Modifier
            )
//Id и название новости
            Text(text = "${item.id} - ${item.title}",fontSize = 20.sp,fontStyle = FontStyle.Normal)
// Содержание новости
            Text(text = "${item.description}")
// Автор новости
            Text(text = "${item.username}", fontStyle = FontStyle.Italic)
// Тэги
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 20.dp)
            ) {
                for (i in item.tags) {
                    Text(
                        text = "#${i.title} ",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace,
                        fontStyle = FontStyle.Italic,
                    )
                }
            }
            if (item.username == viewModel.profileData.value?.name){
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 20.dp)
                ) {
                    //Кнопка удаления новости
                    TextButton(
                        onClick = {
                            isDeleted.value = true
                            viewModel.deleteNews(item.id)
                                  },
                        enabled = !isDeleted.value,
                        modifier = Modifier
                            .weight(1f)
                    ) { Text(text = "УДАЛИТЬ", fontSize = 12.sp) }
                    //Кнопка РЕДАКТИРОВАНИЯ новости
                    TextButton(
                        onClick = {
                            viewModel._editableNews.value = item.id
                            navController.navigate(route = AppNavHost.EditNewsScreen.route)

                                  },
                        enabled = !isDeleted.value,
                        modifier = Modifier
                            .weight(1f)

                    ) { Text(text = "РЕДАКТИРОВАТЬ", fontSize = 12.sp) }
                }
            }
        }
    }
}
//_______Для новостей в профиле____________________
@Composable
fun ItemNewsProfile(
    item: NewsContent,
    viewModel: NewsViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val isDeleted = remember { mutableStateOf(false)}

// -----------------------------------------------------------------------------------
// Карточка пункта списка____________________________________________________________________________
    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .border(2.dp, Color.Black, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp)
                .background(
                    color = if (isDeleted.value) BadInput
                    else Color.White
                )
        ) {

            AsyncImage(
                model = item.image,//profileState.value?.avatar,
                contentDescription = "Картиночка",
                contentScale = ContentScale.Crop,
                modifier = Modifier
            )
            // Автор новости
            Text(text = item.username)
//Id новости и название
            Text(text = "${item.id} - ${item.title}",fontSize = 20.sp,fontStyle = FontStyle.Normal)
// Содержание новости
            Text(text = item.description)
// Тэги
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 20.dp)
            ) {
                for (i in item.tags) {
                    Text(
                        text = "#${i.title} ",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace,
                        fontStyle = FontStyle.Italic,
                    )
                }
            }
            if (item.username == viewModel.profileData.value!!.name){
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 20.dp)
                ) {
                    //Кнопка удаления новости
                    TextButton(
                        onClick = {
                            isDeleted.value = true
                            viewModel.deleteNews(item.id)
                        },
                        enabled = !isDeleted.value,
                        modifier = Modifier
                            .weight(1f)

                    ) { Text(text = "УДАЛИТЬ", fontSize = 12.sp) }
                    //Кнопка РЕДАКТИРОВАНИЯ новости
                    TextButton(
                        onClick = {
                            navController.navigate(route = AppNavHost.EditNewsScreen.route)
                        },
                        enabled = !isDeleted.value,
                        modifier = Modifier
                            .weight(1f)

                    ) { Text(text = "РЕДАКТИРОВАТЬ", fontSize = 12.sp) }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun prevItemNews(){
//    NewsAppTheme {
//        ItemNews(item = NewsContent(
//            "Какая-то новость",
//            1,
//            "",
//            NewsContentTags(1,"Tag"),
//            "Название",
//            "ЮID",
//            "Имя пользователя"
//        )
//
//
//    }
//}
