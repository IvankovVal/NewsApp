package ru.ivankov.newsapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONTokener
import ru.ivankov.newsapp.model.NewsModel
import ru.ivankov.newsapp.model.UserModel
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/*
   * здесь должны быть списки содержащие новости и методы с запросами к серверу на получение данных
   * compose не умеет работать с livedata - для того чтобы рекомпозиция происходила автоматически
   * объект необходимо приводить к типу State
   * для этого нужно добавить зависимость implementation "androidx.compose.runtime:runtime-livedata:$compose_version"(1.1.1)

   * */
class NewsViewModel:ViewModel() {

    val newsList = mutableStateListOf<NewsModel>()


    fun getRegistration(avatar:String, name: String,email: String, password: String, role:String){
            viewModelScope.launch {
                val jsonObject = JSONObject()

//                {
//                    "avatar": "string",
//                    "email": "string",
//                    "name": "string",
//                    "password": "string",
//                    "role": "string"
//                }

                jsonObject.put("text", avatar)
                jsonObject.put("text", email)
                jsonObject.put("text", name)
                jsonObject.put("text", password)
                jsonObject.put("text", role)
                val jsonObjectString = jsonObject.toString()

                GlobalScope.launch(Dispatchers.IO) {
                    val url = URL("https://news-feed.dunice-testing.com/api/v1/auth/register")
                    val httpsURLConnection = url.openConnection() as HttpsURLConnection
                    httpsURLConnection.requestMethod = "POST"
                    httpsURLConnection.setRequestProperty("Content-Type", "application/json")
                    httpsURLConnection.setRequestProperty("Accept", "application/json")
                    httpsURLConnection.doInput = true
                    httpsURLConnection.doOutput = true

                    val outputStreamWriter = OutputStreamWriter(httpsURLConnection.outputStream)
                    outputStreamWriter.write(jsonObjectString)
                    outputStreamWriter.flush()

                    val responseCode = httpsURLConnection.responseCode
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        val response = httpsURLConnection.inputStream.bufferedReader()//отклик
                            .use { it.readText() }
                        withContext(Dispatchers.IO) {
                            val jsonArray = JSONTokener(response).nextValue() as JSONObject
                            val data = jsonArray.getJSONObject("data")
                            val contentArray = data.getJSONArray("content")
                            val taskList: ArrayList <UserModel> = ArrayList()
                            //парсим ответ в список
                            for (i in 0 until contentArray.length()) {
                                val task = contentArray.getJSONObject(i)
                                taskList.add (UserModel.parseFromJSONObject())
                            }
                    else {
                    }
                }
            }

    }

//Registration - создаётся пользователь
    /*
    * Post запрос
    * "avatar": "string",
  "email": "string",
  "name": "string",
  "password": "string",
  "role": "string"
  * */

    //
    /*
    * Post запрос
    "email": "string",
  "password": "string"
  * С ответом:
  * {
  "data": {
    "avatar": "string",
    "email": "string",
    "id": "string",
    "name": "string",
    "role": "string",
    "token": "string"
  },
  "statusCode": 0,
  "success": true
}
* Этот ответ нужно заполнить в MyProfile
  * */
}