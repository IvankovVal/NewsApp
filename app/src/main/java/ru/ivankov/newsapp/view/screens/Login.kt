package ru.ivankov.newsapp.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.*
import ru.ivankov.newsapp.view.removeSpace
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import ru.ivankov.newsapp.viewmodel.NewsViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: NewsViewModel
) {
    val context = LocalContext.current
    val loginPasswordState = remember { mutableStateOf("") }
    val loginEmailState = remember { mutableStateOf("") }
    //authorizationResponse - состояние страницы. При его изменении должна происходить рекомпозиция
    //val enterState = vmNews.profileData?.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = loginEmailState.value,
            onValueChange = { loginEmailState.value = removeSpace(it) },
            label = { Text("Enter email") })

        TextField(
            value = loginPasswordState.value,
            onValueChange = { loginPasswordState.value = removeSpace(it) },
            label = { Text("Enter password") })
        Row {
            //Кнопка входа
            TextButton(
                onClick = {
                    viewModel.postAuthentification(loginEmailState.value, loginPasswordState.value)
                   GlobalScope.launch(Dispatchers.Main) {
                       delay(1000)
//                        Log.d(ContentValues.TAG,"Значение профиля - ${viewModel.profileData.value?.name}")
                        viewModel.searchNews(
                            1,
                            15,
                             "${viewModel.profileData.value?.name}",//"${viewModel.profileData.value?.name}",//дать автора
                            "",
                            emptyList()

                        )
                        navController.navigate(route = AppNavHost.MyProfile.route)
                    }

                },
                modifier = Modifier.weight(1f)
            ) { Text(text = "ВХОД") }


//Кнопка регистрации
            TextButton(
                onClick = { navController.navigate(route = AppNavHost.Registration.route) },
                modifier = Modifier.weight(1f)
            ) { Text(text = "РЕГИСТРАЦИЯ") }

//Кнопка возвращения
            TextButton(
                onClick = {
                    viewModel.getNewsList(1)
                    navController.navigate(route = AppNavHost.News.route)
                          },
                modifier = Modifier.weight(1f)
            ) { Text(text = "НАЗАД") }

        }
    }


}

//____________________________________________________________________________________________________
@Preview(showBackground = true)
@Composable
fun prevLoginScreen() {
    NewsAppTheme {
        LoginScreen(
            navController = rememberNavController(),
            viewModel = NewsViewModel()
        )

    }
}
