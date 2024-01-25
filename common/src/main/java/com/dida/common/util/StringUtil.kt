package com.dida.common.util

fun maskEmail(email: String): String {
        val parts = email.split("@")
        val username = parts[0]
        val maskedUsername = if(username.length<=2){
            username.take(1) + "*".repeat(username.length-1)
        }else{
            username.take(2) + "*".repeat(username.length-2)
        }
        return "$maskedUsername@${parts[1]}"
}