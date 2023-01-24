package ru.ivankov.newsapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
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

    var _editableNews = MutableLiveData(NewsContent("",0,"", emptyList(),"","",""))
    val editableNews: MutableLiveData<NewsContent> = _editableNews

    //Список новостей куда принять
    private val _newsList: MutableLiveData<List<NewsContent>> by lazy { MutableLiveData<List<NewsContent>>() }

    //Список который отправим во view
    val newsList: LiveData<List<NewsContent>> = _newsList

    private val _pageAmount = MutableLiveData(1)
    val pageAmount: MutableLiveData<Int?> = _pageAmount

    private val _loginMessage = MutableLiveData("")
    val loginMessage: MutableLiveData<String> = _loginMessage
    private val _registrationMessage = MutableLiveData("")
    val registrationMessage: MutableLiveData<String> = _registrationMessage//будем показывать Тост

    private val _gettedAvatar = MutableLiveData(
        "https://news-feed.dunice-testing.com/api/v1/file/16c503aa-87a8-4f72-b615-d1065b8ffe06.jpg")
    val gettedAvatar: MutableLiveData<String> = _gettedAvatar

    private val _guttedPicture = MutableLiveData("")
    val guttedPicture: MutableLiveData<String> = _guttedPicture

    //Автор новости
    private val _newsAuthor = MutableLiveData(
        UserInfoDataResponse(
            "any photo",
            "bla@mail.ru",
            "123",
            "Безымянный",
            "user"
        )
    )
    val newsAuthor: MutableLiveData<UserInfoDataResponse?> = _newsAuthor

    //новости в профиль
    private val _profileNews: MutableLiveData<List<NewsContent>> by lazy { MutableLiveData<List<NewsContent>>() }
    val profileNews: LiveData<List<NewsContent>> = _profileNews

    init {
        getNewsList(1)
    }

    val _profileData = MutableLiveData<DataLoginResponse>()
    val profileData: LiveData<DataLoginResponse> = _profileData

    //------------------Методы----------------------------------------------------
    //-------------Регистрация - добавление пользователя-------------------------------------------
    fun postRegistration(
        avatar: String,
        email: String,
        name: String,
        password: String,
        role: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            val callAddUser: Call<AuthorizationResponse>? = ApiService.instance?.api?.addUserRequest(
                registrationRequest(
                    avatar,//"https://news-feed.dunice-testing.com/api/v1/file/caa9e2ec-b90b-4b71-b209-2cfe85730c07.jpeg",
                    email,//"nikadim@mail.ru",
                    name,//"Nikadim", (нельзя допускать одинаковых имён)
                    password,//"198727",
                    role//всегда user
                )
            )
            callAddUser?.enqueue(object : Callback<AuthorizationResponse?> {
                override fun onResponse(call: Call<AuthorizationResponse?>, response: Response<AuthorizationResponse?>) {
                    if (response.code() == 200 || response.code() ==201){
                        Log.d("Reg", "${response.code() }")
                        _registrationMessage.value = ""
                    }

                    else{
                        _registrationMessage.value = "Ошибка! Попробуйте иначе."
                    }
                }

                override fun onFailure(call: Call<AuthorizationResponse?>, t: Throwable) {
                    Log.d("Reg", "$t")
                }
            })
        }
    }

    //------------------Получение списка новостей----------------------------------------------------
    fun getNewsList(page: Int) {
        viewModelScope.launch {
            //вызываем наш //(1) class ApiClient и метод из //(2) interface ApiInterface
            val getNews =
                ApiService.instance?.api?.newsRequest(page, 15)
            getNews?.enqueue(object : Callback<NewsListResponse> {
                override fun onResponse(
                    call: Call<NewsListResponse>,
                    response: Response<NewsListResponse>
                ) { //В методе onResponse мы указываем что мы будем делать с ответом сервера,
////в случае, если postLogin() выполнится удачно

                    //List<NewsContent>
                    _newsList.value = response.body()?.data?.content
                    val elementAmount = response.body()?.data?.numberOfElements
                    if (elementAmount != null) {
                        _pageAmount.value = Math.ceil(elementAmount / 15.0).toInt()
                    }
                    Log.d("page", "Количество страниц - ${_pageAmount.value}")
                }

                override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {

                    Log.d("page", "ОШИБКА!!!")
                }
            })
        }
    }
    //--------------Редактирование учётной записи------------------------------------------------------

    fun updateUser(avatar: String, email: String, name: String, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val callUpdateUser: Call<UserInfoResponse>? = ApiService.instance?.api?.editUserRequest(
                EditUserRequest(
                    avatar,
                    email,
                    name,
                    "user"
                ), token
            )   //updateMyTask(id,name,status)

            callUpdateUser?.enqueue(object : Callback<UserInfoResponse?> {
                override fun onResponse(
                    call: Call<UserInfoResponse?>,
                    response: Response<UserInfoResponse?>
                ) {
                    //Toast.makeText(this,"Задача обновлена",Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<UserInfoResponse?>, t: Throwable) {
                    // Toast.makeText(context,"ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    //--------------Удаление учётной записи------------------------------------------------------
    fun deleteUser(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val callDeleteUser: Call<PostNewsResponse>? =
                ApiService.instance?.api?.deleteUser(token)

            callDeleteUser?.enqueue(object : Callback<PostNewsResponse?> {
                //enqueue - "поставить в очередь"
                override fun onResponse(
                    call: Call<PostNewsResponse?>,
                    response: Response<PostNewsResponse?>
                ) {
                    Log.d("deleteUser", "Профиль удалён")
                    _profileData.value = null
                }

                override fun onFailure(call: Call<PostNewsResponse?>, t: Throwable) {
                    Log.d("deleteUser", "С удалением что-то пошло не так")
                }
            })
        }
    }

    //------------------Аутентификация----------------------------------------------------
    fun postAuthentification(email: String, password: String, completion: () -> Unit) {
        viewModelScope.launch {
            //вызываем наш //(1) class ApiClient и метод из //(2) interface ApiInterface
            val postLogin =
                ApiService.instance?.api?.postLogin(LoginRequest(email, password))
            postLogin?.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) { //В методе onResponse мы указываем что мы будем делать с ответом сервера,
////в случае, если postLogin() выполнится удачно

                    if(response.code() == 200){
                    _profileData.value =
                        DataLoginResponse(
                            avatar = response.body()?.data!!.avatar,//приходит null
                            email = response.body()?.data!!.email,
                            id = response.body()?.data!!.id,
                            name = response.body()?.data!!.name,
                            role = response.body()?.data!!.role,
                            token = response.body()?.data!!.token,
                        )
                        _loginMessage.value = ""
                        Log.d(TAG, "Значение профиля - ${profileData.value?.name}")
                    }
                    else{
                        _loginMessage.value = "Ошибка. Попробуйте ещё раз."
                        Log.d(TAG, "Код - ${response.code()}")

                    }
                    launch(Dispatchers.Main) {
                        completion()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                    Log.d(TAG, "ОШИБКА!!!")
                }
            }
            )
        }
    }

    //-------------Поиск новости-----------------------
    fun searchNews(
        page: Int,
        perPage: Int,
        author: String?,
        keywords: String?,
        tags: List<String>?
    ) {
        val callFindNews = ApiService.instance?.api?.findNews(
            page,
            perPage,
            author,
            keywords,
            tags
        )
        callFindNews?.enqueue(object : Callback<NewsData> {

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                Log.d("search", "${_newsList.value}")
            }

            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                val fundedNews = response.body()!!.content
                _newsList.value = fundedNews
                Log.d("search", "${response.code()}")
            }
        })

    }

    //-------------Функция добавления задачи-------------------------------------------
    fun insertNews(
        new: MyNew
//        description: String,
//        image: String,
//        tags: List<String>,
//        title: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val callPostNews: Call<PostNewsResponse>? = ApiService.instance?.api?.postNews(
                PostNewsBody(
                    description = new.description,
                    image = new.image,
                    tags = new.tags,
                    title = new.title
                ),
                profileData.value!!.token
            )
            callPostNews?.enqueue(object : Callback<PostNewsResponse?> {
                override fun onResponse(
                    call: Call<PostNewsResponse?>,
                    response: Response<PostNewsResponse?>
                ) {
                    getNewsList(1)
                    Log.d("createNews", "${response.code()}")
                }

                override fun onFailure(call: Call<PostNewsResponse?>, t: Throwable) {
                    Log.d("createNews", "$t")
                }
            })
        }
    }

    //------------------------------------------------------------------------
//-------------Функция получения данных пользователя по Id-------------------------------------------
    fun getUserInfoById(id: String) {

        val callActiveTasks = ApiService.instance?.api?.idUserRequest(id, profileData.value!!.token)
        callActiveTasks?.enqueue(object : Callback<UserInfoResponse> {
            override fun onResponse(
                call: Call<UserInfoResponse>,
                response: Response<UserInfoResponse>
            ) {
//-------------переменная со списком
                _newsAuthor.value = UserInfoDataResponse(
                    avatar = response.body()?.data!!.avatar,
                    email = response.body()?.data!!.email,
                    id = response.body()?.data!!.id,
                    name = response.body()?.data!!.name,
                    role = response.body()?.data!!.role,
                )
                Log.d("user", "${response.code()}")
            }
            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Log.d("userF", "$t")
            }
        })
    }

    //-------------Функция добавления аватара-------------------------------------------
    fun uploadAvatar(picture: MultipartBody.Part?){
        viewModelScope.launch(Dispatchers.IO) {
//            val callPostNews: Call<PostNewsResponse>? = ApiService.instance?.api?.postNews(
            val callUploadImage  = ApiService.instance?.api?.uploadImage(picture)

            _gettedAvatar.postValue(callUploadImage!!.body()!!.data) // "${callUploadImage!!.body()!!.data}"
            Log.d("upAva", callUploadImage.body()!!.data)
            Log.d("upAva", "${callUploadImage.code()}")
        }
    }
    //-------------Функция добавления картинки в новости-------------------------------------------
    fun uploadPicture(picture: MultipartBody.Part?){
        viewModelScope.launch(Dispatchers.IO) {
            val callUploadImage  = ApiService.instance?.api?.uploadImage(picture)

            _guttedPicture.postValue(callUploadImage!!.body()!!.data)
            Log.d("upPic", "${callUploadImage.code()}")
        }
    }
    fun deleteNews(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val callDeleteTask: Call<PostNewsResponse>? =
                ApiService.instance?.api?.deleteNewsId(id, profileData.value!!.token)

            callDeleteTask?.enqueue(object : Callback<PostNewsResponse?> {
                override fun onFailure(call: Call<PostNewsResponse?>, t: Throwable) {
                    // Toast.makeText( context,"ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!",Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(
                    call: Call<PostNewsResponse?>,
                    response: Response<PostNewsResponse?>
                ) {
                    Log.d("delnews", "${response.code()}")

                }
            })
        }
    }

    fun updateNews(newsId: Int,
                    image: String,
                    description: String,
                    tags: List<String>,
                    title: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val callupdateNews = ApiService.instance?.api?.editNewsRequest(
                id = newsId,
                body = PostNewsBody(image = image, description = description, tags = tags, title = title),
                token = profileData.value!!.token
            )

            callupdateNews?.enqueue(object : Callback<PostNewsResponse> {
                override fun onResponse(call: Call<PostNewsResponse>, response: Response<PostNewsResponse>) {
                    Log.d("editNews", "${response.code()}")
                }
                override fun onFailure(call: Call<PostNewsResponse>, t: Throwable) {
                    // Toast.makeText(context,"ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!",Toast.LENGTH_SHORT).show()
                }
            })
        }}

//class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
//            return NewsViewModel(application = application) as T
//        }
//        throw  IllegalArgumentException("Unknown ViewModel Class")
//    }
//
//}
}


