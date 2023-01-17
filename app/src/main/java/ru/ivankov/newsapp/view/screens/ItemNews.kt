package ru.ivankov.newsapp.view.screens

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ivankov.newsapp.model.NewsContent
import ru.ivankov.newsapp.model.NewsContentTags
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import ru.ivankov.newsapp.viewmodel.NewsViewModel

@Composable
fun ItemNews(
    item: NewsContent,
    viewModel: NewsViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
// -----------------------------------------------------------------------------------
// Карточка пункта списка__________________________________________________________________________________
    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
        modifier = Modifier
            .clickable {
                if (viewModel.profileData.value != null) {
                    //взять из новости userId, добавить в качесве параметра в фукцию поиска поль-ля по Id
                    //Полученые данные положить в VM.user и перейдя в Профиль отобразить их
                    viewModel.getUserInfoById(item.userId)
                    // тоже что в login
                    GlobalScope.launch(Dispatchers.Main) {
                        delay(1000)
//                        Log.d(ContentValues.TAG,"Значение профиля - ${viewModel.profileData.value?.name}")
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
            .padding(start = 5.dp, end = 5.dp)
            .border(2.dp, Color.Black, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp)
        ) {

            AsyncImage(
                model = "${item.image}",//profileState.value?.avatar,
                contentDescription = "Картиночка",
                contentScale = ContentScale.Crop,
                modifier = Modifier
//                    .size(120.dp)
//                    .clip(CircleShape))
            )
            Text(text = "${item.username}")
            Text(text = "${item.id} - ${item.title}")
            Text(text = "${item.description}")
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
// -----------------------------------------------------------------------------------
// Карточка пункта списка__________________________________________________________________________________
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
        ) {

            AsyncImage(
                model = "${item.image}",//profileState.value?.avatar,
                contentDescription = "Картиночка",
                contentScale = ContentScale.Crop,
                modifier = Modifier
            )
            Text(text = "${item.username}")
            Text(text = "${item.id} - ${item.title}")
            Text(text = "${item.description}")
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
