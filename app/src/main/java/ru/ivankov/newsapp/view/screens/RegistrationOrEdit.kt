package ru.ivankov.newsapp.view.screens


import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.ivankov.newsapp.view.emailValidator
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.removeSpace
import ru.ivankov.newsapp.view.ui.theme.BadInput
import ru.ivankov.newsapp.view.ui.theme.GoodInput
import ru.ivankov.newsapp.viewmodel.NewsViewModel
import java.io.File
import java.io.InputStream

@Composable
fun RegistrationOrEditScreen(
    navController: NavHostController,
    viewModel: NewsViewModel,
    conRezolver: ContentResolver,
    isRegistration: Boolean

) {

    val context = LocalContext.current
    val profileState = viewModel.profileData.observeAsState()
    val messageState = viewModel.registrationMessage.observeAsState()
    val registrationNameState = remember { mutableStateOf("") }
    val registrationEmailState = remember { mutableStateOf("") }
    val isEmailValid = remember { mutableStateOf(false) }
    val tfColor = if (isEmailValid.value) {
        GoodInput
    } else {
        BadInput
    }
    val registrationPasswordState = remember { mutableStateOf("") }
    val registrationAvatarState = remember { mutableStateOf("") }
    val requestState = remember { mutableStateOf("") }
    //для аватара
    /*(1)Нам нужно отслеживать возвращенный URI,
   поэтому мы определим переменную , которая будет содержать это значение,
    которое мы инициализируем как null*/
    var imageUri by remember { mutableStateOf<Uri?>(null) }

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

    //Расширение класса Uri для добавления метода toImageFile
    fun Uri.toImageFile(context: Context): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(
            this,
            filePathColumn, null, null, null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val filePath = cursor.getString(columnIndex)
                cursor.close()
                return File(filePath)
            }
            cursor.close()
        }
        return null
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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


            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                TextButton(
                    onClick = {
                        imagePicker.launch("image/*")
                    },
                ) {
                    Text(
                        text = "Выбрать аватар"
                    )
                }
// --------------------Кнопка сохранить аватар
                TextButton(
                    onClick = {
                        // (6) тут нужно запустить запрос на добавление этого файла на сервер
                        //ответ сервера добавить в состояние аватара (registrationAvatarState)
                        //и значение этого состояния добавить в запрос на регистрацию

                        //функция расширения для получения имени файла
                        fun ContentResolver.getFileName(fileUri: Uri): String {
                            var name = ""
                            val returnCursor = this.query(fileUri, null, null, null, null)
                            if (returnCursor != null) {
                                val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                                returnCursor.moveToFirst()
                                name = returnCursor.getString(nameIndex)
                                returnCursor.close()
                            }
                            return name
                        }
                        //(1)Из Uri в файл
                        val myStream: InputStream? = conRezolver.openInputStream(imageUri!!)//это не файл
                        val file: File = createTempFile()
                        myStream.use{input -> file.outputStream().use{output -> input!!.copyTo(output)}}

                        val myFileName = conRezolver.getFileName(imageUri!!)//имя приходит
                        Log.d("File ", "имя файла - $myFileName")
                        //     Log.d("File ", "имя файла - ${myFile. }")

                        //(2)Создать отправляемый файл
                        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                        //(2)Сформировать мультипарт
                        val filePart = MultipartBody.Part.createFormData(
                            "file",
                            " $myFileName",
                            requestBody)

//(3)Передать мультипарт методу запроса на загрузку файла и запустить его
                        //  suspend {
                        viewModel.uploadAvatar(filePart)//передаём полученный объект для отправки на сервер в соответствующий метод
                        //  delay(1000)
                        // }
                        Log.d("ava", "в аватаре - ${viewModel.gettedAvatar.value}")

                    },
                ) {
                    Text(text = "Сохранить")
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
                    onValueChange = { registrationNameState.value = removeSpace(it) },
                    singleLine = true,//в одну линию
                    label = { Text("Ввести имя") },
                    keyboardOptions = KeyboardOptions(KeyboardCapitalization.Words),//Каждое слово в поле с большой буквы
                    modifier = Modifier.padding(12.dp)
                )
                TextField(
                    value = registrationEmailState.value,
                    onValueChange = {
                        registrationEmailState.value = removeSpace(it)
                        isEmailValid.value = false
                    },
                    singleLine = true,//в одну линию
                    isError = isEmailValid.value,
                    label = { Text("Ввести email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),//тип клавиатуры для email
                    keyboardActions = KeyboardActions(onDone = {
                        isEmailValid.value =
                            emailValidator(registrationEmailState.value)
                    }
                    ),
                    textStyle = TextStyle(fontSize = 16.sp),
                    colors = TextFieldDefaults.textFieldColors
                        (textColor = Color.Black, backgroundColor = tfColor),
                    modifier = Modifier.padding(12.dp)

                )
                TextField(
                    value = registrationPasswordState.value,
                    onValueChange = { registrationPasswordState.value = removeSpace(it) },
                    singleLine = true,//в одну линию
                    label = { Text("Ввести пароль") },
                    modifier = Modifier.padding(12.dp)
                )

            }
        }
//--------------выбор нижнего бокса в зависимости от того для чего используется функция-------------
        if (isRegistration){
// Box  при регистрации----------------
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
                            avatar = "${viewModel.gettedAvatar.value}",
                            registrationEmailState.value,
                            registrationNameState.value,
                            registrationPasswordState.value,
                            role = "user"
                        )
                        Log.d("inReg", "Значение -  ${viewModel.gettedAvatar.value}")
                        requestState.value = "${registrationEmailState}/${registrationNameState}/${registrationPasswordState}"
                        if(messageState.value == ""){
                            Toast.makeText(context, "Успешно", Toast.LENGTH_LONG).show()
                            navController.navigate(route = AppNavHost.Login.route) }
                    },
                    modifier = Modifier.padding(vertical = 12.dp).weight(1f))
                { Text(text = "Зарегистрироваться") }
//------------------Кнопка возврата на стартовую страницу-------------------------------------------
                TextButton(
                    onClick = { navController.navigate(route = AppNavHost.Login.route) },
                    modifier = Modifier.padding(vertical = 12.dp).weight(1f))
                { Text(text = "Назад") }
            }
        }
// ---------------Иначе редактирование профиля------------------------------------------------------
    }
    else{
        Box() { Row(modifier = Modifier.padding(all = 8.dp),horizontalArrangement = Arrangement.Center ) {
//Кнопка редактирования -------------------------------------------------------------------------
                TextButton(
                    modifier = Modifier.padding(vertical = 12.dp).weight(1f),
                    onClick = {
                        viewModel.updateUser(
                            avatar = "${viewModel.gettedAvatar.value}",
                            registrationEmailState.value,
                            registrationNameState.value,
                            "${profileState.value?.token}" )
                        //Выйти из профиля
                        viewModel._profileData.value = null
                        viewModel.getNewsList(1)
                        navController.navigate(route = AppNavHost.News.route)
                    }
                ) {Text("Редактировать")}
//Кнопка отмены редактирования ------------------------------------------------------------------
                TextButton(
                    onClick = { navController.navigate(route = AppNavHost.MyProfile.route) },
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .weight(1f)
                )
                { Text(text = "Назад") }
            }
        }
    }
    }


}

//@Preview(showBackground = true)
//@Composable
//fun prevRegistrationScreen() {
//    NewsAppTheme {
//        //val conRez = contentResolver
//        RegistrationScreen(
//            navController = rememberNavController(),
//            viewModel = NewsViewModel(),
//            conRezolver =
//        )
//
//    }