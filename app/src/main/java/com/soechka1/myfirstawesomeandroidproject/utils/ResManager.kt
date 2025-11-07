package com.soechka1.myfirstawesomeandroidproject.utils

import android.content.Context
import androidx.annotation.StringRes

class ResManager (
    private val ctx: Context)
{
    fun getString(@StringRes stringResId: Int): String{
        return ctx.getString(stringResId)
    }

    fun getStringPattern(@StringRes stringResId: Int, vararg args: Any): String{
        return ctx.getString(stringResId, *args)
    }
}