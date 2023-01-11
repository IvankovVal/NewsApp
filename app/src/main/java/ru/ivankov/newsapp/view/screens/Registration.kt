package ru.ivankov.newsapp.view.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
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
import coil.compose.AsyncImage
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
    val registrationAvatarState = remember { mutableStateOf("") }
    val requestState = remember { mutableStateOf("") }
    //для аватара
    /*(1)Нам нужно отслеживать возвращенный URI,
   поэтому мы определим переменную , которая будет содержать это значение,
    которое мы инициализируем как null*/
    var imageUri by remember { mutableStateOf<Uri?>(null)}

/*(2)вторая переменная, Boolean,сообщит нам, есть ли у нас URI для отображения или нет*/
    var hasImage by remember { mutableStateOf(false) }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> /*(3)Когда мы получаем ответ от средства выбора файлов,
         мы сохраняем возвращенный URI и устанавливаем логическое значение,
          указывающее, что у нас есть действительный URI, если он не равен нулю.*/
            hasImage = uri != null
            imageUri = uri
        }
    )


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
            // (4) проверяем, есть ли у нас действительное изображение для отображения.
            if (hasImage && imageUri != null) {
                // (5) Если есть,то отображаем изображение
                AsyncImage(
                    model = imageUri,
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = "Selected image",
                )
                // (6) тут нужно запустить запрос на добавление этого файла на сервер
                //ответ сервера добавить в состояние аватара (registrationAvatarState)
                //и значение этого состояния добавить в запрос на регистрацию

            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    onClick = {
                        imagePicker.launch("image/*")
                        /*Если мы запустим это, запустится средство выбора изображений,
                         и мы сможем выбрать изображение, но ничего не произойдет,
                          когда мы вернемся в наше приложение*/

                    },
                ) {
                    Text(
                        text = "Выбрать аватар"
                    )
                }
            }
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
                    label = { Text("Ввести имя") },
                    modifier = Modifier.padding(12.dp)
                )
                TextField(
                    value = registrationEmailState.value,
                    onValueChange = { registrationEmailState.value = it },
                    label = { Text("Ввести email") },
                    modifier = Modifier.padding(12.dp)
                )
                TextField(
                    value = registrationPasswordState.value,
                    onValueChange = { registrationPasswordState.value = it },
                    label = { Text("Ввести пароль") },
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