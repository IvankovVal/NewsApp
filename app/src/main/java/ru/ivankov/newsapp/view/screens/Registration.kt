package ru.ivankov.newsapp.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun RegistrationScreen(
    navController: NavHostController,
    viewModel: NewsViewModel

) {

    val context = LocalContext.current
    val profileState = viewModel.profileData.observeAsState()
    val registrationNameState = remember { mutableStateOf("") }
    val registrationEmailState = remember { mutableStateOf("") }
    val registrationPasswordState = remember { mutableStateOf("") }
    val requestState = remember { mutableStateOf("") }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

// Поле для выбора аватара__________________________________________________________________________
        Box(
            modifier = Modifier
                .background(Color.White, RectangleShape)
                .fillMaxWidth()
                .weight(3f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "$requestState" )
        }
// Поле имени, email и пароля_______________________________________________________________________
        Box(
            modifier = Modifier
                .background(Color.White, RectangleShape)
                .fillMaxWidth()
                .weight(3f),
            contentAlignment = Alignment.Center
        ) {
            Column {

                TextField(
                    value = registrationNameState.value,
                    onValueChange = { registrationNameState.value = it },
                    label = { Text("Enter name") },
                    modifier = Modifier.padding(12.dp)
                )
                TextField(
                    value = registrationEmailState.value,
                    onValueChange = { registrationEmailState.value = it },
                    label = { Text("Enter email") },
                    modifier = Modifier.padding(12.dp)
                )
                TextField(
                    value = registrationPasswordState.value,
                    onValueChange = { registrationPasswordState.value = it },
                    label = { Text("Enter password") },
                    modifier = Modifier.padding(12.dp)
                )

            }
        }
        Box(
            modifier = Modifier
                .background(Color.White, RectangleShape)
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Row(

            ) {
//------------------Кнопка регистрации--------------------------------------------------------------
                TextButton(
                    onClick = {
                        viewModel.postRegistration(
                            "any",
                            registrationEmailState.value,
                            registrationNameState.value,
                            registrationPasswordState.value
                        )
                        requestState.value = "${registrationEmailState}/${registrationNameState}/${registrationPasswordState}"
                              navController.navigate(route = AppNavHost.Login.route)
                    },
                    modifier = Modifier.padding(12.dp)
                )
                { Text(text = "Зарегистрироваться") }
//------------------Кнопка возврата на стартовую страницу-------------------------------------------
                TextButton(
                    onClick = { navController.navigate(route = AppNavHost.Login.route) },
                    modifier = Modifier.padding(12.dp)
                )
                { Text(text = "Назад") }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prevRegistrationScreen() {
    NewsAppTheme {
        RegistrationScreen(
            navController = rememberNavController(),
            viewModel = NewsViewModel()
        )

    }
}