package ru.ivankov.newsapp.model

import org.json.JSONObject

//------------------Модель новости-------------------------------------------------------------
class NewsModel (
    val userId:Int,
    val userName:String,
    val newsId:Int,
    val newsTitle:String,
    val newsDescription:String,
//    val newsImage:String,
        )
//------------------Модель пользователя-------------------------------------------------------------

class UserModel(
    val userAvatar: String,
    var userEmail: String,
    val userName: String,
    val userPassword: String,
    val userRole: String
    ){
    companion object {
        fun parseFromJSONObject(json: JSONObject): UserModel {
            val text = json.getString("text")
            val avatar = json.getString("avatar")
            val email = json.getString("email")
            val name = json.getString("name")
            val password = json.getString("password")
            val role = json.getString("role")

            return UserModel(avatar, email,name, password,role)
        }
    }

}