package ru.ivankov.newsapp.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme

@Composable
fun StartScreen(navController: NavHostController) {
    val context = LocalContext.current

    //присваеваем изменяемое состояние текстового поля
    val email = remember { mutableStateOf(TextFieldValue())}
    val emailErrorState = remember { mutableStateOf(false)}

    val password = remember { mutableStateOf(TextFieldValue())}
    val passwordErrorState = remember { mutableStateOf(false)}
   Scaffold(
       modifier = Modifier.fillMaxSize()
   ) {
       Column(
           modifier = Modifier.fillMaxSize(),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center
       ) {
           TextField(
               value = "email", onValueChange = {},
               modifier = Modifier.padding(12.dp)
               )
           TextField(
               value = "пароль", onValueChange = {},
               modifier = Modifier.padding(12.dp)
               )
//Кнопка входа
           Button(onClick = { navController.navigate(route = AppNavHost.MyProfile.route)},
               modifier = Modifier.padding(30.dp)
               ) {Text(text = "Вход")}
//Кнопка регистрации
           Button(onClick = { navController.navigate(route = AppNavHost.Registration.route)},
               modifier = Modifier.padding(30.dp)
           ) {Text(text = "Регистрация")}

           
       }
   }
}
//____________________________________________________________________________________________________
@Preview(showBackground = true)
@Composable
fun prevStartScreen(){
    NewsAppTheme {
        StartScreen(navController = rememberNavController())

    }
}
//Column(
//horizontalAlignment = Alignment.CenterHorizontally,
//verticalArrangement = Arrangement.Center
//) {
//
//// Поле ввода____________________________________________________________________________________
//    Box(modifier = Modifier
//        .background(Color.Yellow, RectangleShape)
//        .fillMaxWidth()
//        .weight(3f),
//        contentAlignment = Alignment.Center
//    ){
//        Column {
//
//
//            TextField(value = "email", onValueChange = {},)
//
//            TextField(value = "пароль", onValueChange = {})
//            Button(onClick = { /*TODO*/ }) {
//                Text(text = "Вход")
//            }
//        }
//    }
//// Поле регистрации____________________________________________________________________________________
//    Box(modifier = Modifier
//        .background(Color.Red, RectangleShape)
//        .fillMaxWidth()
//        .weight(2f),
//        contentAlignment = Alignment.Center
//    ){
//        Text(text = "Регистрация")
//    }
//
//}