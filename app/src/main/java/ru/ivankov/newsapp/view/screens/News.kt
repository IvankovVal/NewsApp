package ru.ivankov.newsapp.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.model.NewsContent
import ru.ivankov.newsapp.model.NewsContentTags
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme

@Composable
fun NewsScreen(navController: NavHostController) {
//Расположим карточки с помощью ConstrainLayout
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (newsListCard, buttonsFieldCard) = createRefs()

// Карточка для  отображения новостей---------------------------------------------------------------
        Card(

            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp,
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .border(2.dp, Color.Black, shape = RoundedCornerShape(15.dp))
//               .fillMaxSize()
                .constrainAs(newsListCard) {
                    start.linkTo(parent.start, 4.dp)
                    end.linkTo(parent.end, 4.dp)
                    top.linkTo(parent.top, 4.dp)
                    bottom.linkTo(buttonsFieldCard.top, 4.dp)
                }
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Gray)
                    .padding(4.dp)
            ) {
                itemsIndexed(
                    listOf(
                        NewsContent ("Какая-то новость", 1, "", NewsContentTags(1,"Tag"), "Название","ЮID", "Имя пользователя"),
                        NewsContent ("Какая-то новость", 1, "", NewsContentTags(1,"Tag"), "Название","ЮID", "Имя пользователя"),
                        NewsContent ("Какая-то новость", 1, "", NewsContentTags(1,"Tag"), "Название","ЮID", "Имя пользователя"),
                        NewsContent ("Какая-то новость", 1, "", NewsContentTags(1,"Tag"), "Название","ЮID", "Имя пользователя"),
                        NewsContent ("Какая-то новость", 1, "", NewsContentTags(1,"Tag"), "Название","ЮID", "Имя пользователя"),
                        NewsContent ("Какая-то новость", 1, "", NewsContentTags(1,"Tag"), "Название","ЮID", "Имя пользователя"),
                        NewsContent ("Какая-то новость", 1, "", NewsContentTags(1,"Tag"), "Название","ЮID", "Имя пользователя"),
                        NewsContent ("Какая-то новость", 1, "", NewsContentTags(1,"Tag"), "Название","ЮID", "Имя пользователя"),
                        NewsContent ("Какая-то новость", 1, "", NewsContentTags(1,"Tag"), "Название","ЮID", "Имя пользователя"),
                        NewsContent ("Какая-то новость", 1, "", NewsContentTags(1,"Tag"), "Название","ЮID", "Имя пользователя"),
                        NewsContent ("Какая-то новость", 1, "", NewsContentTags(1,"Tag"), "Название","ЮID", "Имя пользователя"),
                        NewsContent ("Какая-то новость", 1, "", NewsContentTags(1,"Tag"), "Название","ЮID", "Имя пользователя"),
                        NewsContent ("Какая-то новость", 1, "", NewsContentTags(1,"Tag"), "Название","ЮID", "Имя пользователя"),

                    )
                ) { _, item ->
                    ItemNews(item = item)
                }
            }

        }
// Карточка для кнопок__________________________________________________________________________________
        Card(

            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp,
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .border(2.dp, Color.Black, shape = RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .constrainAs(buttonsFieldCard) {
                    bottom.linkTo(parent.bottom, 4.dp)
                    start.linkTo(parent.start, 4.dp)
                    end.linkTo(parent.end, 4.dp)
                }
        ) {
//-------------------------------------------------------------------------------------------------
            Row {


                //Кнопка закрыть
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
                    ) {
                    Icon(imageVector = Icons.Default.Close,
                        contentDescription = "Закрыть",

                    )

                }
 //Кнопка профиль
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Person,
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
                    Icon(imageVector = Icons.Default.Add,
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
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = "Поиск",

                        )

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
        NewsScreen(navController = rememberNavController())

    }
}
