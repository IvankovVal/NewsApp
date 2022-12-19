package ru.ivankov.newsapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.ivankov.newsapp.model.*

/*
   * здесь должны быть списки содержащие новости и методы с запросами к серверу на получение данных
   * compose не умеет работать с livedata - для того чтобы рекомпозиция происходила автоматически
   * объект необходимо приводить к типу State
   * для этого нужно добавить зависимость implementation "androidx.compose.runtime:runtime-livedata:$compose_version"(1.1.1)

   * */
class NewsViewModel : ViewModel() {
    //------------------Свойства----------------------------------------------------
    //Список новостей
    val newsList: MutableLiveData<ArrayList<NewsContent>> by lazy { MutableLiveData<ArrayList<NewsContent>>() }
  //  private val newsList = MutableLiveData<List<NewsContent>>()
    val newsModel = MutableLiveData<List<NewsContent>>()

    val profileData: MutableLiveData<DataLoginResponse> by lazy { MutableLiveData<DataLoginResponse>() }

    // val authorizationResponse: MutableStateFlow<AuthorizationResponse?> = MutableStateFlow(AuthorizationResponse?)
    //------------------Методы----------------------------------------------------
    //------------------Регистрация----------------------------------------------------
    fun postRegistration() {
        viewModelScope.launch {
        }
    }
    //------------------Аутентификация----------------------------------------------------
    fun postAutentification() {
        viewModelScope.launch {
            //вызываем наш //(1) class ApiClient и метод из //(2) interface ApiInterface
            val postLogin = ApiService.instance?.api?.postLogin(LoginRequest("bellator87@mail.ru", "198727"))
            postLogin?.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>)
                { //В методе onResponse мы указываем что мы будем делать с ответом сервера,
////в случае, если postLogin() выполнится удачно
                       // val answer
                  //  profileData?.postValue( response.body()?.data)
                      // profileData?.postValue(response.body()?.data)
                   this@NewsViewModel.profileData?.value = DataLoginResponse (
                        avatar = response.body()?.data!!.avatar,
                        email = response.body()?.data!!.email,
                        id = response.body()?.data!!.id,
                        name = response.body()?.data!!.name,
                        role = response.body()?.data!!.role,
                        token = response.body()?.data!!.token,
                            )

                    Log.d(TAG, "Значение профиля - ${this@NewsViewModel.profileData?.value?.name}")
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                    Log.d(TAG, "ОШИБКА!!!")
                }
            }
            )
        }
    }
}

