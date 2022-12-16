package ru.ivankov.newsapp.model

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
/*
* Мы создадим модифицированный интерфейс, чтобы добавить конечные точки URL-адреса (кавычки в нашем случае — это конечная точка).
* */
interface ApiService {

    // -----------------------Конечные точки-----------------------------------------------------
    //Загрузка файла
    @Multipart
    @POST("/api/v1/file/uploadFile")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part?
    ): Response<ImageUploadModel>

    //Получение новостей
    @GET("/api/v1/news")
    fun allNewsRequest(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ): Call<NewsListResponse>

    //Запрос на аутентификацию
    @POST("/api/v1/auth/login")
    fun loginRequest(
        @Body body: LoginRequestBody
    ): Call<AuthorizationResponse>

    //Запрос на регистрацию
    @POST("/api/v1/auth/register")
    fun registrationRequest(
        @Body body: RegistrationRequestBody
    ): Call<AuthorizationResponse>
//--------------------------------------------------------------------------------------------------
    //-----------Запрос на создаение записи---------------------------------------------

    @POST("/api/v1/news")
    fun postNews(
        @Body body: PostNewsBody,
        @Header("Authorization") token: String
    ): Call<PostNewsResponse>

    //-----------Запрос на поиск записи по параметру---------------------------------------------

    @GET("/api/v1/news/user/{id}")
    fun idNewsRequest(
        @Path("id") id: String,
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Header("Authorization") token: String
    ): Call<NewsListResponse>

    //-----------Запрос на даление учётной записи---------------------------------------------

    @DELETE("https://news-feed.dunice-testing.com/api/v1/user")
    fun deleteUser(
        @Header("Authorization") token: String
    ): Call<PostNewsResponse>

    //-----------Запрос на даление записи---------------------------------------------

    @DELETE("https://news-feed.dunice-testing.com/api/v1/news/{id}")
    fun deleteNewsId(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Call<PostNewsResponse>

    @GET("/api/v1/user/{id}")
    fun idUserRequest(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<UserInfoResponse>
    //-----------Запрос на редактирование учётной записи---------------------------------------------
    @PUT("/api/v1/user")
    fun editUserRequest(
        @Body body: EditUserRequest,
        @Header("Authorization") token: String
    ): Call<UserInfoResponse>
    //-----------Запрос на редактирование---------------------------------------------
    @PUT("/api/v1/news/{id}")
    fun editNewsRequest(
        @Path("id") id: Int,
        @Body body: PostNewsBody,
        @Header("Authorization") token: String
    ): Call<PostNewsResponse>
//-----------Запрос на поиск---------------------------------------------
    @GET("api/v1/news/find")
    fun findNews(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("author") author: String? = null,
        @Query("keywords") keywords: String? = null,
        @Query("tags") tags: List<String>? = null
    ): Call<NewsData>
// -----------------------Retrofit------------------------------------------------------------------


    companion object {

        var BASE_URL = "https://news-feed.dunice-testing.com"

        fun create() : ApiService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)

        }
    }
}