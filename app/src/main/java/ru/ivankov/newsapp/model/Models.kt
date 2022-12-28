package ru.ivankov.newsapp.model

/*
* Классы для создания объектов которыми будут реализовываться запросы и ответы
* */
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

data class UserInfoDataResponse(
    val avatar: String,
    val email: String,
    val id: String,
    val name: String,
    val role: String
)

////------------------Модели для запроса всех новостей----------------------------------------------------


////------------------Модель POST запроса публикации новости------------------------------------------
//data class PostNewsBody(
//    val description: String,
//    val image: String,
//    val tags: List<String>,
//    val title: String
//)
////------------------Модель ответа на запрос о публикации новости------------------------------------
//data class PostNewsResponse(
//    val success: Boolean,
//    val statusCode: Int,
//    val codes: List<Int>,
//    val timeStamp: String
//)
//
////------------------Модель запроса на загрузку картинки---------------------------------------------
//data class ImageUploadModel(
//    val success: Boolean,
//    val statusCode: Int,
//    val data: String
//)
////------------------Модель GET запроса на получение информации о пользователе по ID-----------------
//data class UserInfoResponse(
//    val data: UserInfoDataResponse,
//    val statusCode: Int,
//    val success: Boolean
//)
//
//data class UserInfoDataResponse(
//    val avatar: String,
//    val email: String,
//    val id: String,
//    val name: String,
//    val role: String
//)
////------------------Модель ответа на запрос о редактировании пользователя---------------------------
//data class EditUserRequest(
//    val avatar: String,
//    val email: String,
//    val name: String,
//    val role: String
//)
