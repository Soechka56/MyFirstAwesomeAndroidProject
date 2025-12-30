package com.soechka1.myfirstawesomeandroidproject.utils

import org.mindrot.jbcrypt.BCrypt

object SecurityUtils {
    fun hashPassword(pass: String): String =
        BCrypt.hashpw(pass, BCrypt.gensalt())

    fun checkPassword(pass: String, hashPass: String): Boolean =
        BCrypt.checkpw(pass, hashPass)
}
