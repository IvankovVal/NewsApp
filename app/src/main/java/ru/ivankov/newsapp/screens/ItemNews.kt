package ru.ivankov.newsapp.screens

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.ivankov.newsapp.model.NewsModel

@Composable
fun ItemNews(item:NewsModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White)
            .padding(3.dp)

        ) {
        //Название новости
        Text(text = item.newsTitle,
            fontWeight = FontWeight.Bold

            )
        //Содержание новости
        Text(text = item.newsDescription)

    }
}
//ItemNews(
//responseData = it,
//MakeToast,
//navController,
//alertDialogTrigger,
//perPage,
//permission_dialogue
//)