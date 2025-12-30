package com.soechka1.myfirstawesomeandroidproject.navscreens.contentpage

import androidx.annotation.StringRes
import com.soechka1.myfirstawesomeandroidproject.R

enum class SortOption(@StringRes val labelRes: Int) {
    NAME_ASC(R.string.sort_name_asc),
    NAME_DESC(R.string.sort_name_desc),
    SINGER_ASC(R.string.sort_singer_asc),
    SINGER_DESC(R.string.sort_singer_desc)
}
