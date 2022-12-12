package ru.ivankov.newsapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.model.NewsModel
import ru.ivankov.newsapp.navigation.AppNavHost
import ru.ivankov.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsScreen(navController: NavHostController) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Gray)
            .padding(4.dp)
        ){
        itemsIndexed(
            listOf(
                NewsModel(1,"Миша",1,"Наступила осень","Птицы улетели"),
                NewsModel(1,"Миша",1,"Наступила осень","Птицы улетели"),
                NewsModel(1,"Миша",1,"Наступила осень","Птицы улетели"),
                NewsModel(1,"Миша",1,"Наступила осень","Птицы улетели"),
                NewsModel(1,"Миша",1,"Наступила осень","Птицы улетели"),
                NewsModel(1,"Миша",1,"Наступила осень","Птицы улетели"),
                NewsModel(1,"Миша",1,"Наступила осень","Птицы улетели"),
                NewsModel(1,"Миша",1,"Наступила осень","Птицы улетели"),
                NewsModel(1,"Миша",1,"Наступила осень","Птицы улетели"),
                NewsModel(1,"Миша",1,"Наступила осень","Птицы улетели"),
                NewsModel(1,"Миша",1,"Наступила осень","Птицы улетели"),
            )
        ){ _,item ->
            ItemNews(item = item)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun prevNewsScreen(){
    NewsAppTheme {
        NewsScreen(navController = rememberNavController())

    }
}