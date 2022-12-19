
package ru.ivankov.newsapp.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import ru.ivankov.newsapp.model.DataLoginResponse
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import ru.ivankov.newsapp.viewmodel.NewsViewModel

@Composable
fun MyProfileScreen(
    navController: NavHostController,
    vmNews: NewsViewModel
) {
    val context = LocalContext.current
    //profileData - состояние страницы. При его изменении должна происходить рекомпозиция
    val profileState = vmNews.profileData?.observeAsState(DataLoginResponse(
        avatar = "",
        email = "no email",
        id = "no id",
        "no name",
        "no role",
        "no token"
    ))
    val userNewsState = vmNews.newsList.observeAsState() //Добавить позже фильтр на пользовательские новости
    //основной контейнер(похоже можно было не создавать)
    androidx.compose.material.Surface(color = Color.White) {
        //Задаём компановку
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (userAvatar,userEmail,userName, userNews,) = createRefs()
            //Аватар
            Image(painter = painterResource(R.drawable.ic_default_avatar),
                contentDescription = "Аватар пользователя",
            modifier = Modifier.constrainAs(userAvatar){
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start,16.dp)
            })
//Имя пользователя--------------------------------------------------------------
            Text(
                text = "${profileState?.value?.name}",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.constrainAs(userName){
                    top.linkTo(userAvatar.bottom, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                }
                )
//Email пользователя------------------------------------------------------------
            Text(
                text = "${profileState?.value?.email}",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.constrainAs(userEmail){
                    top.linkTo(userAvatar.bottom, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                }
            )
//Поле для новостей пользователя-------------------------------------------
            Card (
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.Cyan,
                border = BorderStroke(2.dp, Color.Gray),
                modifier = Modifier.constrainAs(userNews){
                    top.linkTo(userAvatar.bottom,16.dp)
                    bottom.linkTo(parent.bottom,16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

                ){
//Список новостей пользователя-------------------------------------------
                //Атрибуты списка
//                LazyColumn(contentPadding = PaddingValues(
//                    top = 16.dp,
//                    start = 8.dp,
//                    end = 8.dp,
//                    bottom = 72.dp
//                ),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//                    )
//                //Содержание списка
//                {
//                    items(
//
//                    )
//                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prevMyProfileScreen(){
    NewsAppTheme {
        MyProfileScreen(
            navController = rememberNavController(),
            vmNews = NewsViewModel()
        )

    }
}