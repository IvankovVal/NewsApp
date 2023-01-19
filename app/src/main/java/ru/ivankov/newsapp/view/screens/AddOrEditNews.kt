package ru.ivankov.newsapp.view.screens

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.ivankov.newsapp.model.NewsContent
import ru.ivankov.newsapp.model.NewsContentTags
import ru.ivankov.newsapp.view.navigation.AppNavHost
import ru.ivankov.newsapp.view.removeSpace
import ru.ivankov.newsapp.viewmodel.NewsViewModel
import java.io.File
import java.io.InputStream

@Composable
fun AddOrEditNews(
    navController: NavHostController,
    viewModel: NewsViewModel,
    contentResolver: ContentResolver,
    isAddNews: Boolean
) {
    val context = LocalContext.current
    //состояние полей при создании новости
    val editTitleState =
        remember { mutableStateOf(if (isAddNews) "" else viewModel.editableNews.value!!.title) }
    val editDescriptionState =
        remember { mutableStateOf(if (isAddNews) "" else viewModel.editableNews.value!!.description) }
    //для диалога добавления тэгов
    fun getTag(index: Int): String {
        val myTag: NewsContentTags
        myTag = viewModel.editableNews.value!!.tags.getOrElse(index){NewsContentTags(1,"")}

        return myTag.title
    }

    val openAddTagsDialog = remember { mutableStateOf(false) }
    val firstTagState = remember { mutableStateOf(if (isAddNews) "" else getTag(0))}
    val secondTagState = remember { mutableStateOf(if (isAddNews) "" else getTag(1))}
    val thirdTagState = remember { mutableStateOf(if (isAddNews) "" else getTag(2))}
    val fourthTagState =  remember { mutableStateOf(if (isAddNews) "" else getTag(3))}
    val newsTagsList = remember { mutableStateOf(mutableListOf("")) }
    //Чтобы знать редактируемую новость
    val editableNewsState =
        viewModel.editableNews.observeAsState(
         NewsContent("", 1, "", emptyList(), "", "", ""))
    //Реакция на сохранение картинки
    val isPictureSaved = remember { mutableStateOf(false) }


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
//UIStart---------------------------------------------------------------------------------------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Box1
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
                    modifier = Modifier.fillMaxWidth().border(
                        width = 3.dp,
                        color = if (!isPictureSaved.value) Color.White else Color.Green
                    ),
                    contentDescription = "Selected image",
                )


            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                TextButton(onClick = { imagePicker.launch("image/*") })
                { Text(text = "Выбрать картинку") }
// --------------------Кнопка сохранить аватар
                TextButton(
                    onClick = {
                        // (6) тут нужно запустить запрос на добавление этого файла на сервер
                        //ответ сервера добавить в состояние аватара (registrationAvatarState)
                        //и значение этого состояния добавить в запрос на регистрацию

                        //функция расширения для получения имени файла
                        isPictureSaved.value = true
                        fun ContentResolver.getFileName(fileUri: Uri): String {
                            var name = ""
                            val returnCursor = this.query(fileUri, null, null, null, null)
                            if (returnCursor != null) {
                                val nameIndex =
                                    returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                                returnCursor.moveToFirst()
                                name = returnCursor.getString(nameIndex)
                                returnCursor.close()
                            }
                            return name
                        }
                        //(1)Из Uri в файл
                        val myStream: InputStream? =
                            contentResolver.openInputStream(imageUri!!)//это не файл
                        val file: File = createTempFile()
                        myStream.use { input ->
                            file.outputStream().use { output -> input!!.copyTo(output) }
                        }

                        val myFileName = contentResolver.getFileName(imageUri!!)//имя приходит
                        Log.d("File ", "имя файла - $myFileName")
                        //     Log.d("File ", "имя файла - ${myFile. }")

                        //(2)Создать отправляемый файл
                        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                        //(2)Сформировать мультипарт
                        val filePart = MultipartBody.Part.createFormData(
                            "file",
                            " $myFileName",
                            requestBody
                        )

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
        //Box2
        Box(
            modifier = Modifier
                .background(Color.White, RectangleShape)
                .fillMaxWidth()
                .weight(2f),
            contentAlignment = Alignment.Center
        ) {
            Column {
                TextField(
                    modifier = Modifier.padding(12.dp),
                    value = editTitleState.value,
                    onValueChange = { editTitleState.value = it },
                    label = { Text("Название") })


                TextField(
                    modifier = Modifier.padding(12.dp),
                    value = editDescriptionState.value,
                    onValueChange = { editDescriptionState.value = it },
                    label = { Text("Содержание") })

                TextButton(
                    modifier = Modifier.padding(12.dp),
                    onClick = {
                        //Тут должен быть вызов диалога
                        openAddTagsDialog.value = true
                    }
                ) {
                    Text("Добавить тэги")
                }
            }

        }
        //Box3
        Box(
            modifier = Modifier
                .background(Color.White, RectangleShape)
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
// Если перешли из NewsScreen для добавления-------------------------------------
            if (isAddNews) {
                Row {
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(12.dp),

                        onClick = {
                            viewModel.insertNews(
                                description = editDescriptionState.value,
                                image = if (viewModel.guttedPicture.value !== "") "${viewModel.guttedPicture.value}" else "Пустая",
                                tags = if (newsTagsList.value[0] !== "") {
                                    newsTagsList.value
                                } else {
                                    emptyList()
                                },
                                title = editTitleState.value
                            )
                            viewModel.getNewsList(1)
                            navController.navigate(route = AppNavHost.News.route)
                        }
                    ) {
                        Text("Создать")
                    }
                    //Кнопка отменить
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(12.dp),
                        onClick = { navController.navigate(route = AppNavHost.News.route) }
                    ) { Text("Отменить") }
                }
            }
// Если перешли из NewsItem для редактирования-------------------------------------
            else {
                Row {
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(12.dp),

                        onClick = {
                            viewModel.updateNews(
                                newsId = editableNewsState.value.id,
                                image = if (viewModel.guttedPicture.value !== "") "${viewModel.guttedPicture.value}" else "Пустая",
                                description = editDescriptionState.value,
                                tags = if (newsTagsList.value[0] !== "") {
                                    newsTagsList.value
                                } else {
                                    emptyList()
                                },
                                title = editTitleState.value
                            )
                            //и вернуться назад
                            navController.navigate(route = AppNavHost.News.route)
                        }
                    ) {
                        Text("Редактировать")
                    }
                    //Кнопка отменить
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(12.dp),
                        onClick = { navController.navigate(route = AppNavHost.News.route) }
                    ) { Text("Отменить") }
                }
            }

        }
    }
    //UIEnd---------------------------------------------------------------------------------------
//Ниже диалог добавления тэгов----------------------------------------------------------------------

    if (openAddTagsDialog.value) {
        //Вызываем диалог
//--------------------------------------Alert dialog при поиске новости-----------------------------
        AlertDialog(onDismissRequest = { openAddTagsDialog.value = false },
            title = { Text(text = "Найти новость") },
            text = {
                Column {
                    TextField(
                        value = firstTagState.value.toString(),
                        onValueChange = { firstTagState.value = removeSpace(it) },
                    )
                    TextField(
                        value = secondTagState.value.toString(),
                        onValueChange = { secondTagState.value = removeSpace(it) },
                    )
                    TextField(
                        value = thirdTagState.value.toString(),
                        onValueChange = { thirdTagState.value = removeSpace(it) },
                    )
                    TextField(
                        value = fourthTagState.value.toString(),
                        onValueChange = { fourthTagState.value = removeSpace(it) },
                    )
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
//Кнопка редактирования в диалоге-------------------------------------------------------------------
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            newsTagsList.value.clear()
                            if (firstTagState.value !== "") newsTagsList.value.add(firstTagState.value)
                            if (secondTagState.value !== "") newsTagsList.value.add(secondTagState.value)
                            if (thirdTagState.value !== "") newsTagsList.value.add(thirdTagState.value)
                            if (fourthTagState.value !== "") newsTagsList.value.add(fourthTagState.value)
                            Log.d("edittag", firstTagState.value)

                            openAddTagsDialog.value = false
                        }
                    ) {
                        Text("Сохранить")
                    }
//Кнопка отмены редактирования в диалоге------------------------------------------------------------------
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            openAddTagsDialog.value = false
                            Toast.makeText(context, "Отменить", Toast.LENGTH_LONG)
                                .show()
                        }
                    ) {
                        Text("Отмена")
                    }
                }

            }
        )
//-----------------------Конец Alert Dialog---------------------------------------------------------
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
//           // contentResolver = ContentResolver(MainActivity),
//        )
//
//    }
//}
