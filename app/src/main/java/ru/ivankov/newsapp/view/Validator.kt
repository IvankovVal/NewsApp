package ru.ivankov.newsapp.view


fun emailValidator(email: String): Boolean {
    val emailEndsList =
        listOf("ru", "com", "org", "net", "su", "kz", "ua", "by")
    var validate = false
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
                validate = true
        }
    }
    return validate
}

//fun passwordValidator(password: String): Boolean {
//
//    var validate = false
//    if (password.isNotBlank() &&
//        password.length >= 4
//        ) {
//                validate = true
//    }
//    return validate
//}

fun removeSpace(input: String): String {
    var str = input
    str = str.replace("\\s+".toRegex(), "")
    return str
}