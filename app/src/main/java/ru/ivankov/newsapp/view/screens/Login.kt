package ru.ivankov.newsapp.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.*
import ru.ivankov.newsapp.R
import ru.ivankov.newsapp.view.emailValidator
import ru.ivankov.newsapp.view.removeSpace
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.passwordValidator
import ru.ivankov.newsapp.view.ui.theme.BadInput
import ru.ivankov.newsapp.view.ui.theme.GoodInput
import ru.ivankov.newsapp.view.ui.theme.NewsAppTheme
import ru.ivankov.newsapp.viewmodel.NewsViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: NewsViewModel
) {
    val loginPasswordState = remember { mutableStateOf("") }
    val loginEmailState = remember { mutableStateOf("") }
    val loginMessageState = viewModel.loginMessage.observeAsState()

    val isEmailValid = remember { mutableStateOf(false) }
    val isPasswordValid = remember { mutableStateOf(false) }
    val passwordVisibility = remember { mutableStateOf(false) }
    val passwordIcon = if (passwordVisibility.value) painterResource(id = R.drawable.ic_visibility_off)
    else painterResource(id = R.drawable.ic_visibility)

fun loginProfile (){
    // синхронизировать без секундочки
    viewModel.postAuthentification(loginEmailState.value, loginPasswordState.value) {}
    GlobalScope.launch(Dispatchers.Main) {
        delay(1000)
        // одно из решений использовать LaunchEffect на странице news
        viewModel.searchNews(
            1,
            15,
            "${viewModel.profileData.value?.name}",//"${viewModel.profileData.value?.name}",//дать автора
            "",
            listOf()
        )
        if (viewModel.loginMessage.value == "") {
            navController.navigate(route = AppNavHost.MyProfile.route)
        }
    }
}
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${loginMessageState.value}",
            modifier = Modifier.padding(12.dp)
        )
        TextField(
            value = loginEmailState.value,
            onValueChange = {
                loginEmailState.value = removeSpace(it)
                isEmailValid.value = false
            },
            singleLine = true,//в одну линию
            isError = isEmailValid.value,
            label = { Text("Введите email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),//тип клавиатуры для email
            keyboardActions = KeyboardActions(onDone = {
                isEmailValid.value = emailValidator(loginEmailState.value)
                if (isEmailValid.value==true && isPasswordValid.value==true){loginProfile ()}
            }
            ),
            textStyle = TextStyle(fontSize = 16.sp),
            colors = TextFieldDefaults.textFieldColors
                (textColor = Color.Black, backgroundColor =
            if (isEmailValid.value) {
                GoodInput
            } else {
                BadInput
            }
            ),
            modifier = Modifier.padding(12.dp)
        )
        TextField(
            value = loginPasswordState.value,
            onValueChange = { loginPasswordState.value = removeSpace(it) },
            singleLine = true,//в одну линию
            isError = isPasswordValid.value,
            label = { Text("Введите пароль") },
            trailingIcon = { IconButton(onClick = {passwordVisibility.value = ! passwordVisibility.value})
            {Icon(painter = passwordIcon, contentDescription = "видимость пароля")}},
            visualTransformation = if (passwordVisibility.value == true) VisualTransformation.None
            else {PasswordVisualTransformation()},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),//тип клавиатуры для email
            keyboardActions = KeyboardActions(onDone = {
                isPasswordValid.value = passwordValidator(loginPasswordState.value)
                if (isEmailValid.value==true && isPasswordValid.value==true) loginProfile ()
            } ),
            textStyle = TextStyle(fontSize = 16.sp),

            colors = TextFieldDefaults.textFieldColors
                (textColor = Color.Black, backgroundColor =
            if (isPasswordValid.value) {
                GoodInput
            } else {
                BadInput
            }
        ),
            modifier = Modifier.padding(12.dp)
        )
        Row {
            //Кнопка входа
            TextButton(
                onClick = {
                    loginProfile ()
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp)

            ) { Text(text = "ВХОД") }

//Кнопка регистрации
            TextButton(
                onClick = { navController.navigate(route = AppNavHost.RegistrationScreen.route) },
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp)

            ) { Text(text = "РЕГИСТРАЦИЯ") }

//Кнопка возвращения
            TextButton(
                onClick = {
                    viewModel.getNewsList(1)
                    navController.navigate(route = AppNavHost.News.route)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp)

            ) { Text(text = "НАЗАД") }
        }
    }
}
//____________________________________________________________________________________________________
@Preview(showBackground = true)
@Composable
fun PrevLoginScreen() {
    NewsAppTheme {
        LoginScreen(
            navController = rememberNavController(),
            viewModel = NewsViewModel()
        )
    }
}
