package com.biathlonapp.utils

import java.net.UnknownHostException
import java.net.SocketTimeoutException

object ErrorHandler {

    fun getErrorMessage(e: Exception): String {
        return when {
            e.message?.contains("Unable to resolve host") == true ||
                    e is UnknownHostException ||
                    e.message?.contains("No address associated with hostname") == true ->
                "Проверьте подключение к интернету"

            e is SocketTimeoutException ||
                    e.message?.contains("timeout") == true ->
                "Превышено время ожидания. Проверьте соединение"

            e.message?.contains("401") == true ||
                    e.message?.contains("403") == true ->
                "Ошибка авторизации"

            e.message?.contains("404") == true ->
                "Данные не найдены"

            e.message?.contains("500") == true ||
                    e.message?.contains("502") == true ||
                    e.message?.contains("503") == true ->
                "Сервер временно недоступен. Попробуйте позже"

            else -> "Ошибка: ${e.message ?: "Неизвестная ошибка"}"
        }
    }
}