package ru.ivankov.newsapp.model

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

//(2) interface ApiInterface. Описывает методы взаимодействия с конечными точками
interface ApiInterface {

   //Загрузить картиночку на сервер
    @Multipart
    @POST("/api/v1/file/uploadFile")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part?
    ): Response<ImageUploadModel>

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

    @POST("/api/v1/news")
    fun postNews(
        @Body body: PostNewsBody,
        @Header("Authorization") token: String
    ): Call<PostNewsResponse>

    //Для получение данных пользователя по Id
    @GET("/api/v1/user/{id}")
    fun idUserRequest(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<UserInfoResponse>


//    @FormUrlEncoded
//    @POST("insertProduct.php")
//    fun insertProduct(
//        @Field("name") name: String?,
//        @Field("category") category: String?,
//        @Field("price") price: String?
//    ): Call<ResponseBody?>?
//    @FormUrlEncoded
//    @POST("updateCategory.php")
//    fun updateCategory(
//        @Field("id") id: Int,
//        @Field("name") name: String?
//    ): Call<ResponseBody?>?
//    @FormUrlEncoded
//    @POST("updateProduct.php")
//    fun updateProduct(
//        @Field("id") id: Int,
//        @Field("name") name: String?,
//        @Field("category") category: String?,
//        @Field("price") price: String?
//    ): Call<ResponseBody?>?
//    @FormUrlEncoded
//    @POST("deleteCategory.php")
//    fun deleteCategory(
//        @Field("id") id: Int
//    ): Call<ResponseBody?>?
//    @FormUrlEncoded
//    @POST("deleteProduct.php")
//    fun deleteProduct(
//        @Field("id") id: Int
//    ): Call<ResponseBody?>?
//    @DELETE("clearCategories.php")
//    fun clearCategories(): Call<ResponseBody?>?
//    @DELETE("clearProducts.php")
//    fun clearProducts(): Call<ResponseBody?>?
//    @GET("getCategories.php")
////Здесь мы указали метод получения и указываем в каком формате мы будем его получать
////<ArrayList<CategoriesApiModel>>
//    fun getCategory(): Call<ArrayList<CategoriesApiModel>>
//    @GET("getProducts.php")
//    fun getProduct(): Call<ArrayList<ProductsApiModel>>
//    @GET("getFilter.php")
//    fun getFilter(@Query("category") category: String, @Query("price") price: String):
//            Call<ArrayList<ProductsApiModel>>
}
