package ru.ivankov.newsapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
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
                }   } ) } }
    //--------------Редактирование учётной записи------------------------------------------------------

    fun update_task(avatar: String,email: String, name: String, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val callUpdateUser: Call<UserInfoResponse>? = ApiService.instance?.api?.editUserRequest(EditUserRequest(avatar,email,name,"user"),token)   //updateMyTask(id,name,status)

            callUpdateUser?.enqueue(object : Callback<UserInfoResponse?> {
                override fun onResponse(call: Call<UserInfoResponse?>, response: Response<UserInfoResponse?>) {
                    //Toast.makeText(this,"Задача обновлена",Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<UserInfoResponse?>, t: Throwable) {
                    // Toast.makeText(context,"ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!",Toast.LENGTH_SHORT).show()
                }
            })
        }}
    //--------------Удаление учётной записи------------------------------------------------------
    fun deleteUser (token:String){
        viewModelScope.launch(Dispatchers.IO) {
            val callDeleteUser: Call<PostNewsResponse>? = ApiService.instance?.api?.deleteUser(token)

            callDeleteUser?.enqueue(object : Callback<PostNewsResponse?> {//enqueue - "поставить в очередь"
                override fun onResponse(
                call: Call<PostNewsResponse?>,
                response: Response<PostNewsResponse?>
            ) {
                    Log.d("deleteUser", "Профиль удалён")
                    profileData.value = null
                }

                override fun onFailure(call: Call<PostNewsResponse?>, t: Throwable) {
                    Log.d("deleteUser", "С удалением что-то пошло не так")
                }
            })
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



