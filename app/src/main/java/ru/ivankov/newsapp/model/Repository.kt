package ru.ivankov.newsapp.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object Repository {

    // user DATA
    var ownerData: MutableState<OwnerData?> = mutableStateOf(null)
    var publicData: MutableState<PublicUserData?> = mutableStateOf(null)

    // alert DATA
    var alertData: MutableState<AlertData> = mutableStateOf(
        AlertData(
            "",
            "",
            "",
            "",
            "",
            0
        )
    )

    // response LISTS
    val responseData: MutableList<PostNewsResponse?> = mutableListOf(null)
    val usersResponseData:  MutableList<UserInfoResponse?> = mutableListOf(null)
}

class OwnerData(
    var authToken: String,
    override var avatar: String,
    override var email: String,
    override var id: String,
    override var name: String,
    override var role: String
) : IPublicUserData

class PublicUserData(
    override var avatar: String,
    override var email: String,
    override var id: String,
    override var name: String,
    override var role: String
) : IPublicUserData

class AlertData(
    var alertTitle: String,
    var alertDescription: String,
    var alertConfirmBTN: String,
    var alertCancelBTN: String,
    var alertCorrutine: String,
    var alertCorrutineParam: Int
)

class FindInputValues {
    var findAuthor by mutableStateOf("")
    var findKeyWords by mutableStateOf("")
    var findTags by mutableStateOf(listOf<String>())
}
interface IPublicUserData {
    var avatar: String
    var email: String
    var id: String
    var name: String
    var role: String
}

// bellator87@mail.ru 198727
