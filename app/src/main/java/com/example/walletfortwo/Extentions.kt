package com.example.walletfortwo

const val SPLIT = ","
fun String.commaToArray():Array<String> = split(SPLIT).toTypedArray()
fun Array<String>.toCommaString():String = joinToString(SPLIT)