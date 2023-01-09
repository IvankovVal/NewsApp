package ru.ivankov.newsapp.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.ivankov.newsapp.view.screens.*
import ru.ivankov.newsapp.viewmodel.NewsViewModel

sealed class AppNavHost(val route: String) {
    object Login: AppNavHost("start_screen")
    object Registration: AppNavHost("registration_screen")
    object MyProfile: AppNavHost("myProfile_screen")
    object News: AppNavHost("news_screen")
    object Friend: AppNavHost("friend_screen")

}

@Composable
fun AppNavHost(mViewModel: NewsViewModel, navController: NavHostController) {
    val navController = rememberNavController()
//в параметры NavHost передать navController, который создан выше и наш стартовый экран
    NavHost(navController = navController, startDestination = AppNavHost.News.route) {
//route означает ссылка, в фигурных скобках задаём то, куда она ведёт destination
        composable(AppNavHost.MyProfile.route){ ProfileScreen(navController = navController,viewModel = mViewModel )}
        composable(AppNavHost.Friend.route){ FrendScreen(navController = navController,viewModel = mViewModel )}
        composable(AppNavHost.News.route){ NewsScreen(navController = navController,viewModel = mViewModel)}
        composable(AppNavHost.Login.route){ LoginScreen(navController = navController,viewModel = mViewModel)}
        composable(AppNavHost.Registration.route){ RegistrationScreen(navController = navController,viewModel = mViewModel) }

    }
}