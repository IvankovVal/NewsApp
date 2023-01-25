package ru.ivankov.newsapp.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
* Мы создадим модифицированный интерфейс, чтобы добавить конечные точки URL-адреса
*  (кавычки в нашем случае — это конечная точка).
* */
//(1) class ApiClient. Само подключение благодаря, которому происходит отправка запросов.
class ApiService private constructor() {
    val api: ApiInterface
        get() = retrofit!!.create(
            ApiInterface::class.java) //(2) interface ApiInterface
    init {
        retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build() }
    companion object {
        //базовая ссылка на ресурс, с которым будет происходить взаимодействие
        private val BASE_URL = "https://news-feed.dunice-testing.com"
        private var apiClient: ApiService? = null
        private var retrofit: Retrofit? = null
        val instance: ApiService?
            @Synchronized get() {
                if (apiClient == null) { apiClient = ApiService() }
                return apiClient } }}
