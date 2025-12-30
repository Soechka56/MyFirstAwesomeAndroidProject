package com.soechka1.myfirstawesomeandroidproject.mapper

import com.soechka1.myfirstawesomeandroidproject.db.entity.UserWithFavoriteList
import com.soechka1.myfirstawesomeandroidproject.model.UserDataModel

class UserModelMapper {

    fun map(input: UserWithFavoriteList): UserDataModel {
        with(input){
            return UserDataModel(
                userId = user.userId,
                username = user.username,
                email = user.email,
                favorites = favorites
            )
        }
    }
}