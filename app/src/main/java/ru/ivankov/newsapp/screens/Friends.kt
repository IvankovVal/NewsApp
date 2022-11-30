package ru.ivankov.newsapp.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.ui.theme.NewsAppTheme

@Composable
fun FriendsScreen(navController: NavHostController) {
    Text(text = "Friend screen")
}
@Preview(showBackground = true)
@Composable
fun prevFriendsScreen(){
    NewsAppTheme {
        RegistrationScreen(navController = rememberNavController())

    }
}