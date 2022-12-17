package ru.ivankov.newsapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import ru.ivankov.newsapp.model.*
import ru.ivankov.newsapp.view.MainActivity
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
    //------------------Свойства----------------------------------------------------
    var newsList: MutableLiveData<Response<NewsData>> = MutableLiveData()


    var authorizationResponse: MutableLiveData<OwnerData> = MutableLiveData()


   // val authorizationResponse: MutableStateFlow<AuthorizationResponse?> = MutableStateFlow(AuthorizationResponse?)

    //------------------Методы----------------------------------------------------
    //------------------Регистрация----------------------------------------------------

    fun postRegistration() {
        viewModelScope.launch {
//Тут пишем запрос
            val apiInterface = ApiService.create().registrationRequest(
                //Сюда пишем данные, которые требуются для регистрации
                RegistrationRequestBody("","any@mail","anyName", "12345", "user")

//                val avatar: String,
//            val email: String,
//            val name: String,
//            val password: String,
//            val role: String
            )

//Асинхронно отправить запрос и уведомить callback его ответе или, если произошла ошибка при обращении к серверу, создании запроса или обработке ответа.
            apiInterface.enqueue( object : Callback<AuthorizationResponse> {
                override fun onResponse(call: Call<AuthorizationResponse>,response: Response<AuthorizationResponse>)
                {
                    val responseBody = response.body()
                   // Toast.makeText(coroutineContext, response.body().toString(), Toast.LENGTH_LONG).show()
                    Log.i(TAG, "$response.body().toString()")
                //authorizationResponse = responseBody.data
                }

                override fun onFailure(call: Call<AuthorizationResponse>, t: Throwable) {

                }


            })


        }
    }
    //------------------Аутентификация----------------------------------------------------
    fun postAutentification() {
        viewModelScope.launch {
            val loginResp = ApiService.create().loginRequest(
                LoginRequestBody("bellator87@mail.ru", "198727")
            ).awaitResponse()
            if (loginResp.isSuccessful) {
                val loginData = loginResp.body()!!
                val ownerData = OwnerData(
                    loginData.data.token,
                    loginData.data.avatar,
                    loginData.data.email,
                    loginData.data.id,
                    loginData.data.name,
                    loginData.data.role

                )
                authorizationResponse.value = ownerData

            } else {
            }
        }
    }
    }


//val registrationResp = ApiService.registrationRequest(
//    RegistrationRequestBody(
//        //тело запроса на авторизацию
//        //те данные которые нужны для авторизации
//
//    )
//).awaitResponse()
//if (registrationResp.isSuccessful) {
//    inputLogin.value = inputRegisterEmail.value
//    inputPassword.value = inputRegisterPassword.value
//    registerUI.value = false
//    loginUI.value = true
//    Repository.uploadedImage.value = null
//} else {
//    MakeToast("Register failed")
//}