package ru.ivankov.newsapp.view.screens

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.removeSpace
import ru.ivankov.newsapp.viewmodel.NewsViewModel
import java.io.File
import java.io.InputStream

@Composable
fun AddNews(
    navController: NavHostController,
    viewModel: NewsViewModel,
    conRezolver: ContentResolver,
) {
    val context = LocalContext.current

    //состояние полей при создании новости
    val editTitleState = remember { mutableStateOf("") }
    val editDescriptionState = remember { mutableStateOf("") }
    val editTagsOnCreateState = remember { mutableStateOf("") }

    //для картиночки
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
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Добавляем картинку------------------
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
                        text = "Выбрать картинки"
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
                        viewModel.uploadPicture(filePart)//передаём полученный объект для отправки на сервер в соответствующий метод
                        Log.d("pic", "выбор картинки - ${viewModel.guttedPicture.value}")

                    },
                ) {
                    Text(text = "Сохранить")
                }
            }
        }
        //Конец добавления картинки-----------
        TextField(
            modifier = Modifier.padding(12.dp),
            value = editTitleState.value,
            onValueChange = { editTitleState.value = it },
            label = { Text("Название") })

        TextField(
            modifier = Modifier.padding(12.dp),
            value = editTagsOnCreateState.value,
            onValueChange = { editTagsOnCreateState.value = it },
            label = { Text("tags") })

        TextField(
            modifier = Modifier.padding(12.dp),
            value = editDescriptionState.value,
            onValueChange = { editDescriptionState.value = it },
            label = { Text("Содержание") })

        Row {
            TextButton(
                modifier = Modifier.weight(1f).padding(12.dp),

                onClick = {

                        viewModel.insertNews(
                        editDescriptionState.value,
                        image = "${viewModel.guttedPicture.value}",
                        emptyList(),
                        editTitleState.value
                    )
                        viewModel.getNewsList(1)
                        navController.navigate(route = AppNavHost.News.route)

                }
            ) {
                Text("Создать")
            }
            //Кнопка отменить
            TextButton(
                modifier = Modifier.weight(1f).padding(12.dp),
                onClick = {
                   // viewModel.getNewsList(1)
                    navController.navigate(route = AppNavHost.News.route)
                }
            ) {
                Text("Отменить")
            }
        }
    }
}

//____________________________________________________________________________________________________
//@Preview(showBackground = true)
//@Composable
//fun PrevAddNews() {
//    NewsAppTheme {
//        AddNews(
//            navController = rememberNavController(),
//            viewModel = NewsViewModel()
//        )
//
//    }
//}
