package com.soechka1.myfirstawesomeandroidproject

enum class TypeError(val messageId: Int ) {
    ERROR_USERNAME_SHORT(R.string.username_password_too_shor),
    ERROR_EMAIL_EMPTY(R.string.error_email_empty),
    ERROR_EMAIL_EXIST(R.string.error_user_creation_failed),
    ERROR_EMAIL_NOT_EXIST(R.string.error_email_not_exist),
    ERROR_EMAIL_INVALID(R.string.error_email_invalid),
    ERROR_PASSWORD_EMPTY(R.string.error_password_empty),
    ERROR_PASSWORD_SHORT(R.string.error_password_too_short),
    ERROR_PASSWORD_INCORRECT(R.string.error_password_not_correct),
    ERROR_PASSWORDS_NOT_MATCH(R.string.error_passwords_match)

}