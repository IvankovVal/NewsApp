package ru.ivankov.newsapp.screens

import android.graphics.drawable.Drawable
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
import ru.ivankov.newsapp.model.NewsModel
import ru.ivankov.newsapp.navigation.AppNavHost
import ru.ivankov.newsapp.ui.theme.NewsAppTheme

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
                        NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                        NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                        NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                        NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                        NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                        NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                        NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                        NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                        NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                        NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                        NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
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
/*
@Composable
fun TwoCards() {
// Карточка для поиска новостей
    Card(

        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
        modifier = Modifier
            .padding(start = 5.dp, top = 5.dp, end = 5.dp)
            .border(2.dp, Color.DarkGray, shape = RoundedCornerShape(20.dp))
    ) {
        Button(onClick = { //Вызов диалога для поиска новости
                         },
            modifier = Modifier.padding(30.dp)
        ) {Text(text = "Поиск новостей")}

    }
    // Карточка для  отображения новостей
    Card(

        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
        modifier = Modifier
            .padding(start = 5.dp, top = 5.dp, end = 5.dp)
            .border(2.dp, Color.Black, shape = RoundedCornerShape(20.dp))
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
                    NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                    NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                    NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                    NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                    NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                    NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                    NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                    NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                    NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                    NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                    NewsModel(1, "Миша", 1, "Наступила осень", "Птицы улетели"),
                )
            ) { _, item ->
                ItemNews(item = item)
            }
        }

    }

}}
*/