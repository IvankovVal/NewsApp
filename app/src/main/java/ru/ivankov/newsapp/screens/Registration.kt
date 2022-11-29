package ru.ivankov.newsapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.ui.theme.NewsAppTheme

@Composable
fun RegistrationScreen(navController: NavHostController) {
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


                TextField(value = "Имя", onValueChange = {},)
                TextField(value = "Фамилия", onValueChange = {},)
                TextField(value = "email", onValueChange = {},)
                TextField(value = "пароль", onValueChange = {})
                Text(text = "А ещё как-то аватар нужно")


            }
        }
        Box(modifier = Modifier
            .background(Color.Yellow, RectangleShape)
            .fillMaxWidth()
            .weight(3f),
            contentAlignment = Alignment.Center
        ){
            Row {


                Button(onClick = { /*TODO*/ }) {Text(text = "Назад")}
                Button(onClick = { /*TODO*/ }) {Text(text = "Зарегистрироваться")}

            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun prevRegistrationScreen(){
    NewsAppTheme {
        RegistrationScreen(navController = rememberNavController())

    }
}