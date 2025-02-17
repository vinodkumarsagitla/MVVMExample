package com.vks.mvvmexample.utils

import java.net.URLEncoder

fun String.toUrlEncode(): String = URLEncoder.encode(this, "UTF-8")