package ru.ivankov.newsapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.navigation.NavHostController

@Composable
fun Start(navController: NavHostController) {
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


                TextField(value = "email", onValueChange = {},)

                TextField(value = "пароль", onValueChange = {})
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Вход")
                }
            }
        }
// Поле регистрации____________________________________________________________________________________
        Box(modifier = Modifier
            .background(Color.Red, RectangleShape)
            .fillMaxWidth()
            .weight(2f),
            contentAlignment = Alignment.Center
        ){
            Text(text = "Регистрация")
        }

    }
}