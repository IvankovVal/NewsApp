package ru.ivankov.newsapp.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.ivankov.newsapp.model.*
import java.lang.IllegalArgumentException
import kotlin.random.Random

/*
   * здесь должны быть списки содержащие новости и методы с запросами к серверу на получение данных
   * compose не умеет работать с livedata - для того чтобы рекомпозиция происходила автоматически
   * объект необходимо приводить к типу State
   * для этого нужно добавить зависимость implementation "androidx.compose.runtime:runtime-livedata:$compose_version"(1.1.1)

   * */

//class NewsViewModel(application: Application) : AndroidViewModel(application) {
class NewsViewModel : ViewModel() {
    //------------------Свойства----------------------------------------------------

    //Список новостей куда принять
    private val _newsList: MutableLiveData<List<NewsContent>> by lazy { MutableLiveData<List<NewsContent>>() }
    //Список который отправим во view
    val newsList: LiveData<List<NewsContent>> = _newsList
    init {
        getNewsList()
    }

    private val profileData = MutableLiveData<DataLoginResponse>()
    val _profileData: LiveData<DataLoginResponse> = profileData

    //------------------Методы----------------------------------------------------

    //------------------Получение списка новостей----------------------------------------------------
    fun getNewsList() {
        viewModelScope.launch {
            //вызываем наш //(1) class ApiClient и метод из //(2) interface ApiInterface
            val getNews =
                ApiService.instance?.api?.newsRequest(1,15)
            getNews?.enqueue(object : Callback<NewsListResponse>{
                override fun onResponse(
                    call: Call<NewsListResponse>,
                    response: Response<NewsListResponse>
                ) { //В методе onResponse мы указываем что мы будем делать с ответом сервера,
////в случае, если postLogin() выполнится удачно

                    //List<NewsContent>
                    val answer: List<NewsContent>? = response.body()?.data?.content
                    _newsList.value = response.body()?.data?.content
                  //  Log.d(TAG, "Значение профиля - ${response.body()?.data?.content }")
                }

                override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {

                    Log.d(TAG, "ОШИБКА!!!")
                }
            }
            )
        }
    }

    //------------------Аутентификация----------------------------------------------------
    fun postAutentification() {
        viewModelScope.launch {
            //вызываем наш //(1) class ApiClient и метод из //(2) interface ApiInterface
            val postLogin =
                ApiService.instance?.api?.postLogin(LoginRequest("bellator87@mail.ru", "198727"))
            postLogin?.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) { //В методе onResponse мы указываем что мы будем делать с ответом сервера,
////в случае, если postLogin() выполнится удачно

                    profileData.value =
                        DataLoginResponse(
                            avatar = response.body()?.data!!.avatar,
                            email = response.body()?.data!!.email,
                            id = response.body()?.data!!.id,
                            name = response.body()?.data!!.name,
                            role = response.body()?.data!!.role,
                            token = response.body()?.data!!.token,
                        )
                    Log.d(TAG, "Значение профиля - ${profileData.value?.name}")
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                    Log.d(TAG, "ОШИБКА!!!")
                }
            }
            )
        }
    }

    //------------------Регистрация----------------------------------------------------
    fun postRegistration() {
        viewModelScope.launch {
        }
    }

}

//class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
//            return NewsViewModel(application = application) as T
//        }
//        throw  IllegalArgumentException("Unknown ViewModel Class")
//    }
//
//}



