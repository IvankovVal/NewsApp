package ru.ivankov.newsapp.view.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import ru.ivankov.newsapp.viewmodel.NewsViewModel

@Composable
fun RegistrationScreen(navController: NavHostController,
                       vmNews: NewsViewModel

) {

    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

// Поле ввода____________________________________________________________________________________
        Box(modifier = Modifier
            .background(Color.Yellow, RectangleShape)
            .fillMaxWidth()
            .weight(3f),
            contentAlignment = Alignment.Center
        ){
            Column {


                TextField(value = "Имя", onValueChange = {},modifier = Modifier.padding(12.dp))
                TextField(value = "Email", onValueChange = {},modifier = Modifier.padding(12.dp))
                TextField(value = "Пароль", onValueChange = {},modifier = Modifier.padding(12.dp))


            }
        }
        Box(modifier = Modifier
            .background(Color.Yellow, RectangleShape)
            .fillMaxWidth()
            .weight(3f),
            contentAlignment = Alignment.Center
        ){
            Column(horizontalAlignment = Alignment.CenterHorizontally

            ) {
//------------------Кнопка регистрации--------------------------------------------------------------
                Button(
                    onClick = {
                        Toast.makeText(context, "Нажато", Toast.LENGTH_LONG).show()
                        vmNews.postRegistration()

                    },
                    modifier = Modifier.padding(12.dp))
                {Text(text = "Зарегистрироваться")}
//------------------Кнопка возврата на стартовую страницу-------------------------------------------
                Button(
                    onClick = { navController.navigate(route = AppNavHost.Start.route)},
                    modifier = Modifier.padding(12.dp))
                {Text(text = "Назад")}

            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun prevRegistrationScreen(){
    NewsAppTheme {
        RegistrationScreen(
            navController = rememberNavController(),
            vmNews = NewsViewModel()

            )

    }
}