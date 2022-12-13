package ru.ivankov.newsapp.screens

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.model.NewsModel
import ru.ivankov.newsapp.ui.theme.NewsAppTheme

@Composable
fun ItemNews(item:NewsModel) {
// -----------------------------------------------------------------------------------
// Карточка пункта списка__________________________________________________________________________________
    Card(

        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .border(2.dp, Color.Black, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth()
    ){
        Column(modifier = Modifier
            .padding(start = 20.dp)) {

            Text(text = "${item.userName}")
            Text(text = "${item.newsId} - ${item.newsTitle}")
            Text(text = "${item.newsDescription}")
        }

    }




// -----------------------------------------------------------------------------------
}
@Preview(showBackground = true)
@Composable
fun prevItemNews(){
    NewsAppTheme {
        ItemNews(item = NewsModel(
            1,
            "Валерий",
            1,
            "Название",
            "Это текст новости"
        )
        )

    }
}
