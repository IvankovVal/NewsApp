package ru.ivankov.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.screens.*

sealed class AppNavHost(val route: String) {
    object Start: AppNavHost("start_screen")
    object Registration: AppNavHost("registration_screen")
    object Enter: AppNavHost("enter_screen")
    object MyProfile: AppNavHost("myProfile_screen")
    object News: AppNavHost("news_screen")
    object Friend: AppNavHost("friend_screen")

}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
//в параметры NavHost передать navController, который создан выше и наш стартовый экран
    NavHost(navController = navController, startDestination = AppNavHost.Start.route) {
//route означает ссылка, в фигурных скобках задаём то, куда она ведёт destination
        composable(AppNavHost.Start.route){ Start(navController = navController)}
        composable(AppNavHost.Registration.route){ Registration(navController = navController)}
        composable(AppNavHost.Enter.route){ Enter(navController = navController)}
        composable(AppNavHost.MyProfile.route){ MyProfile(navController = navController)}
        composable(AppNavHost.News.route){ News(navController = navController)}
        composable(AppNavHost.Friend.route){ Friend(navController = navController)}

    }
}