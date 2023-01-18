package ru.ivankov.newsapp.model

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


//(2) interface ApiInterface. Описывает методы взаимодействия с конечными точками
interface ApiInterface {

   //Загрузить картиночку на сервер
    @Multipart
    @POST("/api/v1/file/uploadFile")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part?
    ): Response<ImageUploadResponse>

// Запрос на вход в профиль

    @POST("/api/v1/auth/login")
    fun postLogin(
        @Body body: LoginRequest
    ): Call<LoginResponse>

// Запрос на получение новостей

    @GET("/api/v1/news")
    fun newsRequest(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ): Call<NewsListResponse>

//Запрос на удаление профиля

    @DELETE("https://news-feed.dunice-testing.com/api/v1/user")
    fun deleteUser(
        @Header("Authorization") token: String
    ): Call<PostNewsResponse>

    //Запрос на редактирование профиля
    @PUT("/api/v1/user")
    fun editUserRequest(
        @Body body: EditUserRequest,
        @Header("Authorization") token: String
    ): Call<UserInfoResponse>

    //Запрос на регистрацию
    @POST("/api/v1/auth/register")
    fun addUserRequest(
        @Body body: registrationRequest
    ): Call<AuthorizationResponse>

    //Поиск новости по параметрам (автор, слово, тэг)
    @GET("api/v1/news/find")
    fun findNews(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("author") author: String? = null,
        @Query("keywords") keywords: String? = null,
        @Query("tags") tags: List<String>? = null
    ): Call<NewsData>

    //Создание новости
    @POST("/api/v1/news")
    fun postNews(
        @Body body: PostNewsBody,
        @Header("Authorization") token: String
    ): Call<PostNewsResponse>
    //Удаление новости
    @DELETE("https://news-feed.dunice-testing.com/api/v1/news/{id}")
    fun deleteNewsId(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Call<PostNewsResponse>

    //Для получение данных пользователя по Id
    @GET("/api/v1/user/{id}")
    fun idUserRequest(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<UserInfoResponse>
}

//interface MyApi{
//
//
//    //Загрузить картиночку на сервер
//    @Multipart
//    @POST("/api/v1/file/uploadFile")
//    suspend fun uploadImage(
//        @Part file: MultipartBody.Part?
//    ): Response<ImageUploadResponse>
//
//
//    companion object{
//        operator fun invoke() :MyApi{
//
//            return Retrofit.Builder()
//                .baseUrl("https://news-feed.dunice-testing.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(MyApi::class.java)
//        }
//
//    }
//}