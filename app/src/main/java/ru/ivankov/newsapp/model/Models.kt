package ru.ivankov.newsapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/*
* Классы для создания объектов которыми будут реализовываться запросы и ответы
* */
//Модель для создания новости
data class MyNew(
    val description: String,
    val image: String,
    val tags: List<String>,
    val title: String
)
data class UserInfoRequest(
    val avatar: String,
    val email: String,
    val name: String,
    val password: String,
    val role: String
)
//Модель ответа на загругку картиночки
data class ImageUploadResponse(
    val success: Boolean,
    val statusCode: Int,
    val data: String
)
// Модель запроса на аутентификацию
data class LoginRequest (
    val email: String,
    val password: String,
)
// Модель ответа на запрос на аутентификацию
data class LoginResponse (
    val data: DataLoginResponse,
    val statusCode: Int,
    val success: Boolean,
)
data class DataLoginResponse(
    var avatar: String,
    val email: String,
    val id: String,
    val name: String,
    val role: String,
    val token: String,
)
// Модель ответа на запрос всех новостей
data class NewsListResponse(
    var data: NewsData
)

data class NewsData(
    val content: List<NewsContent>,
    val numberOfElements: Int
)

data class NewsContent(
    val description: String,
    val id: Int,
    val image: String,
    val tags: List<NewsContentTags>,
    val title: String,
    val userId: String,
    val username: String
)

data class NewsContentTags(
    val id: Int,
    val title: String
)
//Удаление пользователя
data class PostNewsResponse(
    val success: Boolean,
    val statusCode: Int,
    val codes: List<Int>,
    val timeStamp: String
)
//Модель запроса на редактирование профиля
data class EditUserRequest(
    val avatar: String,
    val email: String,
    val name: String,
    val role: String
)

//Модели для ответа на запрос для редактирования профиля
data class UserInfoResponse(
    val data: UserInfoDataResponse,
    val statusCode: Int,
    val success: Boolean
)
//Для получения данных пользователя по Id
data class UserInfoDataResponse(
    val avatar: String,
    val email: String,
    val id: String,
    val name: String,
    val role: String
)

//Модель запроса на регистрацию
data class registrationRequest(
    val avatar: String,
    val email: String,
    val name: String,
    val password: String,
    val role: String
)
data class AuthorizationResponse(
    val data: AuthorizationDataResponse,
    val statusCode: Int,
    val success: Boolean
)
data class AuthorizationDataResponse(
    val avatar: String,
    val email: String,
    val id: String,
    val name: String,
    val role: String,
    val token: String
)
//Для создания новости
data class PostNewsBody(
    val description: String,
    val image: String,
    val tags: List<String>,
    val title: String
)
