package ru.ivankov.newsapp.view

import ru.ivankov.newsapp.model.MyNew
import ru.ivankov.newsapp.model.UserInfoRequest


fun emailValidator(email: String): Boolean {
    val emailEndsList =
        listOf("ru", "com", "org", "net", "su", "kz", "ua", "by")
    var isValid = false
    if (email.isNotBlank() &&
        email.contains('@') &&
        !email.startsWith('@') &&
        !email.endsWith('@')) {
        val parsedEmail = email.split("@")
        if (parsedEmail.size == 2 &&
            parsedEmail[0].length in 3..15 &&
            parsedEmail[1].contains('.') &&
            !parsedEmail[1].startsWith('.') &&
            !parsedEmail[1].endsWith('.')) {
            val parsedEmailEnd = parsedEmail[1].split('.')
            if (parsedEmailEnd.size == 2 &&
                parsedEmailEnd[0].length in 2..10 &&
                parsedEmailEnd[1] in emailEndsList)
                isValid = true
        }
    }
    return isValid
}

fun newsValidator (news: MyNew): Boolean {
    var isValid = false
    if (
                news.description.length > 5 &&
                news.title.length > 3
    ) isValid = true
    return isValid
}

fun userInfoRequestValidator( user: UserInfoRequest): Boolean {
    var isValid = false
    if (
        user.name.length > 5 &&
        user.email.length > 3 &&
        user.password.length > 4
    ) isValid = true
    return isValid
}

fun passwordValidator(password: String): Boolean {

    var validate = false
    if (password.isNotBlank() &&
        password.length >= 4
        ) {
                validate = true
    }
    return validate
}

fun removeSpace(input: String): String {
    var str = input
    str = str.replace("\\s+".toRegex(), "")
    return str
}